package com.tplink.blog.dao.pojo;

import lombok.Data;

/**
 * @author xyd
 * @create 2022-12-04 11:37
 */
@Data
public class ArticleTag {
    private Long id;

    private Long articleId;

    private Long tagId;
}
