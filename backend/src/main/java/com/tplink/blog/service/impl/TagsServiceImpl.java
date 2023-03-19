package com.tplink.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tplink.blog.dao.mapper.TagMapper;
import com.tplink.blog.dao.pojo.Tag;
import com.tplink.blog.service.TagsService;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.TagVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xyd
 * @create 2022-10-12 17:03
 */
@Service
public class TagsServiceImpl implements TagsService {

    @Autowired(required = false)
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long id) {
        List<Tag> tags = tagMapper.findTagsByArticleId(id);
        List<TagVo> tagVoList = copyList(tags);
        return tagVoList;
    }

    /**
     * 获取热门标签
     * @param limit
     * @return
     */
    @Override
    public List<TagVo> hots(int limit) {
        List<Long> hotsTagIds = tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(hotsTagIds)){
            return Collections.emptyList();
        }
        List<Tag> tagList = tagMapper.findTagsByTagIds(hotsTagIds);
        List<TagVo> tagVos = copyList(tagList);
        return tagVos;
    }

    /**
     * 获取所有文章标签
     * @return
     */
    @Override
    public Result listAll() {
        List<Tag> tags = tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tags));
    }

    @Override
    public Result findAllDetail() {
        List<TagVo> tagVos = tagMapper.findAllDetail();

        return Result.success(tagVos);
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }

    public List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    public TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        tagVo.setId(tag.getId().toString());
        return tagVo;
    }
}
