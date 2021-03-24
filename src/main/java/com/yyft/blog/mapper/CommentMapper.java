package com.yyft.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyft.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {
}