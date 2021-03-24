package com.yyft.blog.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * comment
 *
 * @author fzc
 */
@Data
public class Comment implements Serializable {
    private Integer sn;

    private String content;

    private static final long serialVersionUID = 1L;
}