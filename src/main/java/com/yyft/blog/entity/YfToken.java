package com.yyft.blog.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * yf_token
 *
 * @author fzc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YfToken implements Serializable {
    /**
     * sn
     */
    private Integer sn;

    /**
     * 用户sn
     */
    private Integer userSn;

    /**
     * token
     */
    private String token;

    /**
     * 保证设备唯一性的id, 微信openid或android设备的唯一ID
     */
    private String uid;

    /**
     * 申请token的项目类型, DEVICE_WX等
     */
    private String device;

    /**
     * token失效时间
     */
    private Date expireDate;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * ip地址
     */
    private String ip;

    private String tokenSalt;

    /**
     * 微信的unionid,全局唯一id
     */
    private String unionid;

    /**
     * 三方平台的登陆session, 微信小程序中为session_key
     */
    private String thirdSession;

    private static final long serialVersionUID = 1L;
}