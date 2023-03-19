package com.tplink.blog.service;

import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.TagVo;

import java.util.List;

/**
 * @author xyd
 * @create 2022-10-12 17:02
 */
public interface TagsService {

    List<TagVo> findTagsByArticleId(Long id);

    List<TagVo> hots(int limit);

    Result listAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
