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

CREATE TABLE `blog`
(
    `blog_id`        int(11)      NOT NULL AUTO_INCREMENT COMMENT 'blogID',
    `title`          varchar(128) NOT NULL COMMENT 'blog标题',
    `show_cover_pic` varchar(256)          DEFAULT NULL COMMENT '封面图路径',
    `digest`         varchar(256)          DEFAULT NULL COMMENT 'blog摘要',
    `content`        text COMMENT 'blog内容',
    `create_time`    datetime              DEFAULT NULL COMMENT '创建时间',
    `publish_time`   datetime              DEFAULT NULL COMMENT '发布时间',
    `update_time`    datetime              DEFAULT NULL COMMENT '更新时间',
    `status`         varchar(20)  NOT NULL DEFAULT 'CREATE' COMMENT 'blog状态  CREATE-已创建,  PUBLISH-已发表, INACTIVE-已作废',
    `sort_id`        int(3)       NOT NULL DEFAULT '999' COMMENT '排序权重  0最前  越大越后',
    `labelids`       varchar(256) COMMENT '标签ids',
    `view_count`     int(6) COMMENT '浏览次数',
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


create table feeling
(
    `sn`    int(11) auto_increment,
    `marks` varchar(64),
    PRIMARY KEY (`sn`)
);

create table `comment`
(
    `sn`      int(11) auto_increment,
    `content` VARCHAR(256),
    PRIMARY key (`sn`)
);

alter table blog
    add column `version` int(4) comment '版本(乐观锁)';
alter table label
    add column `type` varchar(2) comment '类型（0 分类 ，1日期）';

alter table blog
    MODIFY COLUMN `content` mediumtext NOT NULL COMMENT '博客内容';
alter table `label`
    add column `status` varchar(2) comment '状态 0 失效 ，1有效' default '1';
alter table feeling
    modify column `marks` varchar(256);
alter table feeling
    add column `source` varchar(48);

-- 2021-03-31
alter table `label`
    modify column `name` varchar(256);
-- 2021-04-02
alter table blog
    add column `is_del` tinyint(2);

alter table comment
    add column `is_del`      tinyint(2),
    add column `status`      varchar(2) comment '0 待审核，1已审核',
    add column `email`       varchar(40),
    add column `name`        varchar(20),
    add column `create_time` datetime;

alter table feeling
    add column `is_del` tinyint(2);

-- 2021-04-04(√)
alter table label
    modify column `status` varchar(2) default '0' comment '0有效 1无效';
alter table `yf_usr`
    add column `totp_sk` varchar(32);

-- 2021-04-05
alter table `comment`
    add column `sid`      int(9) comment '回复id',
    add column `bid`      int(9) comment 'blogid',
    add column `type`     varchar(2) comment '0评论 ,1 回复',
    add column `re_email` varchar(2) comment '0无需email回复,1需email回复';
alter table `comment`
    add column `viewable` varchar(2) comment '是否可见';
alter table `comment`
    add column `site` varchar(20) comment '站点';

alter table `comment`
    modify column `re_email` tinyint(2) comment '0无需email回复,1需email回复';

create table `sys_config`
(
    `sn`      int primary key auto_increment,
    `name`    varchar(30) comment '配置名',
    `content` varchar(256) comment '内容',
    `is_del`  varchar(2) comment '是否已删'
);

alter table `yf_usr`
    add column `totp_img` varchar(256) comment '二维码地址';

alter table label change column `status` `is_del` varchar(2);
