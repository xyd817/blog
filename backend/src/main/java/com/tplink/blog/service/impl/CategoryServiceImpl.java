package com.tplink.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tplink.blog.dao.mapper.CategoryMapper;
import com.tplink.blog.dao.pojo.Category;
import com.tplink.blog.service.CategoryService;
import com.tplink.blog.vo.CategoryVo;
import com.tplink.blog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyd
 * @create 2022-12-01 7:15
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long id) {
        Category category = categoryMapper.selectById(id);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    /**
     * 返回所有文章类别
     * @return
     */
    @Override
    public Result findAll() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(categories));
    }

    /**
     * 所有文章分类详情
     * @return
     */
    @Override
    public Result findAllDetail() {
//        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        List<CategoryVo> categoryVos = categoryMapper.findAllDetail();
        return Result.success(categoryVos);
    }

    /**
     * 分类文章列表
     * @param id
     * @return
     */
    @Override
    public Result findCategoriesById(Long id) {
        Category category = categoryMapper.selectById(id);
        return Result.success(copy(category));
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        List<CategoryVo> categoryVos = new ArrayList<>();
        for (Category category : categories) {
            categoryVos.add(copy(category));
        }
        return categoryVos;
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        categoryVo.setId(category.getId().toString());
        return categoryVo;
    }
}
