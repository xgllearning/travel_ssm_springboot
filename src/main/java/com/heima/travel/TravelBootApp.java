package com.heima.travel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan("com.heima.travel.mapper")
@EnableAspectJAutoProxy//开启aop注解支持
@EnableTransactionManagement//开始事务支持
public class TravelBootApp {
    public static void main(String[] args) {
        SpringApplication.run(TravelBootApp.class,args);
    }
}