package top.reid.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import java.io.Serial;

/**
 * <p>
 * 访问令牌
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/1/19
 * @Version V1.0
 **/
public class JwtToken implements AuthenticationToken {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 访问令牌
     */
    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
