package com.yyft.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyft.blog.entity.SysConfig;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/6 9:55
 */
@Repository
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    @Select("select * from sys_config where is_del = '0'")
    List<SysConfig> findAll();

    @Select("select content from sys_config where is_del = '0' and name = #{name,jdbcType=VARCHAR}")
    String findByName(String name);

}
