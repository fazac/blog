package com.yyft.blog.service;

import com.yyft.blog.entity.Label;
import com.yyft.blog.mapper.LabelMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/3/24 13:36
 */

@Slf4j
@Service
public class LabelService {
    private LabelMapper labelMapper;

    public List<Label> findByType(String type) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(type)) {
            map.put("type", type);
        }
        map.put("status", "1");
        return labelMapper.selectByMap(map);
    }

    @Autowired
    public void setLabelMapper(LabelMapper labelMapper) {
        this.labelMapper = labelMapper;
    }
}
