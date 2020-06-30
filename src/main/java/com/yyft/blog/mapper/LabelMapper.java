package com.yyft.blog.mapper;

import com.yyft.blog.entity.Label;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LabelMapper {
    @Insert("insert into label (`name`)  values (#{name,jdbcType=VARCHAR})")
    int insert(Label record);

}