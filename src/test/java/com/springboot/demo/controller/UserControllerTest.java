package com.springboot.demo.controller;

import com.springboot.demo.common.JsonUtils;
import com.springboot.demo.entity.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ldb
 * @date 2019-04-29 16:40
 */
//@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
//@Rollback
//@Transactional
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;


    private static final String SERVICE_NAME_PREFIX = "/user";

    private static final String TOKEN = "mytoken";


    /**
     * 上传文件测试
     * @throws Exception
     */
    @Test
    public void testUpLoadFile() throws Exception {
        File file = new File("d:/a.jpg");
        mockMvc.perform(
                MockMvcRequestBuilders.multipart(SERVICE_NAME_PREFIX + "/upload")
                        .file(new MockMultipartFile("file", file.getName(), null, new FileInputStream(file)))
                        .header("token",TOKEN)
                        .param("name","刘德华")
                        .contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().isOk())
                .andExpect(jsonPath("$.%s", "statusCode").value(0))
                .andDo(print()).andReturn();

    }

    @Test
    public void testSave() throws Exception {
        User user = new User("ldb", 24, LocalDate.of(2019, Month.JULY, 5), true, LocalDateTime.now(), "123456");
        mockMvc.perform(
                post(SERVICE_NAME_PREFIX  + "/save")
                        .content(JsonUtils.objectToJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$.%s", "statusCode").value(0))
                .andDo(print()).andReturn();

    }

}
