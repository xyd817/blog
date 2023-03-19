package com.tplink.blog.controller;

import com.tplink.blog.service.LoginService;
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
 * @create 2022-11-28 21:03
 */
@Api(tags="登出相关api")
@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    /**
     * 登出
     * @param token
     * @return
     */
    @ApiOperation(value = "登出")
    @GetMapping("")
    public Result logout(@RequestHeader("Authorization") String token) {
        System.out.println("================>>>>>>>>>>>>>>>>>>>>>>>>>");
        return loginService.logout(token);
    }

}
