/*
 * *********************************************************************************************
 *    Copyright (c) 2019. YNT CO., LTD. All Rights Reserved.
 *    FileName    : RespStatusCode.java
 *    Date        : 2019-4-29
 *    Author      : 梁宇 （Eid Leung）
 *    Module      : RespStatusCode
 *    Function    :
 *  --------------------------------------------------------------------------------------------
 *    Modify History
 *    NO        Date        Modifier        Modified Content
 * *********************************************************************************************
 */

package com.springboot.demo.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 返回错误码枚举类
 *
 * @author ioiogoo
 * @date 2019-03-15 10:35
 */
@ToString
@Getter
@AllArgsConstructor
public enum RespStatusCode implements CommonEnum {
    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    /**
     * 参数错误：1001-1999
     */
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    PARAM_TO_LONG(1005, "参数长度超过255个字符"),

    /**
     * 用户错误：2001-2999
     */
    USER_NOT_LOGGED_IN(2001, "用户未登录"),
    USER_LOGIN_ERROR(2002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003, "账号已被禁用"),
    USER_NOT_EXIST(2004, "用户不存在"),
    USER_HAS_EXISTED(2005, "用户已存在"),
    USER_REGISTER_ERROR(2006, "用户注册失败"),
    USER_PASSWORD_ERROR(2007, "密码错误"),
    CAPTCHA_ERROR(2008, "验证码错误"),
    USER_ACCOUNT_LOCKED(2009, "账号已被锁定"),
    USER_TOKEN_INVALID(2010, "token无效"),
    USER_UN_SUBSCRIBE(2011, "用户未关注"),
    /**
     * 业务错误：3001-3999
     */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(3001, "某业务出现问题"),
    FILE_CHECK_ERROR(3002, "wav音频文件校验失败，转换失败"),
    FILE_AUDIO_ERROR(3003, "音频文件转成wav失败"),
    FILE_UPLOAD_ERROR(3004,"文件上传出错！"),

    /**
     * 系统错误：4001-4999
     */
    SYSTEM_INNER_ERROR(4001, "系统繁忙，请稍后重试"),
    SERVICE_UN_AVAILABLE(4002, "服务不可用"),

    /**
     * 数据错误：5001-5999
     */
    RESULT_DATA_NONE(5001, "数据未找到"),
    DATA_IS_WRONG(5002, "数据有误"),
    DATA_ALREADY_EXISTED(5003, "数据已存在"),

    /**
     * 数据库错误 6001-6999
     */
    DB_INSERT_INVOKE_ERROR(6001, "数据库插入错误"),
    DB_DELETE_INVOKE_ERROR(6002, "数据库删除错误"),
    DB_UPDATE_INVOKE_ERROR(6003, "数据库修改错误"),
    DB_SELECT_INVOKE_ERROR(6004, "数据查询错误"),

    /**
     * 接口错误：7001-7999
     */
    INTERFACE_INNER_INVOKE_ERROR(7001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(7002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(7003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(7004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(7005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(7006, "接口负载过高"),
    REQUEST_METHOD_ERROR(7007, "请求方式错误"),

    /**
     * 权限错误：8001-8999
     */
    PERMISSION_NO_AUTHENTICATION(8002, "没有认证"),
    PERMISSION_NO_ACCESS(8001, "无访问权限"),

    /**
     * 前端、APP错误： 9001-9999
     */
    PHONE_NUMBER_FORMAT_ERROR(9001, "手机号码格式错误"),
    DUPLICATE_BIND_TEL_ERROR(9002, "重复绑定手机号");
    private Integer value;
    private String msg;

    @JsonValue
    public String getCode() {
        return String.valueOf(value);
    }
}
