package com.yyft.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.query.BlogQuery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

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

    @Select("<script> " +
            " select * from blog  " +
            " <trim prefix='where' suffixOverrides=','> " +
            " <if test='type!=null'> " +
            " module_type = #{type}," +
            " </if>" +
            " </trim> limit #{page} ,#{pageSize} </script>")
    List<Blog> findBlogsByQuery(BlogQuery blogQuery);

    @Update("<script> update blog set status = 'PUBLISH',publish_time = now() where blog_id in" +
            " <foreach item='id' " +
            " collection='list' separator=',' open='(' close=')' index=''> " +
            " #{id, jdbcType=INTEGER} </foreach> </script>")
    int publishBlogs(List<Integer> ids);

    @Select("select DISTINCT(DATE_FORMAT(create_time,'%M %Y')) date from blog order by date desc;")
    List<String> findAllCreateTime();
}