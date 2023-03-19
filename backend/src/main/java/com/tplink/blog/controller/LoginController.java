package com.tplink.blog.controller;

import com.tplink.blog.service.LoginService;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.params.LoginParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xyd
 * @create 2022-11-06 9:22
 */
@Api(tags="登录相关api")
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @param loginParams
     * @return
     */
    @ApiOperation(value = "登录")
    @PostMapping
    public Result login(@RequestBody LoginParams loginParams) {
        return loginService.login(loginParams);
    }


}
