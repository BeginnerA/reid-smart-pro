package top.reid.smart.base.core.util;

import cn.hutool.core.text.CharPool;

/**
 * <p>
 * 常用字符
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2021/12/31
 * @Version V1.0
 **/
public interface CommonCharacter extends CharPool {

    /**
     * 中文字符集
     */
    String UTF_8 = "UTF-8";

    /**
     * {@code 500 Server Error} (HTTP/1.0 - RFC 1945)
     */
    Integer SC_INTERNAL_SERVER_ERROR_500 = 500;

    /**
     * {@code 200 OK} (HTTP/1.0 - RFC 1945)
     */
    Integer SC_OK_200 = 200;

    /**
     * 访问权限认证未通过 510
     */
    Integer SC_REID_NO_AUTHZ = 510;

    /**
     * 访问令牌
     */
    String X_ACCESS_TOKEN = "X-Access-Token";

    /**
     * 令牌无效提示信息
     */
    String TOKEN_IS_INVALID_MSG = "Token 失效，请重新登录!";

    /**
     * 登录用户 Token 令牌缓存 KEY 前缀
     */
    String PREFIX_USER_TOKEN  = "prefix_user_token_";

    /**
     * Token 缓存时间：3600秒即一小时
     */
    int  TOKEN_EXPIRE_TIME  = 3600;

    /**
     * 微服务读取配置文件属性 服务地址
     */
    String CLOUD_SERVER_KEY = "spring.cloud.nacos.discovery.server-addr";

    /**
     * gateway 通过 header 传递根路径 basePath
     */
    String X_GATEWAY_BASE_PATH = "X_GATEWAY_BASE_PATH";

    /**
     * XML Http 请求
     */
    String XML_HTTP_REQUEST = "XMLHttpRequest";

    /**
     * X-Requested-With 请求
     */
    String X_REQUESTED_WITH = "X-Requested-With";

    /**
     * 常用值存储字段
     */
    String VALUE_FIELD = "value";

    /**
     * 常用基础包存储字段
     */
    String BASE_PACKAGES_FIELD = "basePackages";
}
