package com.springboot.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.demo.common.ResponseWrapper;
import com.springboot.demo.common.db.DataSource;
import com.springboot.demo.entity.ReqData;
import com.springboot.demo.entity.User;
import com.springboot.demo.entity.emums.ColorType;
import com.springboot.demo.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ldb
 * @since 2019-04-29
 */
@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    public List<User> getUser() {

        return null;
    }

    @GetMapping("/test")
    public IPage<User> test() {
        return iUserService.page(new Page<User>(0, 12), null);
    }

    @ApiOperation("添加或者修改用户")
    @PostMapping("/save")
    @DataSource("db02")
    public ResponseWrapper<String> addOrUpdate(@RequestBody User user) {
        iUserService.saveOrUpdate(user);

        return ResponseWrapper.markSuccess("");
    }

    @ApiOperation("添加或者修改用户")
    @PostMapping("/save2")
    public ResponseWrapper<String> addOrUpdate2(@RequestBody User user) {
        iUserService.saveOrUpdate(user);

        return ResponseWrapper.markSuccess("");
    }

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public ResponseWrapper<String> upload(@RequestParam("file") MultipartFile file, String name) {

        log.info("file:{},name:{}", file, name);

        return ResponseWrapper.markSuccess("");
    }


    /**
     *Date birth 默认不支持接收，报string 转 date 错误
     *
     */
    @ApiOperation("支持各种数据类型接收")
    @PostMapping("/testData")
    public ResponseWrapper<String> testData(@RequestBody ReqData reqData) {

        System.out.println("hello");
        return ResponseWrapper.markSuccess("");
    }

    @ApiOperation("接收枚举作为参数")
    @GetMapping(value = "/testData2")
    public ResponseWrapper<String> testData(String name ,ColorType colorType,@RequestParam Date date) {

        System.out.println("hello");
        return ResponseWrapper.markSuccess(name);
    }


    @ApiOperation("接收数组和Map")
    @PostMapping("/testData3")
    public ResponseWrapper<String> testData(Integer[]  ids ,@RequestBody HashMap<Integer,ColorType> map) {

        System.out.println("hello");
        return ResponseWrapper.markSuccess("");
    }


    @ApiOperation("查询接收日期")
    @GetMapping("/testData4")
    public ResponseWrapper<String> getData(String name, Date start,@RequestParam LocalDate end,@RequestParam LocalDateTime mid) {

        System.out.println("hello");
        return ResponseWrapper.markSuccess("");
    }

}
