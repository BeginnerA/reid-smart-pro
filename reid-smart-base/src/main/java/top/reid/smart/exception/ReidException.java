package top.reid.smart.exception;

import java.io.Serial;

/**
 * <p>
 * 自定义全局异常
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2021/11/19
 * @Version V1.0
 **/
public class ReidException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	private ExceptionEnum exceptionEnum;

	public ReidException(ExceptionEnum exceptionEnum) {
		this.exceptionEnum = exceptionEnum;
	}

	public ExceptionEnum getExceptionEnum() {
		return exceptionEnum;
	}

	public void printException(ReidException e){
		ExceptionEnum exceptionEnum = e.getExceptionEnum();
		System.out.println("异常[" + exceptionEnum.getCode() + "]：" + exceptionEnum.getMessage());
	}

	public ReidException(String message){
		super(message);
	}

	public ReidException(Throwable cause) {
		super(cause);
	}

	public ReidException(String message,Throwable cause) {
		super(message,cause);
	}
}
