package com.tplink.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tplink.blog.dao.pojo.Tag;
import com.tplink.blog.vo.TagVo;

import java.util.List;

/**
 * @author xyd
 * @create 2022-10-12 15:30
 */
public interface TagMapper extends BaseMapper<Tag> {
     List<Tag> findTagsByArticleId(Long id);
     List<Long> findHotsTagIds(int limit);
     List<Tag> findTagsByTagIds(List<Long> tagIds);

    List<TagVo> findAllDetail();
}
