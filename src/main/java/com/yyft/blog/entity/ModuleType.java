package com.yyft.blog.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * module_type
 *
 * @author fzc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleType implements Serializable {
    /**
     * sn
     */
    private Integer sn;

    /**
     * 名称
     */
    private String typeName;

    /**
     * 排序时间
     */
    private Integer sortId;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}