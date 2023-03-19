package com.tplink.blog.common.aop;

import com.alibaba.fastjson.JSON;
import com.tplink.blog.utils.HttpContextUtils;
import com.tplink.blog.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author xyd
 * @create 2022-12-04 18:01
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    // 定义切点
    @Pointcut("@annotation(com.tplink.blog.common.aop.LogAnnotation)")
    public void pt() {}

    @Around("pt()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        // 执行原来的方法
        Object obj = point.proceed();
        long end = System.currentTimeMillis();
        // 保存日志
        recordLog(point, end - start);
        return obj;
    }

    // 记录日志
    private void recordLog(ProceedingJoinPoint point, long time) {
        // 获取署名信息对象
        MethodSignature signature = (MethodSignature)point.getSignature();
        // 通过反射获取Method对象
        Method method = signature.getMethod();
        // 获取方法上的注解对象
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        log.info("============================log start==========================");
        log.info("module:{}", annotation.module());
        log.info("operator:{}", annotation.operator());
        // 获取请求方法名
        String className = point.getTarget().getClass().getName();
        String name = signature.getName();
        log.info("request method:{}",className + "." + name + "()");
        // 请求参数
        Object[] args = point.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.info("params:{}", params);

        // 获取ip
        HttpServletRequest request = HttpContextUtils.getServletRequest();
        log.info("ip:{}", IpUtils.getIpAddr(request));
        log.info("执行时间:{}ms", time);
        log.info("============================log stop==========================");


    }
}
