package com.tplink.blog.handler;

import com.alibaba.fastjson.JSON;
import com.tplink.blog.dao.pojo.SysUser;
import com.tplink.blog.service.LoginService;
import com.tplink.blog.utils.UserThreadLocal;
import com.tplink.blog.vo.ErrorCode;
import com.tplink.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author xyd
 * @create 2022-11-29 21:28
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    /**
     * 登陆拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("******************************************");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info("===================request start================");
        String requestURI = request.getRequestURI();
        log.info("request uri: {}", requestURI);
        log.info("request method: {}", request.getMethod());
        log.info("token:{}", token);
        log.info("===================request end================");

        if (token == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSON(result));
            return false;
        }

        SysUser sysUser = loginService.checkToken(token);

        if (sysUser == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSON(result));
            return false;
        }
        UserThreadLocal.put(sysUser);
        return true;
    }

    /**
     * 退出后将用户信息从ThreadLocal中移除，避免内存泄露
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
