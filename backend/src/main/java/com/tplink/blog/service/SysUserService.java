package com.tplink.blog.service;

import com.tplink.blog.dao.pojo.SysUser;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.UserVo;

/**
 * @author xyd
 * @create 2022-10-12 16:51
 */
public interface SysUserService {
    SysUser findUserById(Long userId);


    SysUser findUser(String account, String pwd);

    Result getUserInfoByToken(String token);

    /**
     * 通过账号寻找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 通过id获取用户
     * @param id
     * @return
     */
    UserVo findUserVoById(Long id);

}
