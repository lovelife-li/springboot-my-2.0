package com.springboot.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.springboot.demo.entity.emums.ColorType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

/**
 * @author ldb
 * @date 2019-04-30 14:18
 */
@Data
public class ReqData {

    private Integer id;

    private String desc;


    private Date birth;

    private LocalDate createTime;

    private LocalDateTime updateTime;

    private ColorType colorType;

    private Integer[] ids;

    private HashMap<Integer,ColorType> map;

}
