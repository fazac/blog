package com.yyft.blog.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * comment
 *
 * @author fzc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer sn;

    private String content;
    @TableLogic
    private Boolean is_del;

    private String status;

    private String email;

    private String name;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}