package com.springboot.demo.entity.emums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import com.springboot.demo.common.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ldb
 * @date 2019-04-30 14:21
 */
@Getter
@ToString
@AllArgsConstructor
public enum  ColorType implements CommonEnum,IEnum<Integer> {

    GREEN(1001,"红色"),
    RED(1002,"绿色");

    @JsonValue
    private Integer value;
    private String msg;

    public static void main(String[] args) {
        System.out.println(ColorType.GREEN);

        System.out.println(CommonEnum.getEnumByCode(ColorType.class,1002));

        System.out.println(CommonEnum.getEnumByName(ColorType.class,"绿色"));

    }

}
