package com.tplink.blog.utils;

import com.tplink.blog.dao.pojo.SysUser;

/**
 * @author xyd
 * @create 2022-11-30 7:29
 */
public class UserThreadLocal {

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    private UserThreadLocal() {

    }

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static void remove() {
        LOCAL.remove();
    }

    public static SysUser get() {
        return LOCAL.get();
    }


}
