package com.springboot.demo.common.db;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum DBTypeEnum {

    db01("db01"), db02("db02");

    private String value;

    static DBTypeEnum getEnumByCode(String code) {
        for(DBTypeEnum _enum : DBTypeEnum.class.getEnumConstants()) {
            if (code.equals(_enum.getValue())) {
                return _enum;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getEnumByCode("db01").getValue());
    }

}