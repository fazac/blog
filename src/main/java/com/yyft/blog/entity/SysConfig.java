package com.yyft.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/6 9:30
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysConfig implements Serializable {

    @TableId(type = IdType.AUTO)
    public Integer sn;

    private String name;

    private String content;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String isDel;
}
