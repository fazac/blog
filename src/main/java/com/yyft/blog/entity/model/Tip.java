package com.yyft.blog.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/3/31 23:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tip {
    private String title;
    private String msg;
}
