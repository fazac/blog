package com.yyft.blog.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * blog_label
 *
 * @author fzc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogLabel implements Serializable {
    /**
     * sn
     */
    private Integer id;

    /**
     * blogID
     */
    private Integer blogId;

    /**
     * labelID
     */
    private Integer labelId;

    /**
     * 点击次数
     */
    private Integer count;

    private static final long serialVersionUID = 1L;
}