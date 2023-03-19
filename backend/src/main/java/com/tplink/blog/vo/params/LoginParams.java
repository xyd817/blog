package com.tplink.blog.vo.params;

import lombok.Data;

/**
 * @author xyd
 * @create 2022-11-06 8:46
 */
@Data
public class LoginParams {

    private String account;

    private String password;

    private String nickname;
}
