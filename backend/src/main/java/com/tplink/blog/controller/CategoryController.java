package com.tplink.blog.controller;

import com.tplink.blog.service.CategoryService;
import com.tplink.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xyd
 * @create 2022-12-04 10:43
 */
@Api(tags="文章类别相关接口")
@RestController
@RequestMapping("categories")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "文章类别列表")
    @GetMapping
    public Result listCategory() {
        return Result.success(categoryService.findAll());
    }

    @ApiOperation(value = "文章分类详情")
    @GetMapping("detail")
    public Result categoriesDetail() {
        return categoryService.findAllDetail();
    }

    @ApiOperation(value = "文类文章列表")
    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long id) {
        return categoryService.findCategoriesById(id);
    }
}
