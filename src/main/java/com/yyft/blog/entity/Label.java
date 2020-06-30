package com.yyft.blog.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * label
 *
 * @author fzc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Label implements Serializable {
    /**
     * labelID
     */
    private Integer labelId;

    /**
     * 标签名
     */
    private String name;

    private static final long serialVersionUID = 1L;
}