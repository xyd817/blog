package com.tplink.blog.service;

import com.tplink.blog.vo.CategoryVo;
import com.tplink.blog.vo.Result;

/**
 * @author xyd
 * @create 2022-12-01 7:13
 */
public interface CategoryService {

    CategoryVo findCategoryById(Long id);

    /**
     * 返回所有文章类别
     * @return
     */
    Result findAll() ;

    /**
     * 所有文章分类详情
     * @return
     */
    Result findAllDetail();

    /**
     * 分类文章列表
     * @param id
     * @return
     */
    Result findCategoriesById(Long id);

}
