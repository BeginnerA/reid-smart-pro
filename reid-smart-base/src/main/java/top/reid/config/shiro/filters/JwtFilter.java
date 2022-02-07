package top.reid.config.shiro.filters;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import top.reid.smart.core.util.CommonCharacter;
import top.reid.smart.core.util.StrTools;
import top.reid.config.shiro.JwtToken;
import top.reid.config.shiro.util.JwtTools;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 鉴权登录拦截器
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/19
 * @Version V1.0
 **/
public class JwtFilter extends BasicHttpAuthenticationFilter {
    /**
     * 默认开启跨域设置（使用单体）
     * 微服务情况下，此属性设置为false
     */
    private boolean allowOrigin = true;

    public JwtFilter(){}

    public JwtFilter(boolean allowOrigin){
        this.allowOrigin = allowOrigin;
    }

    /**
     * 执行登录认证
     * 该方法返回值表示是否跳过认证，这里如果返回 true，则不会再走一遍认证流程，<br>
     * 如果返回 false，则会执行 isAccessAllowed 方法，再执行 isLoginAttempt 方法，<br>
     * 如果为 false 继续执行 executeLogin 方法，如果没有执行 executeLogin 或者执行结果也是 false，则将执行 sendChallenge 方法，表示认证失败。<br>
     * 返回 true 时，则必须在 postHandle 配置退出登录，这个方法将在执行完业务逻辑后执行，否则将导致下次没有携带 token 时直接使用上次的登录结果，从而非法访问接口。<br>
     *
     * 默认返回 false 时，isLoginAttempt这些方法将重复调用，所以不建议。如果要返回 false，建议复写 sendChallenge 方法，因为其响应内容为空。<br>
     *
     * 也可以将 isAccessAllowed 作为纯判断是否需要认证，或者不复写该方法。<br>
     * @param request 服务请求
     * @param response 服务响应
     * @param mappedValue 映射值
     * @return 是否允许访问
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
            JwtTools.responseError(response,401, CommonCharacter.TOKEN_IS_INVALID_MSG);
            return false;
        }
    }

    /**
     * 执行登录
     * @param request 服务请求
     * @param response 服务响应
     * @return 是否成功
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(CommonCharacter.X_ACCESS_TOKEN);

        if (StrTools.isEmpty(token)) {
            // 获取token参数
            token = httpServletRequest.getParameter("token");
        }

        JwtToken jwtToken = new JwtToken(token);
        // 提交给 realm 进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 对跨域提供支持
     * @param request 服务请求
     * @param response 服务响应
     * @return 如果允许过滤器链继续执行，则为 true，如果子类已显式处理请求，则为 false
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if(allowOrigin){
            httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
            // shiro 过滤器配置引起的跨域问题
            // 是否允许发送 Cookie，默认 Cookie 不包括在 CORS 请求之中。设为 true 时，表示服务器允许 Cookie 包含在请求中。
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        }
        // 跨域时会首先发送一个 option 请求，这里我们给 option 请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }
}
