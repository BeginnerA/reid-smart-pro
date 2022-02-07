package top.reid.config.shiro;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import top.reid.smart.core.util.CommonCharacter;
import top.reid.config.shiro.util.JwtTools;
import top.reid.smart.spring.SpringContextTools;
import top.reid.smart.spring.annotation.CheckBean;
import top.reid.system.SysCommonApi;
import top.reid.system.vo.LoginUser;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>
 * 自定义 Realm 认证逻辑
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/13
 * @Version V1.0
 **/
@CheckBean({SysCommonApi.class})
@Slf4j
@Component
@ConditionalOnProperty(prefix = "reid.shiro", name = "enableShiro", havingValue = "true")
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    SysCommonApi sysCommonApi;

    /**
     * 权限信息认证(包括角色以及权限)是用户访问 controller 的时候才进行验证( redis 存储的此处权限信息)
     * 触发检测用户权限时才会调用此方法，例如 checkRole,checkPermission
     *
     * @param principalCollection 身份信息
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("=============== Shiro 权限认证开始 ============ [ roles、permissions ] ==========");
        String username = null;
        if (principalCollection != null) {
            LoginUser sysUser = (LoginUser) principalCollection.getPrimaryPrincipal();
            username = sysUser.getUsername();
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 设置用户拥有的角色集合
        Set<String> roleSet = sysCommonApi.queryUserRoles(username);
        info.setRoles(roleSet);

        // 设置用户拥有的权限集合
        Set<String> permissionSet = sysCommonApi.queryUserAuths(username);
        info.addStringPermissions(permissionSet);
        System.out.println(permissionSet);
        log.info("=============== Shiro 权限认证成功 ==============");
        return info;
    }

    /**
     * 用户信息认证是在用户进行登录的时候进行验证(不存 redis )
     * 也就是说验证用户输入的账号和密码是否正确，错误抛出异常
     *
     * @param authenticationToken 用户登录的账号密码信息
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("=============== Shiro 身份认证开始 ============ doGetAuthenticationInfo ==========");
        Object token = authenticationToken.getCredentials();
        if (token == null) {
            log.info("———————— 身份认证失败 —————————— IP 地址:  "+ ServletUtil.getClientIP(SpringContextTools.getHttpServletRequest()));
            throw new AuthenticationException(CommonCharacter.TOKEN_IS_INVALID_MSG);
        }
        // 校验 token 有效性
        LoginUser loginUser = null;
        try {
            loginUser = this.checkUserTokenIsEffect((String) token);
        } catch (AuthenticationException e) {
            JwtTools.responseError(SpringContextTools.getHttpServletResponse(),401, e.getMessage());
            e.printStackTrace();
            return null;
        }
        return new SimpleAuthenticationInfo(loginUser, token, getName());
    }

    /**
     * 校验 token 的有效性
     * @param token token
     */
    public LoginUser checkUserTokenIsEffect(String token) throws AuthenticationException {
        // 解密获得 username，用于和数据库进行对比
        String username = JwtTools.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token 非法无效!");
        }

        // 查询用户信息
        log.debug("———————— 校验 token 是否有效 ———————— checkUserTokenIsEffect ———————— "+ token);
        LoginUser loginUser = sysCommonApi.getUserByName(username);
        if (loginUser == null) {
            throw new AuthenticationException("用户不存在!");
        }
        // 判断用户状态
        if (loginUser.getStatus() != 1) {
            throw new AuthenticationException("账号已被锁定，请联系管理员!");
        }
        // 校验 token 是否超时失效 & 或者账号密码是否错误
        if (!jwtTokenRefresh(token, username, loginUser.getPassword())) {
            throw new AuthenticationException(CommonCharacter.TOKEN_IS_INVALID_MSG);
        }

        return loginUser;
    }

    /**
     * JWTToken 刷新生命周期 （实现： 用户在线操作不掉线功能）。<br>
     * 1、登录成功后将用户的 JWT 生成的 Token 作为 k、v 存储到 cache 缓存里面（这时候 k、v 值一样），缓存有效期设置为 Jwt 有效时间的2倍。<br>
     * 2、当该用户再次请求时，通过 JWTFilter 层层校验之后会进入到 doGetAuthenticationInfo 进行身份验证。<br>
     * 3、当该用户这次请求 jwt 生成的 token 值已经超时，但该 token 对应 cache 中的 k 还是存在，则表示该用户一直在操作只是 JWT 的 token 失效了，程序会给 token 对应的 k 映射的 v 值重新生成 JWTToken 并覆盖 v 值，该缓存生命周期重新计算。<br>
     * 4、当该用户这次请求 jwt 在生成的 token 值已经超时，并在 cache 中不存在对应的k，则表示该用户账户空闲超时，返回用户信息已失效，请重新登录。<br>
     * 注意： 前端请求 Header 中设置 Authorization 保持不变，校验有效性以缓存中的 token 为准。
     * 用户过期时间 = Jwt 有效时间 * 2。
     *
     * @param userName 登录名称
     * @param passWord 登录密码
     * @return 是否成功
     */
    public boolean jwtTokenRefresh(String token, String userName, String passWord) {
//        String cacheToken = String.valueOf(redisUtil.get(CommonCharacter.PREFIX_USER_TOKEN + token));
//        if (StrTools.isNotEmpty(cacheToken)) {
//            // 校验token有效性
//            if (!JwtUtil.verify(cacheToken, userName, passWord)) {
//                String newAuthorization = JwtUtil.sign(userName, passWord);
//                // 设置超时时间
//                redisUtil.set(CommonCharacter.PREFIX_USER_TOKEN + token, newAuthorization);
//                redisUtil.expire(CommonCharacter.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME *2 / 1000);
//                log.debug("—————————— 用户在线操作，更新 token 保证不掉线 ————————— jwtTokenRefresh ——————— "+ token);
//            }
//            return true;
//        }
        return false;
    }

    /**
     * 清除当前用户的权限认证缓存
     * @param principals 权限信息
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
}
