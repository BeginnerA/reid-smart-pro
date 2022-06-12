package top.reid.config.shiro.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.reid.common.api.vo.Result;
import top.reid.smart.core.util.StrTools;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * <p>
 * JWT 工具类
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/1/19
 * @Version V1.0
 **/
public class JwtTools {

    /**
     * Token 过期时间30分钟（用户登录过期时间是此时间的两倍，以 token 在 reids 缓存时间为准）
     */
    public static final long EXPIRE_TIME = 30 * 60 * 1000;

    /**
     * 响应错误
     * @param response 服务响应
     * @param code 响应代码
     * @param errorMsg 错误信息
     */
    public static void responseError(ServletResponse response, Integer code, String errorMsg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Result<String> jsonResult = new Result<String>(code, errorMsg);
        OutputStream os = null;
        try {
            os = httpServletResponse.getOutputStream();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(401);
            os.write(new ObjectMapper().writeValueAsString(jsonResult).getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验 token 是否正确
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            // 根据密码生成 JWT 效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            // 效验 TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得 token 中的信息无需 secret 解密也能获得
     * @param token 密钥
     * @return token 中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名，5min 后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的 token
     */
    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带 username 信息
        return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);

    }

    /**
     * 根据 request 中的 token 获取用户账号
     * @param request Http Servlet 请求
     * @return 用户账号
     */
    public static String getUserNameByToken(HttpServletRequest request) {
        String accessToken = request.getHeader("X-Access-Token");
        String username = getUsername(accessToken);
        if (StrTools.isEmpty(username)) {
            throw new NullPointerException("未获取到用户");
        }
        return username;
    }

}
