package com.tplink.blog.common.cache;

import java.lang.annotation.*;

/**
 * @author xyd
 * @create 2022-12-10 9:40
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1 * 60 * 1000;

    String name() default "";

}
