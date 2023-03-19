package com.tplink.blog.service;

import com.tplink.blog.dao.pojo.SysUser;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.params.LoginParams;

/**
 * @author xyd
 * @create 2022-11-06 8:46
 */
public interface LoginService {

    /**
     * 登录
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);


    /**
     * 登出
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParams
     * @return
     */
    Result register(LoginParams loginParams);

    /**
     * 验证Token
     * @param token
     * @return
     */
    SysUser checkToken(String token);
}
