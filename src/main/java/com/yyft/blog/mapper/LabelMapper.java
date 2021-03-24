package com.yyft.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyft.blog.entity.Label;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelMapper extends BaseMapper<Label> {
    @Insert("insert into label (`name`)  values (#{name,jdbcType=VARCHAR})")
    int saveLabel(Label record);

}