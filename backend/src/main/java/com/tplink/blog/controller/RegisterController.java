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
 * @create 2022-11-28 22:00
 */
@Api(tags="注册相关api")
@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    /**
     * 注册
     * @param loginParams
     * @return
     */
    @ApiOperation(" 注册")
    @PostMapping
    public Result register(@RequestBody LoginParams loginParams) {
        return loginService.register(loginParams);
    }

}
