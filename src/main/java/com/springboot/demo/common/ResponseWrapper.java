package com.springboot.demo.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * response工具类
 *
 * @author ldb
 * @date 2019-03-15 10:36
 */
@ApiModel(value = "响应体")
public class ResponseWrapper<T> {
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private RespStatusCode statusCode;
    /**
     * 状态信息
     */
    @ApiModelProperty(value = "状态信息")
    private String msg;
    /**
     * 返回时间
     */
    @ApiModelProperty(value = "返回时间")
    private String respTime;
    /**
     * 返回内容
     */
    @ApiModelProperty(value = "返回内容")
    private T data;


    /**
     * 操作成功
     *
     * @param data
     * @return
     */
    public static <T> ResponseWrapper<T> markSuccess(T data) {
        ResponseWrapper<T> responseHelper = new ResponseWrapper<>();
        responseHelper.setStatusCode(RespStatusCode.SUCCESS);
        responseHelper.setMsg(RespStatusCode.SUCCESS.getMsg());
        responseHelper.setRespTime(TimeUtil.getCurrentNanoTime());
        responseHelper.setData(data);
        return responseHelper;
    }
    public static ResponseWrapper<Void> markSuccess() {
        ResponseWrapper<Void> responseHelper = new ResponseWrapper<>();
        responseHelper.setStatusCode(RespStatusCode.SUCCESS);
        responseHelper.setMsg(RespStatusCode.SUCCESS.getMsg());
        responseHelper.setRespTime(TimeUtil.getCurrentNanoTime());
        return responseHelper;
    }

    /**
     * 参数无效错误
     *
     * @return
     */
    public static <T> ResponseWrapper<T> markParamError() {
        ResponseWrapper<T> responseHelper = new ResponseWrapper<>();
        responseHelper.setStatusCode(RespStatusCode.PARAM_IS_INVALID);
        responseHelper.setMsg(RespStatusCode.PARAM_IS_INVALID.getMsg());
        responseHelper.setRespTime(TimeUtil.getCurrentNanoTime());
        return responseHelper;
    }

    /**
     * 参数无效错误
     *
     * @return
     */
    public static <T> ResponseWrapper<T> markParamError(RespStatusCode respStatusCode) {
        ResponseWrapper<T> responseHelper = new ResponseWrapper<>();
        responseHelper.setStatusCode(respStatusCode);
        responseHelper.setMsg(respStatusCode.getMsg());
        responseHelper.setRespTime(TimeUtil.getCurrentNanoTime());
        return responseHelper;
    }


    /**
     * 自定义回复类型
     *
     * @param respStatus
     * @param data
     * @return
     */
    public static <T> ResponseWrapper<T> markCustom(RespStatusCode respStatus, T data) {
        ResponseWrapper<T> responseHelper = new ResponseWrapper<>();
        responseHelper.setStatusCode(respStatus);
        responseHelper.setMsg(respStatus.getMsg());
        responseHelper.setRespTime(TimeUtil.getCurrentNanoTime());
        responseHelper.setData(data);
        return responseHelper;
    }

    /**
     * 自定义回复类型
     *
     * @param statusCode
     * @param msg
     * @param data
     * @return
     */
    public static <T> ResponseWrapper<T> markCustom(RespStatusCode statusCode, String msg, T data) {
        ResponseWrapper<T> responseHelper = new ResponseWrapper<>();
        responseHelper.setStatusCode(statusCode);
        responseHelper.setMsg(msg);
        responseHelper.setRespTime(TimeUtil.getCurrentNanoTime());
        responseHelper.setData(data);
        return responseHelper;
    }

    public RespStatusCode getStatusCode() {
        return statusCode;
    }

    public ResponseWrapper<T> setStatusCode(RespStatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseWrapper<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getRespTime() {
        return respTime;
    }

    public ResponseWrapper<T> setRespTime(String respTime) {
        this.respTime = respTime;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "statusCode=" + statusCode +
                ", msg='" + msg + '\'' +
                ", respTime='" + respTime + '\'' +
                ", data=" + data +
                '}';
    }
}
