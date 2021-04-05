package com.yyft.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyft.blog.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    @Update(" <script> " +
            " update comment set status = '1' , viewable = #{viewable,jdbcType=VARCHAR} " +
            " where sn in " +
            " <foreach item = 'id' collection='ids' separator=',' open='(' close=')' index=''>" +
            " #{id, jdbcType=INTEGER} " +
            " </foreach>" +
            "</script>")
    int updateComment(@Param("ids") List<Integer> ids, String viewable);
}