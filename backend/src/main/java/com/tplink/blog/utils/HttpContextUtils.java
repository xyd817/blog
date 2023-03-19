package com.tplink.blog.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xyd
 * @create 2022-12-04 19:21
 */
public class HttpContextUtils {

    public static HttpServletRequest getServletRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
