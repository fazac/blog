package com.yyft.blog.service;

import com.yyft.blog.entity.ModuleType;
import com.yyft.blog.mapper.ModuleTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-22 17:48
 * @Version 1.0
 */
@Slf4j
@Service
public class ModuleTypeService {

    @Autowired
    private ModuleTypeMapper moduleTypeMapper;

    public List<ModuleType> getModuleTypes() {
        return moduleTypeMapper.getModuleTypes();
    }
        
}