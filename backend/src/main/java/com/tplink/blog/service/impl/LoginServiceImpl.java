package com.tplink.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.tplink.blog.dao.pojo.SysUser;
import com.tplink.blog.service.LoginService;
import com.tplink.blog.service.SysUserService;
import com.tplink.blog.utils.JWTUtils;
import com.tplink.blog.vo.ErrorCode;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xyd
 * @create 2022-11-06 8:48
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private static String slat = "xiaoyongde@!#";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     *
     * @param loginParams
     * @return
     */
    @Override
    public Result login(LoginParams loginParams) {

        // 获取账号密码
        String account =loginParams.getAccount();
        String pwd = loginParams.getPassword();

        // 判断账号密码是否为空
        if (StringUtils.isBlank(account) || StringUtils.isBlank(pwd)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        // md5加密
        String password = DigestUtils.md5Hex(pwd + slat);
        // 判断是否存在当前用户
        SysUser user = sysUserService.findUser(account, password);
        if (user == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        // 登录成功， 使用JWT生成token返回，并保存在Redis中
        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(user), 1, TimeUnit.DAYS);

        return Result.success(token);
    }

    /**
     * 登出
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    /**
     * 注册
     * @param loginParams
     * @return
     */
    @Override
    public Result register(LoginParams loginParams) {
        // 取出参数
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();

        // 验证参数是否为空
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        // 判断用户是否存在
        SysUser user = sysUserService.findUserByAccount(account);
        if(user != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIT.getCode(), ErrorCode.ACCOUNT_EXIT.getMsg());
        }

        // 注册
        SysUser sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + slat));
        sysUser.setNickname(nickname);
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("static/user/user_1.png");
        sysUser.setAdmin(1);
        sysUser.setDeleted(0);
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);

        // 设置Token
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);

        return Result.success(token);
    }

    /**
     * 验证token，并返回用户信息
     * @param token
     * @return
     */
    @Override
    public SysUser checkToken(String token) {
        if (token == null) {
            return null;
        }

        Map<String, Object> payload = JWTUtils.checkToken(token);
        if (payload == null) {
            return null;
        }
        // 从Redis获取用户信息
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (userJson == null) {
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }


    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("xyd" + slat));
    }
}
