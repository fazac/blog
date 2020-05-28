drop table if exists  `yf_usr`;
create table yf_usr(
    `sn` int(11) auto_increment comment 'sn',
    `name` varchar(10) comment '名称',
    `phone` varchar(11) comment '手机号',
    `email` varchar(32) comment 'emial',
    `img` varchar(128) comment '图像',
    `uid` varchar(64) COMMENT '唯一id',
    PRIMARY key (`sn`)
);
