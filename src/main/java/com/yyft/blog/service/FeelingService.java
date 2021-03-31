package com.yyft.blog.service;

import com.yyft.blog.entity.Feeling;
import com.yyft.blog.mapper.FeelingMapper;
import com.yyft.common.utils.number.MathUtil;
import com.yyft.common.utils.number.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-23 15:46
 * @Version 1.0
 */
@Slf4j
@Service
public class FeelingService {
    @Autowired
    private FeelingMapper feelingMapper;

    public Feeling getLastFeeling() {
        Integer maxid = feelingMapper.getLastId();
        if (maxid != null) {
            return feelingMapper.selectByPrimaryKey(RandomUtil.nextInt(1, maxid + 1));
        } else {
            return new Feeling();
        }
    }
}