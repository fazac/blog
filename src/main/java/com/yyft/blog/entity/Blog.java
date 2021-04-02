package com.yyft.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
    @TableId(type = IdType.AUTO)
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
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

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
     * 分类id
     */
    private String labelids;

    /**
     * 浏览数
     */
    private String viewCount;
    /**
     * 是否已作废
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String isDel;

    /**
     * 版本号
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
}