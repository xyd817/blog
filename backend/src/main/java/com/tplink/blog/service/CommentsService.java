package com.tplink.blog.service;

import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.params.CommentParam;

/**
 * @author xyd
 * @create 2022-12-03 18:05
 */
public interface CommentsService {
    /**
     * 通过文章id获取文章的评论
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    /**
     * 新建Comment
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
