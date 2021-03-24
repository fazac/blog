package com.yyft.blog.service;

import com.yyft.blog.entity.Comment;
import com.yyft.blog.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentService {
    private CommentMapper commentMapper;

    public boolean saveComment(Comment comment) {
        return commentMapper.insert(comment) > 0;
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
}
