package com.tplink.blog.controller;

import com.tplink.blog.utils.QiniuUtils;
import com.tplink.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author xyd
 * @create 2022-12-06 21:37
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping("")
    public Result upload(@RequestParam("image")MultipartFile file) {

        System.out.println("===================图片:" + file);

        // 文件名
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".");

        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return Result.success(qiniuUtils.url + fileName);
        }
        return Result.fail(20001, "文件上传失败");

    }
}
