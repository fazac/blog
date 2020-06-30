package com.yyft.blog.mapper;

import com.yyft.blog.entity.Feeling;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FeelingMapper {

    @Insert("insert into feeling (marks) values (#{marks,jdbcType=VARCHAR})")
    int insert(Feeling record);

    @Select("select *  from feeling where sn = #{sn,jdbcType=INTEGER}")
    Feeling selectByPrimaryKey(Integer sn);

    @Select("select max(sn) from feeling")
    Integer getLastId();

}