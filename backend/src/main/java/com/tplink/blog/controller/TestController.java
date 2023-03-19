package com.tplink.blog.controller;

import com.tplink.blog.common.aop.LogAnnotation;
import com.tplink.blog.dao.pojo.SysUser;
import com.tplink.blog.utils.UserThreadLocal;
import com.tplink.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.slf4j.Log4jLogger;
import org.apache.logging.slf4j.Log4jLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xyd
 * @create 2022-11-29 22:01
 */
@Api(tags="测试ThreadLocal相关api")
@RestController
@RequestMapping("test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 测试ThreadLocal
     * @return
     */
    @ApiOperation("测试ThreadLocal")
    @GetMapping
    public Result test() {

        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }

    @GetMapping("logAOP")
    @LogAnnotation(module = "AOP测试", operator = "进行日志AOP")
    public void testAOP(@RequestParam String param1) {

        logger.info("===============进行日志AOP================");
    }


}
