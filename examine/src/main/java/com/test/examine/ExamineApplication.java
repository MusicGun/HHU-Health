package com.test.examine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.test.examine.mapper")
@EnableCaching
public class ExamineApplication {
    public static void main(String[] args) {


        SpringApplication.run(ExamineApplication.class);


    }


}
