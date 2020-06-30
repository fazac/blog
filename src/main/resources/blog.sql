# drop table if exists `yf_usr`;
# create table yf_usr
# (
#     `sn`       int(11) auto_increment comment 'sn',
#     `name`     varchar(10) comment '名称',
#     `phone`    varchar(11) comment '手机号',
#     `email`    varchar(32) comment 'emial',
#     `img`      varchar(128) comment '图像',
#     `uid`      varchar(64) COMMENT '唯一id',
#     `password` varchar(24) comment '密码,md5加密',
#     `salt`     varchar(13),
#     PRIMARY key (`sn`)
# );
#
# create table yf_token
# (
#     `sn`            int(11) auto_increment comment 'sn',
#     `user_sn`       int(11) comment '用户sn',
#     `token`         varchar(128) comment 'token',
#     `uid`           varchar(64) COMMENT '保证设备唯一性的id, 微信openid或android设备的唯一ID',
#     `device`        varchar(32) COMMENT '申请token的项目类型, DEVICE_WX等',
#     `expire_date`   datetime COMMENT 'token失效时间',
#     `update_time`   datetime COMMENT '更新时间',
#     `ip`            varchar(40) COMMENT 'ip地址',
#     `token_salt`    varchar(32),
#     `unionid`       varchar(64) COMMENT '微信的unionid,全局唯一id',
#     `third_session` varchar(64) COMMENT '三方平台的登陆session, 微信小程序中为session_key',
#     PRIMARY key (`sn`)
# );
#
# alter table yf_token
#     add column `public_key` varchar(512) comment '公钥';
# alter table yf_token
#     MODIFY COLUMN `token` varchar(1024) DEFAULT NULL COMMENT 'token';

CREATE TABLE `blog`
(
    `blog_id`        int(11)      NOT NULL AUTO_INCREMENT COMMENT 'blogID',
    `title`          varchar(128) NOT NULL COMMENT 'blog标题',
    `show_cover_pic` varchar(256)          DEFAULT NULL COMMENT '封面图路径',
    `digest`         varchar(256)          DEFAULT NULL COMMENT 'blog摘要',
    `content`        text COMMENT 'blog内容',
    `author`         varchar(30)           DEFAULT NULL COMMENT 'blog创建人',
    `create_time`    datetime              DEFAULT NULL COMMENT '创建时间',
    `publish_time`   datetime              DEFAULT NULL COMMENT '发布时间',
    `update_time`    datetime              DEFAULT NULL COMMENT '更新时间',
    `module_type`    int(11)               DEFAULT NULL COMMENT '所属模块类型',
    `status`         varchar(20)  NOT NULL DEFAULT 'CREATE' COMMENT 'blog状态  CREATE-已创建,  PUBLISH-已发表, INACTIVE-已作废',
    `sort_id`        int(3)       NOT NULL DEFAULT '999' COMMENT '排序权重  0最前  越大越后',
    PRIMARY KEY (`blog_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='博客';

create table `label`
(
    `label_id` int(11)     not null auto_increment comment 'labelID',
    `name`     varchar(20) not null comment '标签名',
    PRIMARY KEY (`label_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='标签';

create table `blog_label`
(
    `id`       int(11) auto_increment comment 'sn',
    `blog_id`  int(11) not null comment 'blogID',
    `label_id` int(11) not null comment 'labelID',
    `count`    int(11) comment '点击次数',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='博客标签';

create table module_type
(
    `sn`          int(11) auto_increment comment 'sn',
    `type_name`   varchar(20) comment '名称',
    `sort_id`     int(3) comment '排序时间',
    `create_time` datetime comment '创建时间',
    PRIMARY KEY (`sn`)
);


create table feeling
(
    `sn`    int(11) auto_increment,
    `marks` varchar(64),
    PRIMARY KEY (`sn`)
);