package com.yyft.blog.entity.vo;

import lombok.Data;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/3 11:50
 */
@Data
public class TQColumn {
    private String data;
    private String name;
    private boolean searchable;
    private boolean orderable;
    private TQSearch search;
}
