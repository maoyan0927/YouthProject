package com.youth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan("com.youth.mapper")
public class YouthApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(YouthApplication.class, args);
    }

}
