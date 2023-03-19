package com.tplink.blog.handler;

import com.tplink.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xyd
 * @create 2022-10-24 7:19
 */
@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception ex) {
        ex.printStackTrace();
        return Result.fail(-999, "系统异常");
    }
}
