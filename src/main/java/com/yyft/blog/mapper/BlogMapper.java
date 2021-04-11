package com.yyft.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.model.DateSortModel;
import com.yyft.blog.entity.query.BlogQuery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BlogMapper extends BaseMapper<Blog> {

    @Insert("insert into blog (title, show_cover_pic, digest, \n" +
            "      content, author, create_time, \n" +
            "      publish_time, update_time, module_type, \n" +
            "      `status`, sort_id)\n" +
            "    values (#{title,jdbcType=VARCHAR}, #{showCoverPic,jdbcType=VARCHAR}, #{digest,jdbcType=VARCHAR}, \n" +
            "      #{content,jdbcType=VARCHAR}, #{author,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, \n" +
            "      #{publishTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, #{moduleType,jdbcType=INTEGER}, \n" +
            "      #{status,jdbcType=VARCHAR}, #{sortId,jdbcType=INTEGER})")
    int insertBlog(Blog blog);

    @Select("select * from blog where blog_id =#{id}")
    Blog findById(Integer id);


    @Select("select title from blog where blog_id =#{id}")
    String findTitleById(Integer id);

    @Select("<script> " +
            " select * from blog  " +
            " <trim prefix='where' suffixOverrides=','> " +
            " <if test='type!=null'> " +
            " module_type = #{type}," +
            " </if>" +
            " </trim> limit #{page} ,#{pageSize} </script>")
    List<Blog> findBlogsByQuery(BlogQuery blogQuery);

    @Update("<script> update blog set status = 'PUBLISH',publish_time = #{date,jdbcType=TIMESTAMP} where blog_id in" +
            " <foreach item='id' " +
            " collection='ids' separator=',' open='(' close=')' index=''> " +
            " #{id, jdbcType=INTEGER} </foreach> </script>")
    int publishBlogs(@Param("ids") List<Integer> ids, Date date);

    @Select("select DATE_FORMAT(publish_time,'%y-%m') date , count(1) count from blog where is_del = '0' and publish_time is not null  group by date ORDER BY date desc ;")
    List<DateSortModel> findAllCreateTime();
}