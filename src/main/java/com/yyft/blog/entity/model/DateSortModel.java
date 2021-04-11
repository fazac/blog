package com.yyft.blog.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/11 10:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateSortModel {
    private String date;
    private Long count;
}
