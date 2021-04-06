package com.yyft.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Constants;
import com.yyft.blog.entity.Label;
import com.yyft.blog.mapper.LabelMapper;
import com.yyft.blog.tools.listener.ApplicationStartCacheListener;
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
    private ApplicationStartCacheListener ac;

    public List<Label> findByType(String type) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(type)) {
            map.put("type", type);
        }
        return labelMapper.selectByMap(map);
    }

//    public IPage<Label> findLabelByPage(Integer current, String type) {
//        Page<Label> page = new Page<>((current - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE);
//        QueryWrapper<Label> qw = new QueryWrapper<>();
//        if (StringUtils.isNotBlank(type)) {
//            qw.eq("type", type);
//        }
//        return labelMapper.selectPage(page, qw);
//    }

    public boolean createLabel(Label label) {
        int res = labelMapper.insert(label);
        if (res == 1) {
            ac.refreshLabels();
            return true;
        }
        return false;
    }

    public boolean updateLabel(Label label) {
        int res = labelMapper.updateById(label);
        if (res == 1) {
            ac.refreshLabels();
            return true;
        }
        return false;
    }

    public boolean deleteLabels(List<Integer> ids) {
        return labelMapper.deleteBatchIds(ids) == ids.size();
    }

    @Autowired
    public void setLabelMapper(LabelMapper labelMapper) {
        this.labelMapper = labelMapper;
    }

    @Autowired
    public void setAc(ApplicationStartCacheListener ac) {
        this.ac = ac;
    }
}
