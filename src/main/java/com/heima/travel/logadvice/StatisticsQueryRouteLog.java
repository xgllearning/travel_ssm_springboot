package com.heima.travel.logadvice;

import com.heima.travel.exception.SystemException;
import com.heima.travel.vo.Code;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class StatisticsQueryRouteLog {
    //进行日志记录
    //private static final Logger LOGGER= LoggerFactory.getLogger(StatisticsQueryRouteLog.class);

    //声明切入点方法
    //对controller层进行增强若不能生效的原因：增强的切面类属于父容器，而被增强的对象属于子容器controller，同时子容器也未开启aop注解支持
    //解决：1.在子容器维护切面类对象，扫描当前包 2.在子容器开启aop注解支持 【了解】
    //@Pointcut("execution(public * com.heima.travel.controller.RouteController.find*(..))")
    //public void pt(){}

    //推荐在服务层做增强，对服务层的find方法进行运行时长统计
    @Pointcut("execution(public * com.heima.travel.service.RouteService.find*(..))")
    public void pt(){}

    //配置环绕通知
    @Around("pt()")
    public Object aroundLog(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            //获取方法名称
            String methodName = pjp.getSignature().getName();
            log.info("{}方法执行了...",methodName);
            long start = System.currentTimeMillis();
            result = pjp.proceed();
            log.info("{}方法执行耗时为：{}",methodName,System.currentTimeMillis()-start);
        } catch (Throwable e) {
            throw new SystemException(Code.SYSTEM_UNKNOW_ERR,"统计service层RouteService类下的find方法未知异常");//Integer code, String message
        }
        return result;
    }


}
