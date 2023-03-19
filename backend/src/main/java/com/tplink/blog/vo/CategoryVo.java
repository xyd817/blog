package com.tplink.blog.vo;

import lombok.Data;

/**
 * @author xyd
 * @create 2022-11-30 22:01
 */
@Data
public class CategoryVo {

    private String id;

    private String avatar;

    private String CategoryName;

    private String description;

    private Integer articles;
}
