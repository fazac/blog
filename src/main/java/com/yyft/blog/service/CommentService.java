package com.yyft.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyft.blog.entity.Comment;
import com.yyft.blog.entity.Constants;
import com.yyft.blog.entity.vo.TableQuery;
import com.yyft.blog.mapper.CommentMapper;
import com.yyft.blog.util.QueryConvert;
import com.yyft.common.utils.time.ClockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CommentService {
    private CommentMapper commentMapper;

    private JavaMailSender javaMailSender;

    public boolean addComment(Comment comment) {
        comment.setStatus("0");
        comment.setType("0");
        comment.setViewable("1");
        return commentMapper.insert(comment) > 0;
    }

    public boolean updateComment(List<Integer> cids, String viewable) {
        return commentMapper.updateComment(cids, viewable) == cids.size();
    }

    public IPage<Comment> findFrontCommentByBlogId(Integer bid, Integer current) {
        QueryWrapper<Comment> qw = new QueryWrapper<>();
        Page<Comment> page = new Page<>(current, Constants.FONT_PAGE_SIZE);
        qw.eq("bid", bid);
        qw.eq("type", "0");
        qw.orderByDesc("create_time");
        IPage<Comment> comments = commentMapper.selectPage(page, qw);
        return initPage(comments);
    }

    public IPage<Comment> findByTableQuery(TableQuery tq) {
        QueryWrapper<Comment> qw = QueryConvert.convertWrapper(tq);
        qw.eq("type", "0");
        IPage<Comment> comments = commentMapper.selectPage(QueryConvert.convertPage(tq), qw);
        return initPage(comments);
    }

    private IPage<Comment> initPage(IPage<Comment> comments) {
        if (comments.getRecords() != null && !comments.getRecords().isEmpty()) {
            comments.getRecords().forEach(x -> {
                if (x.getSid() != null) {
                    x.setSComment(commentMapper.selectById(x.getSid()));
                }
            });
        }
        return comments;
    }

    @Transactional
    public void replyComment(Integer commentid, String reply) {
        Comment source = commentMapper.selectById(commentid);
        Comment comment = new Comment();
        comment.setContent(reply);
        comment.setType("1");
        comment.setStatus("1");
        comment.setViewable("1");
        comment.setCreateTime(ClockUtil.currentDate());
        commentMapper.insert(comment);
        source.setStatus("1");
        source.setSid(comment.getSn());
        if ("1".equals(source.getReEmail())) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo();
            simpleMailMessage.setFrom("1650031931@qq.com");
            simpleMailMessage.setSubject(Constants.JWT_ISSUER + " 回复:" + source.getContent());
            simpleMailMessage.setText(reply);
            javaMailSender.send(simpleMailMessage);
        }
        commentMapper.updateById(source);
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
}
