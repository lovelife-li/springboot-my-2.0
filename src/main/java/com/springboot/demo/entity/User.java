package com.springboot.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.springboot.demo.entity.emums.ColorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ldb
 * @since 2019-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("db_user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

    /**
     * 喜欢颜色
     */
    private ColorType color;

    private LocalDate birth;

    private Boolean enable;

    private LocalDateTime hiredate;

    private String pwd;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public User(String name, Integer age, LocalDate birth, Boolean enable, LocalDateTime hiredate, String pwd) {
        this.name = name;
        this.age = age;
        this.birth = birth;
        this.enable = enable;
        this.hiredate = hiredate;
        this.pwd = pwd;
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
