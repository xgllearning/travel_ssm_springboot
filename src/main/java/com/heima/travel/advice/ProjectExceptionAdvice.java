package com.heima.travel.advice;

import com.heima.travel.exception.BusinessException;
import com.heima.travel.exception.SystemException;
import com.heima.travel.vo.Code;
import com.heima.travel.vo.ResultInfo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 *   TODO: 异常处理器 (AOP思想)
 *       1. ControllerAdvice : 默认controller层bean的所有方法为切入点
 *       2. 通知 : 异常通知
 * */
//为Rest风格开发的控制器类做增强
//此注解自带@ResponseBody注解与@ControllerAdvice注解，具备对应的功能
//ControllerAdvice注解: 添加在异常处理器上
@RestControllerAdvice
@Slf4j
public class ProjectExceptionAdvice {
    //0.添加pom.xml依赖
    //1.获取日志对象 绑定当前的类
    //private static final Logger LOGGER = LoggerFactory.getLogger(ProjectExceptionAdvice.class);
    //专门处理BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResultInfo doBusinessException(BusinessException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        //TODO：3. log4j打印日志-->在本地生成日志文件
        // 4.获取用户当前请求的url地址，定位报错信息的请求路径
        String requestURI = request.getRequestURI();
        // 5.获取错误信息
        String errorMsg = ex.getMessage();
        // 6.设置日志输出级别
        log.info("用户访问：{},报错：{}",requestURI,errorMsg);
        response.sendRedirect("/error.html");
        //发送对应消息传递给用户，提醒规范操作
        return new ResultInfo(false,ex.getCode(),ex.getMessage());
    }

    //专门处理SystemException
    @ExceptionHandler({SystemException.class})
    public ResultInfo doSystemException(SystemException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect("/error.html");
        //TODO：3. log4j打印日志-->在本地生成日志文件
        // 4.获取用户当前请求的url地址，定位报错信息的请求路径
        String requestURI = request.getRequestURI();
        // 5.获取错误信息
        String errorMsg = ex.getMessage();
        // 6.设置日志输出级别
        //LOGGER.info("用户访问：{},报错：{}",requestURI,errorMsg);
        log.info("用户访问：{},报错：{}",requestURI,errorMsg);
//        发送固定消息传递给用户，安抚用户
//        发送特定消息给运维人员，提醒维护
//                记录日志
        return new ResultInfo(false,ex.getCode(),ex.getMessage());
    }

//    //处理其他未知异常
//    @ExceptionHandler(Exception.class)
//    public ResultInfo doException(Exception ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
//        response.sendRedirect("/error.html");
//        return new ResultInfo(false,Code.SYSTEM_UNKNOW_ERR,"服务器正在维护,请稍后访问");
//    }
}
