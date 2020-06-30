package com.yyft.blog.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * blog
 *
 * @author fzc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog implements Serializable {
    /**
     * blogID
     */
    private Integer blogId;

    /**
     * blog标题
     */
    private String title;

    /**
     * 封面图路径
     */
    private String showCoverPic;

    /**
     * blog摘要
     */
    private String digest;

    /**
     * blog内容
     */
    private String content;

    /**
     * blog创建人
     */
    private String author;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 所属模块类型
     */
    private Integer moduleType;

    /**
     * blog状态  CREATE-已创建,  PUBLISH-已发表, INACTIVE-已作废
     */
    private String status;

    /**
     * 排序权重  0最前  越大越后
     */
    private Integer sortId;

    private static final long serialVersionUID = 1L;

    /**
     * 模块类型名称
     */
    private String moduleTypeName;
}