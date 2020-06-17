package com.yyft.blog.service;

import com.yyft.blog.entity.YfToken;
import com.yyft.blog.mapper.YfTokenMapper;
import com.yyft.common.model.BizException;
import com.yyft.common.model.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-16 14:47
 * @Version 1.0
 */
@Slf4j
@Service
public class YfTokenService {
    @Autowired
    private YfTokenMapper yfTokenMapper;

    public boolean addToken(YfToken yfToken) {
        return yfTokenMapper.insert(yfToken) > 0;
    }


    public String findPublicKeyByToken(String token) {
        String res = yfTokenMapper.findPublicKeyByToken(token);
        if (StringUtils.isEmpty(res)) {
            throw new BizException(ResultCode.PERMISSION_TOKEN_INVALID.message());
        }
        return res;
    }
}