package com.yyft.blog.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/3 12:09
 */

@Data
public class TableResult<T> implements Serializable {
    private Long draw;
    private Long recordsTotal;
    private Long recordsFiltered;
    private List<T> data;
    private String error;
}