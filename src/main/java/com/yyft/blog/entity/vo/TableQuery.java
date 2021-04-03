package com.yyft.blog.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/3 11:46
 */
@Data
public class TableQuery {
    private Long start;
    private Long length;
    private Long draw;
    private List<TQColumn> columns;
    private List<TQOrder> order;
    private TQSearch search;
}
