package com.yyft.blog.service;

import com.yyft.blog.mapper.FeelingMapper;
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

    public String getLastFeeling() {
        Integer maxid = feelingMapper.getLastId();
        if (maxid != null) {
            return feelingMapper.selectByPrimaryKey(maxid).getMarks();
        } else {
            return "";
        }
    }
}