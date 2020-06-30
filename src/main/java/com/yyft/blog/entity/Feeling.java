package com.yyft.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * feeling
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feeling implements Serializable {
    private Integer sn;

    private String marks;

    private static final long serialVersionUID = 1L;
}