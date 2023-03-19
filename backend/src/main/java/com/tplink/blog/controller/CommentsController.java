package com.tplink.blog.controller;

import com.tplink.blog.service.CommentsService;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.params.CommentParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xyd
 * @create 2022-12-03 18:01
 */
@Api(tags = "文章评论相关接口")
@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @ApiOperation(value = "评论列表")
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id) {
        return commentsService.commentsByArticleId(id);
    }

    @ApiOperation(value = "新建评论")
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam) {
        System.out.println(commentParam);
        return commentsService.comment(commentParam);
    }
}
