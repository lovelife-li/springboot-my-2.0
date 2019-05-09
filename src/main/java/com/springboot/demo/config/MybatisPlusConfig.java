package com.springboot.demo.config;

import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.springboot.demo.mapper")
public class MybatisPlusConfig {




    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // maxTime SQL 执行最大时长，超过自动停止运行，有助于发现问题。（单位毫秒）
        performanceInterceptor.setMaxTime(500);
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }



}
