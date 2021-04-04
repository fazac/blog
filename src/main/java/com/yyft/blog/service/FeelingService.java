package com.yyft.blog.service;

import com.yyft.blog.entity.Feeling;
import com.yyft.blog.entity.Label;
import com.yyft.blog.mapper.FeelingMapper;
import com.yyft.common.utils.number.MathUtil;
import com.yyft.common.utils.number.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-23 15:46
 * @Version 1.0
 */
@Slf4j
@Service
public class FeelingService {
    private FeelingMapper feelingMapper;

    public Feeling getLastFeeling() {
        Integer maxid = feelingMapper.getLastId();
        if (maxid != null) {
            while (true) {
                Feeling f = feelingMapper.selectByPrimaryKey(RandomUtil.nextInt(1, maxid + 1));
                if (f != null) {
                    return f;
                }
            }
        } else {
            return new Feeling();
        }
    }

    public List<Feeling> findAllFeels() {
        return feelingMapper.selectList(null);
    }

    public boolean createFeeling(Feeling feeling) {
        return feelingMapper.insert(feeling) == 1;
    }

    public boolean deleteFeelings(List<Integer> ids) {
        return feelingMapper.deleteBatchIds(ids) == ids.size();
    }

    @Autowired
    public void setFeelingMapper(FeelingMapper feelingMapper) {
        this.feelingMapper = feelingMapper;
    }
}