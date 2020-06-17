package com.yyft.blog.service;

import com.yyft.blog.entity.Constants;
import com.yyft.blog.entity.YfToken;
import com.yyft.blog.entity.YfUsr;
import com.yyft.blog.mapper.YfTokenMapper;
import com.yyft.blog.mapper.YfUsrMapper;
import com.yyft.blog.util.TokenUtil;
import com.yyft.common.model.BizException;
import com.yyft.common.model.ResultCode;
import com.yyft.common.utils.base.ObjectUtil;
import com.yyft.common.utils.collection.type.Pair;
import com.yyft.common.utils.number.RandomUtil;
import com.yyft.common.utils.security.MD5Util;
import com.yyft.common.utils.time.ClockUtil;
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
    @Autowired
    private YfTokenMapper yfTokenMapper;
    @Autowired
    private TokenUtil tokenUtil;

    public boolean register(YfUsr yfUsr) {
        yfUsrMapper.insert(yfUsr);
        return true;
    }

    public Pair<String, String> login(String mobile, String name) {
        YfUsr yfUsr = yfUsrMapper.findByMobilePass(mobile, name);
        if (yfUsr != null && yfUsr.getSn() != null) {
            YfToken yfToken = yfTokenMapper.getByUserId(yfUsr.getSn(), ClockUtil.currentDate());
            if (yfToken != null && yfToken.getSn() != null) {
                return new Pair<>(yfToken.getToken(), yfToken.getPublicKey());
            } else {
                return tokenUtil.createToken(yfUsr);
            }
        } else {
            throw new BizException(ResultCode.USER_LOGIN_ERROR.message());
        }
    }
}