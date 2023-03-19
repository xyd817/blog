package com.tplink.blog.common.cache;

import com.alibaba.fastjson.JSON;
import com.tplink.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author xyd
 * @create 2022-12-10 9:44
 */
@Component
@Aspect
@Slf4j
public class CacheAspect {

    // 定义切点
    @Pointcut("@annotation(com.tplink.blog.common.cache.Cache)")
    public void pt() {};

    // 注入Redis
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 环绕通知
    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) {

        try {
            // 获取切点署名信息对象
            Signature signature = pjp.getSignature();
            // 类名
            String className = pjp.getTarget().getClass().getSimpleName();
            // 方法名
            String methodName = signature.getName();

            Class[] paramsType = new Class[pjp.getArgs().length];
            Object[] args = pjp.getArgs();
            // 参数
            String params = "";
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    paramsType[i] = args[i].getClass();
                } else {
                    paramsType[i] = null;
                }
            }

            if (StringUtils.isNotBlank(params)) {
                // 加密 防止key过长以及字符转义获取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            // 获取Method对象
            Method method = signature.getDeclaringType().getMethod(methodName, paramsType);
            // 获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            // 缓存过期时间
            long expire = annotation.expire();
            // 缓存名称
            String name = annotation.name();
            // 先从Redis获取
            String redisKey = name + "::" + className + "::" + methodName + "::" + params;
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotBlank(redisValue)) {
                log.info("走了缓存：{}，{}", className, methodName);
                return JSON.parseObject(redisValue, Result.class);
            }
            // 执行切入方法
            Object proceed = pjp.proceed();
            // 存入redis
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(proceed), expire);
            log.info("存入了缓存， {}， {}", className, methodName);
            return proceed;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return Result.fail(-999, "系统错误");

    }




}
