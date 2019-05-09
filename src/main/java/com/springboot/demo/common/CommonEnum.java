/*
 * *********************************************************************************************
 *    Copyright (c) 2019. YNT CO., LTD. All Rights Reserved.
 *    FileName    : CommonEnum.java
 *    Date        : 2019-4-29
 *    Author      : 梁宇 （Eid Leung）
 *    Module      : CommonEnum
 *    Function    :
 *  --------------------------------------------------------------------------------------------
 *    Modify History
 *    NO        Date        Modifier        Modified Content
 * *********************************************************************************************
 */

package com.springboot.demo.common;

public interface CommonEnum {
    Integer getValue();
    String getMsg();

    static <T extends CommonEnum> T getEnumByCode(Class<T> clazz, Integer code) {
        for(T _enum : clazz.getEnumConstants()) {
            if (code.equals(_enum.getValue())) {
                return _enum;
            }
        }
        return null;
    }

    static <T extends CommonEnum> T getEnumByName(Class<T> clazz, String name) {
        for(T _enum : clazz.getEnumConstants()) {
            if (_enum.getMsg().equals(name)) {
                return _enum;
            }
        }
        return null;
    }
}
