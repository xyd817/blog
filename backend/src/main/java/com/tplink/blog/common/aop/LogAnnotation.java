package com.tplink.blog.common.aop;

/**
 * @author xyd
 * @create 2022-12-04 17:56
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operator() default "";
}
