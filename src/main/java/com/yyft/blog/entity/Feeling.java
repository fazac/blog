package com.yyft.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * feeling
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feeling implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer sn;

    private String marks;

    private String source;
    @TableLogic
    private Boolean isDel;
    private static final long serialVersionUID = 1L;
}