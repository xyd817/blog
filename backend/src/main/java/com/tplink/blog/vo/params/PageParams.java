package com.tplink.blog.vo.params;

import lombok.Data;

/**
 * @author xyd
 * @create 2022-10-12 15:36
 */

@Data
public class PageParams {

    private Integer page;

    private Integer pageSize;

    private String categoryId;

    private String tagId;

    private String year;

    private String month;

    public String getMonth() {
        if (month != null && month.length() == 1) {
            return "0" + month;
        }
        return month;
    }
}
