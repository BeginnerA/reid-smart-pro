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
public interface ReidExceptionEnum {
    /**
     * 获取状态码
     * @return 状态码
     */
    String getCode();

    /**
     * 获取提示信息
     * @return 提示信息
     */
    String getMessage();
}
