package com.yyft.blog.entity.query;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-22 10:29
 * @Version 1.0
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class BlogQuery {
    @Getter
    @Setter
    private Integer page;
    @Getter
    @Setter
    private Integer pageSize;
    @Getter
    @Setter
    private Integer type;


}