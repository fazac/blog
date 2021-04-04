package com.yyft.blog.service;

import com.yyft.blog.entity.YfToken;
import com.yyft.blog.entity.YfUsr;
import com.yyft.blog.mapper.YfTokenMapper;
import com.yyft.blog.mapper.YfUsrMapper;
import com.yyft.blog.util.TokenUtil;
import com.yyft.blog.util.TotpUtil;
import com.yyft.common.model.BizException;
import com.yyft.common.model.ResultCode;
import com.yyft.common.utils.collection.type.Pair;
import com.yyft.common.utils.time.ClockUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<YfUsr> yfUsrs = yfUsrMapper.findByMobile(yfUsr.getPhone());
        if (!yfUsrs.isEmpty()) {
            return false;
        }
        yfUsrMapper.insert(yfUsr);
        return true;
    }

    public Pair<String, String> login(String mobile, String name) {
        YfUsr yfUsr;
        List<YfUsr> yfUsrs = yfUsrMapper.findByMobile(mobile);
        if (!yfUsrs.isEmpty()) {
            yfUsr = yfUsrs.get(0);
        } else {
            return null;
        }
        if (StringUtils.isBlank(yfUsr.getTotpSk())) {
            yfUsr = yfUsrMapper.findByMobilePass(mobile, name);
        } else {
            if (!TotpUtil.verify(yfUsr.getTotpSk(), name)) {
                return null;
            }
        }
        if (yfUsr.getSn() != null) {
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

    public YfUsr selectById(Integer sn) {
        return yfUsrMapper.selectByPrimaryKey(sn);
    }

    public boolean updateById(YfUsr yfUsr) {
        return yfUsrMapper.updateByPrimaryKey(yfUsr) > 0;
    }
}