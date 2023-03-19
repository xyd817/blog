package com.tplink.blog.controller;

import com.tplink.blog.service.TagsService;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.TagVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xyd
 * @create 2022-10-21 7:15
 */
@Api(tags="文章标签相关api")
@RestController
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagsService tagsService;

    /**
     * 获取热门标签列表
     * @return
     */
    @ApiOperation("获取热门标签列表")
    @GetMapping("/hot")
    public Result listHotTags() {
        int limit = 6;
        List<TagVo> hots = tagsService.hots(6);
        return Result.success(hots);
    }

    @ApiOperation(value = "所有文章标签")
    @GetMapping
    public Result listAll() {
        return tagsService.listAll();
    }

    /**
     * 所有标签详情
     * @return
     */
    @ApiOperation(value = "所有标签详情")
    @GetMapping("detail")
    public Result findAllDetail() {
        return tagsService.findAllDetail();
    }

    @ApiOperation(value = "标签文章列表")
    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id) {
        return  tagsService.findDetailById(id);
    }



}
