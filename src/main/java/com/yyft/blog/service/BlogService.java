package com.yyft.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Constants;
import com.yyft.blog.mapper.BlogMapper;
import com.yyft.common.utils.text.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-19 16:31
 * @Version 1.0
 */
@Slf4j
@Service
public class BlogService {
    private BlogMapper blogMapper;

    public boolean saveBlog(Blog blog) {
        return blogMapper.insertBlog(blog) > 0;
    }

    public Blog findById(Integer id) {
        return blogMapper.findById(id);
    }

    public IPage<Blog> findBlogsByQuery(int current, int size, Integer labelid, String name) {
        Page<Blog> page = new Page<>(current, size);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        if (labelid != null) {
            wrapper.like("labelids", StringUtil.surroundSeperator(labelid.toString(), Constants.LINE_SEPERATOR))
                    .like("title", name);
        } else {
            wrapper.like("title", name);
        }
        return blogMapper.selectPage(page, wrapper);
    }

    public List<String> findCreateTime() {
        return blogMapper.findAllCreateTime();
    }

    public boolean addBlog(Blog blog) {
        return blogMapper.insert(blog) > 0;
    }

    @Autowired
    public void setBlogMapper(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }
}