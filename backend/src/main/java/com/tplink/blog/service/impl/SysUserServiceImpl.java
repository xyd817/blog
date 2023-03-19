package com.tplink.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tplink.blog.dao.mapper.SysUserMapper;
import com.tplink.blog.dao.pojo.SysUser;
import com.tplink.blog.service.SysUserService;
import com.tplink.blog.utils.JWTUtils;
import com.tplink.blog.vo.ErrorCode;
import com.tplink.blog.vo.LoginUserVo;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xyd
 * @create 2022-10-12 16:52
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired(required = false)
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public SysUser findUserById(Long userId) {

        SysUser sysUser = sysUserMapper.selectById(userId);
        if(sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("码神之路");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String pwd) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SysUser::getId, SysUser::getAccount, SysUser::getAvatar,
                SysUser::getNickname)
                .eq(SysUser::getAccount, account)
                .eq(SysUser::getPassword, pwd)
                .last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(lambdaQueryWrapper);
        return sysUser;
    }

    @Override
    public Result getUserInfoByToken(String token) {

        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null) {
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        }
        // 从Redis中获取
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        return Result.success(loginUserVo);
    }

    /**
     * 通过账号寻找用户
     * @param account
     * @return
     */
    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getAccount, account)
                .last("limit 1");
        return sysUserMapper.selectOne(lambdaQueryWrapper);
    }

    /**
     * 保存用户
     * @param sysUser
     */
    @Override
    public void save(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }

    /**
     * 通过id获取用户信息
     * @param id
     * @return
     */
    @Override
    public UserVo findUserVoById(Long id) {
        UserVo userVo = new UserVo();
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null) {
            userVo.setId(String.valueOf(id));
            userVo.setNickname("test");
            userVo.setAvatar("static/user_1.png");
        }
        userVo.setAvatar(sysUser.getAvatar());
        userVo.setNickname(sysUser.getNickname());
        userVo.setId(String.valueOf(id));
        return userVo;
    }
}
