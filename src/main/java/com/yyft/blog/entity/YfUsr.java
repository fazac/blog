package com.yyft.blog.entity;

import java.io.Serializable;

import lombok.*;

/**
 * yf_usr
 *
 * @author fzc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YfUsr implements Serializable {
    /**
     * sn
     */
    private Integer sn;

    /**
     * 名称
     */
    @NonNull
    private String name;

    /**
     * 手机号
     */
    @NonNull
    private String phone;

    /**
     * emial
     */
    private String email;

    /**
     * 图像
     */
    private String img;

    /**
     * 唯一id
     */
    private String uid;

    /**
     * 密码,md5加密
     */
    @NonNull
    private String password;

    private String salt;

    private static final long serialVersionUID = 1L;
}