package com.tplink.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tplink.blog.dao.pojo.Category;
import com.tplink.blog.vo.CategoryVo;

import java.util.List;

/**
 * @author xyd999
 * @create 2022-11-30 22:12
 */
public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryVo> findAllDetail();
}
