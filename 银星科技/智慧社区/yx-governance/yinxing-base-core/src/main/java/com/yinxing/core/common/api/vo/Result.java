package com.yinxing.core.common.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yinxing.core.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 接口返回数据格式
 * </p>
 *
 * @Author REID
 * @Data 2022/10/26
 * @Version V1.0
 **/
@Data
@ApiModel(value = "接口返回对象", description = "接口返回对象")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    @ApiModelProperty(value = "成功标志")
    private boolean success = true;

    /**
     * 返回处理消息
     */
    @ApiModelProperty(value = "返回处理消息")
    private String message = "";

    /**
     * 返回代码
     */
    @ApiModelProperty(value = "返回代码")
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    @ApiModelProperty(value = "返回数据对象")
    private T result;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private long timestamp = System.currentTimeMillis();

    public Result() {
    }

    /**
     * 构造函数
     *
     * @param code    返回代码
     * @param message 返回处理消息
     */
    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功
     *
     * @param message 返回处理消息
     * @return 结果
     */
    public Result<T> success(String message) {
        this.message = message;
        this.code = CommonConstant.SC_OK_200;
        this.success = true;
        return this;
    }

    /**
     * 成功
     *
     * @param <T> 返回类型
     * @return 结果
     */
    public static <T> Result<T> ok() {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        return r;
    }

    /**
     * 成功
     *
     * @param msg 返回消息
     * @param <T> 返回类型
     * @return 结果
     */
    public static <T> Result<T> ok(String msg) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setResult((T) msg);
        r.setMessage(msg);
        return r;
    }

    /**
     * 成功
     *
     * @param data 返回数据
     * @param <T>  返回类型
     * @return 结果
     */
    public static <T> Result<T> ok(T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setResult(data);
        return r;
    }

    /**
     * 成功
     *
     * @param msg  返回消息
     * @param data 返回数据
     * @param <T>  返回类型
     * @return 结果
     */
    public static <T> Result<T> ok(String msg, T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }

    /**
     * 失败
     *
     * @param msg 返回消息
     * @param <T> 返回类型
     * @return 结果
     */
    public static <T> Result<T> error(String msg) {
        return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
    }

    /**
     * 失败
     *
     * @param msg  返回消息
     * @param data 返回数据
     * @param <T>  返回类型
     * @return 结果
     */
    public static <T> Result<T> error(String msg, T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(false);
        r.setCode(CommonConstant.SC_INTERNAL_SERVER_ERROR_500);
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }

    /**
     * 失败
     *
     * @param code 返回代码
     * @param msg  返回数据
     * @param <T>  返回类型
     * @return 结果
     */
    public static <T> Result<T> error(int code, String msg) {
        Result<T> r = new Result<T>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    /**
     * 失败（500），系统性错误
     *
     * @param message 返回数据
     * @return 结果
     */
    public Result<T> error500(String message) {
        this.message = message;
        this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
        this.success = false;
        return this;
    }

    /**
     * 无权限访问返回结果
     */
    public static <T> Result<T> noAuth(String msg) {
        return error(CommonConstant.SC_NO_AUTH, msg);
    }

}