package com.heima.travel.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Guoliang Xu
 * @Description: TODO
 * @DateTime: 2022/6/4 22:46
 * TODO:通过AOP实现全局异常统一管理
 * 1.创建本类进行全局管理，RestControllerAdvice=@ControllerAdvice+@ResponseBody
 * 2.配置文件扫描(springmvc.xml) <context:component-scan base-package="com.heima.travel.controller,com.heima.travel.advice"/>
 * 3.开启AOP注解支持并开启事务，applicationContext.xml添加
 * 4.对异常进行处理分类，Exception类型异常，BusinessException类型异常的通知，SystemException
 * 5.最好自定义异常编码
 */

//@ControllerAdvice
//@RestControllerAdvice
public class HandlerExceptionAdvice {

    private static final Logger LOGGER= LoggerFactory.getLogger(HandlerExceptionAdvice.class);

    @ExceptionHandler(RuntimeException.class)
    public void handlerRuntimeException(RuntimeException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        //todo log4j打印日志--》日志文件
       // System.out.println(ex.getMessage());
        //获取当前请求的url地址
        String curUrl = request.getRequestURI();
        //获取错误信息
        String errorMsg = ex.getMessage();
        LOGGER.info("用户访问：{},报错：{}",curUrl,errorMsg);
        response.sendRedirect("/error.html");
    }

}
