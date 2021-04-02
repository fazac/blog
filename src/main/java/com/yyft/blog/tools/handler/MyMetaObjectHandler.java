package com.yyft.blog.tools.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yyft.common.utils.time.ClockUtil;
import com.yyft.common.utils.time.DateFormatUtil;
import com.yyft.common.utils.time.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/2 14:18
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", ClockUtil.currentDate(), metaObject);
        this.setFieldValByName("updateTime", ClockUtil.currentDate(), metaObject);
        this.setFieldValByName("version", 1, metaObject);
        this.setFieldValByName("isDel", "0", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", ClockUtil.currentDate(), metaObject);
    }
}
