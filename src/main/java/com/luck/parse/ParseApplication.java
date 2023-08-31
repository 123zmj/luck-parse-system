package com.luck.parse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author 张梦娇
 * @description <p>解析系统启动类</p>
 * @date 2023-08-23 15:32
 **/
@MapperScan(basePackages = {"com.luck.parse.mapper"})
@SpringBootApplication
public class ParseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParseApplication.class);
    }
}
