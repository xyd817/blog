package com.tplink.blog.vo.params;

import com.tplink.blog.vo.CategoryVo;
import com.tplink.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author xyd
 * @create 2022-12-04 11:25
 */
@Data
public class ArticleParam {
    private String id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
