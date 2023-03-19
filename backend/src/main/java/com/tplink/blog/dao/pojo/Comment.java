package com.tplink.blog.dao.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author xyd
 * @create 2022-12-03 17:49
 */
@Data
public class Comment {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}
