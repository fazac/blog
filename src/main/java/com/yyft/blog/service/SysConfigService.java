package com.yyft.blog.service;

import com.yyft.blog.mapper.SysConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/6 10:58
 */
@Service
@Slf4j
public class SysConfigService {

    private SysConfigMapper sysConfigMapper;

    public String findByName(String name) {
        return sysConfigMapper.findByName(name);
    }

    @Autowired
    public void setSysConfigMapper(SysConfigMapper sysConfigMapper) {
        this.sysConfigMapper = sysConfigMapper;
    }
}
