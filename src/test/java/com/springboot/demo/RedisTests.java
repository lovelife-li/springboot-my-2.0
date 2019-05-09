package com.springboot.demo;

import com.springboot.demo.common.RedisClient;
import com.springboot.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTests {

    @Autowired
    RedisClient redisClient; //k-v都是对象的

    @Test
    public void test() {

        redisClient.set("ynt", new User("zhangsan", 20));
        
        System.out.println("hello");

    }

}
