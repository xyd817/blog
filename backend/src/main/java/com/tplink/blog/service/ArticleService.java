package com.tplink.blog.service;

import com.tplink.blog.vo.ArticleBodyVo;
import com.tplink.blog.vo.ArticleVo;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.params.ArticleParam;
import com.tplink.blog.vo.params.ArticleUpdateParam;
import com.tplink.blog.vo.params.PageParams;

/**
 * @author xyd
 * @create 2022-10-12 15:54
 */
public interface ArticleService {
    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit) ;


    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 获取文章详情
     * @param articleId
     * @return
     */
    ArticleBodyVo findArticleBody(Long articleId);

    /**
     * 通过文章id获取文章
     * @param id
     * @return
     */
    ArticleVo findArticleById(Long id);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);

    /**
     * 发布文章修改
     * @param articleUpdateParam
     * @return
     */
    Result updateArticle(ArticleUpdateParam articleUpdateParam);
}
