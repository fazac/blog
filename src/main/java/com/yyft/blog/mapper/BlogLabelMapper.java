package com.yyft.blog.mapper;

import com.yyft.blog.entity.BlogLabel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlogLabelMapper {
    @Insert("insert into blog_label (blog_id, label_id, `count`) values (#{blogId,jdbcType=INTEGER}, #{labelId,jdbcType=INTEGER}, #{count,jdbcType=INTEGER})")
    int insert(BlogLabel record);

}