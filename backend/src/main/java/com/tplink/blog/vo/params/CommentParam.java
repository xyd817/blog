package com.tplink.blog.vo.params;

import lombok.Data;

/**
 * @author xyd
 * @create 2022-12-03 20:48
 */
@Data
public class CommentParam {

    private String articleId;

    private String content;

    private String parent;

    private String toUserId;
}
