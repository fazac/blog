package com.yyft.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyft.blog.entity.Feeling;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface FeelingMapper extends BaseMapper<Feeling> {

    @Insert("insert into feeling (marks) values (#{marks,jdbcType=VARCHAR})")
    int insert(Feeling record);

    @Select("select *  from feeling where sn = #{sn,jdbcType=INTEGER} and is_del = '0'")
    Feeling selectByPrimaryKey(Integer sn);

    @Select("select max(sn) from feeling")
    Integer getLastId();


}