package top.reid.config.shiro.filters;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import top.reid.smart.core.map.MapTools;
import top.reid.smart.core.util.CommonCharacter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <p>
 *  踢出会话控制过滤器
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/15
 * @Version V1.0
 **/
@Slf4j
public class KickoutSessionControlFilter extends AccessControlFilter {
    /**
     * 会话属性
     */
    private final String sessionAttributeKey = "kickout";
    /**
     * 踢出后到的地址
     */
    private String kickoutUrl;
    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;
    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;
    /**
     * 会话管理器
     */
    private SessionManager sessionManager;
    /**
     * 缓存
     */
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * 设置 Cache 的 key 的前缀
     * @param cacheManager 缓存管理器
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro_redis_cache");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            // 如果没有登录，直接进行之后的流程
            return true;
        }


        Session session = subject.getSession();
        // SysUser user = (SysUser) subject.getPrincipal();
        String username = ""; // user.getUserName();
        Serializable sessionId = session.getId();

        // 读取缓存，没有就存入
        Deque<Serializable> deque = cache.get(username);

        // 如果此用户没有 session 队列，也就是还没有登录过，缓存中没有
        // 就 new 一个空队列，不然 deque 对象为空，会报空指针
        if(deque==null){
            deque = new LinkedList<Serializable>();
        }

        // 如果队列里没有此 sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute(sessionAttributeKey) == null) {
            // 将 sessionId 存入队列
            deque.push(sessionId);
            // 将用户的 sessionId 队列缓存
            cache.put(username, deque);
        }

        // 如果队列里的 sessionId 数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            // 如果踢出后者
            if(kickoutAfter) {
                kickoutSessionId = deque.removeFirst();
                // 踢出后再更新下缓存队列
                cache.put(username, deque);
            } else {
                // 否则踢出前者
                kickoutSessionId = deque.removeLast();
                // 踢出后再更新下缓存队列
                cache.put(username, deque);
            }



            try {
                // 获取被踢出的 sessionId 的 session 对象
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    // 设置会话的 kickout 属性表示踢出了
                    kickoutSession.setAttribute(sessionAttributeKey, true);
                }
            } catch (Exception e) {
                log.error("设置会话异常："+ e);
            }
        }

        // 如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute(sessionAttributeKey) != null) {
            // 会话被踢出了
            try {
                // 退出登录
                subject.logout();
            } catch (Exception e) {
                log.error("会话被踢出异常："+ e);
            }
            saveRequest(request);

            Map<String, String> resultMap = MapTools.newHashMap(2);
            // 判断是不是 Ajax 请求
            if (CommonCharacter.XML_HTTP_REQUEST.equalsIgnoreCase(((HttpServletRequest) request).getHeader(CommonCharacter.X_REQUESTED_WITH))) {
                resultMap.put("user_status", "300");
                resultMap.put("message", "您已经在其他地方登录，请重新登录！");
                // 输出 json 串
                out(response, resultMap);
            }else{
                // 重定向
                WebUtils.issueRedirect(request, response, kickoutUrl);
            }
            return false;
        }
        return true;
    }

    private void out(ServletResponse hresponse, Map<String, String> resultMap) throws IOException {
        try {
            hresponse.setCharacterEncoding("UTF-8");
            PrintWriter out = hresponse.getWriter();
            out.println(JSONUtil.toJsonStr(resultMap));
            out.flush();
            out.close();
        } catch (Exception e) {
            System.err.println("Kick-outSessionFilter.class 输出 JSON 异常（可以忽略）：" + e);
        }
    }
}
