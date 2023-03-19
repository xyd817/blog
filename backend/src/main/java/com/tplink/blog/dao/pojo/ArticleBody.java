package com.tplink.blog.dao.pojo;

import lombok.Data;
import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;

/**
 * @author xyd
 * @create 2022-11-30 22:07
 */
@Data
public class ArticleBody {

    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;
}
