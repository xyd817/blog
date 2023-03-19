package com.tplink.blog.controller;

import com.tplink.blog.common.aop.LogAnnotation;
import com.tplink.blog.common.cache.Cache;
import com.tplink.blog.service.ArticleService;
import com.tplink.blog.vo.ArticleVo;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.params.ArticleParam;
import com.tplink.blog.vo.params.ArticleUpdateParam;
import com.tplink.blog.vo.params.PageParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xyd
 * @create 2022-10-12 15:32
 */
@Api(tags="文章相关api")
@RestController
@RequestMapping("articles")
public class ArticleController {


    @Autowired
    private ArticleService articleService;
    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @ApiOperation("文章列表")
    @PostMapping("findArticlePage")
    @LogAnnotation(module = "文章", operator = "文章列表")
    public Result listArticles(@RequestBody PageParams pageParams) {
        System.out.println(">>>>>>>>>>>>>>" + pageParams);
        return articleService.listArticle(pageParams);
    }

    /**
     * 最热文章列表
     * @return
     */
    @ApiOperation("最热文章列表")
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页最新文章
     * @return
     */
    @ApiOperation("首页最新文章")
    @PostMapping("new")
    public Result newArticles() {
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @ApiOperation("文章归档")
    @PostMapping("listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }

    /**
     * 通过文章id获取文章详情
     * @param id
     * @return
     */
    @ApiOperation(value = "获取文章详情")
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id")  Long id) {
        ArticleVo articleVo = articleService.findArticleById(id);
        System.out.println("******************************************");
        return Result.success(articleVo);
    }

    @ApiOperation(value = "发布文章")
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }

    @ApiOperation(value = "文章修改")
    @PostMapping("updateArticle")
    public Result updateArticle(@RequestBody ArticleUpdateParam articleUpdateParam) {
        System.out.println(articleUpdateParam);
        return articleService.updateArticle(articleUpdateParam);
    }



}
