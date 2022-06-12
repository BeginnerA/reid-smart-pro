package top.reid.smart.exception;

/**
 * <p>
 * 自定义全局异常
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/1/26
 * @Version V1.0
 **/
public enum ExceptionEnum implements ReidExceptionEnum {
    // HTTP异常状态 常用状态码：https://www.php.cn/http/http-http.html
    HTTP_EXCEPTION_400("400","请求出错"),
    HTTP_EXCEPTION_401("401","授权失败"),
    HTTP_EXCEPTION_403("403","禁止访问"),
    HTTP_EXCEPTION_404("404","Web 服务器找不到您所请求的文件或脚本。请检查 URL 以确保路径正确"),
    HTTP_EXCEPTION_405("405","不允许此方法，对于请求所标识的资源，不允许使用请求行中所指定的方法。请确保为所请求的资源设置了正确的 MIME 类型"),
    HTTP_EXCEPTION_406("406","不可接受，根据此请求中所发送的“接受”标题，此请求所标识的资源只能生成内容特征为“不可接受”的响应实体"),
    HTTP_EXCEPTION_407("407","需要代理身份验证，在可为此请求提供服务之前，您必须验证此代理服务器。请登录到代理服务器，然后重试"),
    HTTP_EXCEPTION_412("412","前提条件失败，在服务器上测试前提条件时，部分请求标题字段中所给定的前提条件估计为 FALSE。客户机将前提条件放置在当前资源 meta-information（标题字段数据）中，以防止所请求的方法被误用到其他资源"),
    HTTP_EXCEPTION_414("414","Request-URI 太长，服务器拒绝服务此请求"),
    HTTP_EXCEPTION_500("500","服务器的内部错误，Web 服务器不能执行此请求"),
    HTTP_EXCEPTION_501("501","Web 服务器不支持实现此请求所需的功能。请检查 URL 中的错误"),
    HTTP_EXCEPTION_502("502","网关出错，当用作网关或代理时，服务器将从试图实现此请求时所访问的upstream 服务器中接收无效的响应"),
    // 数据库异常状态
    DATABASE_EXCEPTION_2000("2000","数据库连接异常"),
    MONITOR_EXCEPTION_2001("2001","文件监控异常");

    /**
     * 错误码
     */
    public final String code;
    /**
     * 提示信息
     */
    public final String message;

    ExceptionEnum(String code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode(){
        return code;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
