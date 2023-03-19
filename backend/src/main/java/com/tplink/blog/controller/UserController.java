package com.tplink.blog.controller;

import com.tplink.blog.service.SysUserService;
import com.tplink.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xyd
 * @create 2022-11-21 7:09
 */
@Api(tags="用户相关api")
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 依据token获取当前用户
     * @param token
     * @return
     */
    @ApiOperation(value = "依据token获取当前用户")
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        System.out.println("token=====" + token);
        return sysUserService.getUserInfoByToken(token);
    }
}
