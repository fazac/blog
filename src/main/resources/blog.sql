drop table if exists `yf_usr`;
create table yf_usr
(
    `sn`       int(11) auto_increment comment 'sn',
    `name`     varchar(10) comment '名称',
    `phone`    varchar(11) comment '手机号',
    `email`    varchar(32) comment 'emial',
    `img`      varchar(128) comment '图像',
    `uid`      varchar(64) COMMENT '唯一id',
    `password` varchar(24) comment '密码,md5加密',
    `salt`     varchar(13),
    PRIMARY key (`sn`)
);

create table yf_token
(
    `sn`            int(11) auto_increment comment 'sn',
    `user_sn`       int(11) comment '用户sn',
    `token`         varchar(128) comment 'token',
    `uid`           varchar(64) COMMENT '保证设备唯一性的id, 微信openid或android设备的唯一ID',
    `device`        varchar(32) COMMENT '申请token的项目类型, DEVICE_WX等',
    `expire_date`   datetime COMMENT 'token失效时间',
    `update_time`   datetime COMMENT '更新时间',
    `ip`            varchar(40) COMMENT 'ip地址',
    `token_salt`    varchar(32),
    `unionid`       varchar(64) COMMENT '微信的unionid,全局唯一id',
    `third_session` varchar(64) COMMENT '三方平台的登陆session, 微信小程序中为session_key',
    PRIMARY key (`sn`)
);

alter table yf_token
    add column `public_key` varchar(512) comment '公钥';
alter table yf_token
    MODIFY COLUMN `token` varchar(1024) DEFAULT NULL COMMENT 'token';