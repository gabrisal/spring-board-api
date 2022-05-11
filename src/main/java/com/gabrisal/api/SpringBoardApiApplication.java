package com.gabrisal.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackageClasses = SpringBoardApiApplication.class)
@SpringBootApplication
public class SpringBoardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoardApiApplication.class, args);
    }

}
