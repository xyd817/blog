package com.tplink.blog.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xyd
 * @create 2022-12-03 19:34
 */
@Data
public class CommentVo {

    private String id;

    private UserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}
