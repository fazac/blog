package com.yyft.blog.service;

import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.query.BlogQuery;
import com.yyft.blog.mapper.BlogMapper;
import com.yyft.blog.mapper.ModuleTypeMapper;
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
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private ModuleTypeMapper moduleTypeMapper;

    public boolean saveBlog(Blog blog) {
        return blogMapper.insertBlog(blog) > 0;
    }

    public Blog findById(Integer id) {
        return blogMapper.findById(id);
    }

    public List<Blog> findBlogsByQuery(BlogQuery blogQuery) {
        List<Blog> blogs = blogMapper.findBlogsByQuery(blogQuery);
        blogs.forEach(x -> {
            x.setModuleTypeName(moduleTypeMapper.selectByPrimaryKey(x.getModuleType()).getTypeName());
        });
        return blogs;
    }

    public List<String> findCreateTime() {
        return blogMapper.findAllCreateTime();
    }
}