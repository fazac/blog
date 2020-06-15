package com.yyft.blog.service;

import com.yyft.blog.entity.YfUsr;
import com.yyft.blog.mapper.YfUsrMapper;
import com.yyft.common.utils.number.RandomUtil;
import com.yyft.common.utils.security.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-15 14:39
 * @Version 1.0
 */
@Slf4j
@Service
public class YfUserService {
    @Autowired
    private YfUsrMapper yfUsrMapper;

    public boolean addUser(YfUsr yfUsr) {
        yfUsr.setSalt(RandomUtil.randomStringRandomLength(10, 13));
        yfUsr.setPassword(MD5Util.passwordEncode(yfUsr.getPassword(), yfUsr.getSalt()));
        yfUsrMapper.insert(yfUsr);
        return true;
    }
}