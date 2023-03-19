package com.tplink.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tplink.blog.dao.dos.Archives;
import com.tplink.blog.dao.pojo.Article;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.params.PageParams;

import java.util.List;

/**
 * @author xyd
 * @create 2022-10-12 15:28
 */
public interface ArticleMapper extends BaseMapper<Article> {
    List<Archives> listArchives();

    IPage<Article> ListArticles(Page page, Long categoryId, Long tagId, String year, String month);
}
