package com.heima.travel.advice;

import com.heima.travel.exception.BusinessException;
import com.heima.travel.exception.SystemException;
import com.heima.travel.vo.Code;
import com.heima.travel.vo.ResultInfo;

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
public class ProjectExceptionAdvice {

    //专门处理BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResultInfo doBusinessException(BusinessException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
//        System.out.println("异常触发");
        response.sendRedirect("/error.html");
        //发送对应消息传递给用户，提醒规范操作
        return new ResultInfo(false,ex.getCode(),ex.getMessage());
    }

    //专门处理SystemException
    @ExceptionHandler({SystemException.class})
    public ResultInfo doSystemException(SystemException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect("/error.html");
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
