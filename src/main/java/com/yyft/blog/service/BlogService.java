package com.yyft.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Constants;
import com.yyft.blog.entity.vo.TableQuery;
import com.yyft.blog.mapper.BlogMapper;
import com.yyft.blog.util.QueryConvert;
import com.yyft.common.utils.text.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    public IPage<Blog> findBlogsByQuery(int current, int size, Integer labelid, String name, String archive, String status) {
        Page<Blog> page = new Page<>(current, size);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        if (labelid != null) {
            wrapper.like("labelids", StringUtil.surroundSeperator(labelid.toString(), Constants.LINE_SEPERATOR))
                    .like("title", name);
        } else {
            wrapper.like("title", name);
        }
        if (!StringUtils.isBlank(status)) {
            wrapper.eq("status", status);
        }
        IPage<Blog> blogs = blogMapper.selectPage(page, wrapper);
        blogs.getRecords().forEach(x -> {
            x.setContent("");
        });
        return blogs;
    }

    public IPage<Blog> findByTableQuery(TableQuery tq) {
        Page<Blog> page = QueryConvert.convertPage(tq);
        QueryWrapper<Blog> wrapper = QueryConvert.convertWrapper(tq);
        IPage<Blog> blogs = blogMapper.selectPage(QueryConvert.convertPage(tq), QueryConvert.convertWrapper(tq));
        blogs.getRecords().forEach(x -> {
            x.setContent("");
        });
        return blogs;
    }

    public List<String> findCreateTime() {
        return blogMapper.findAllCreateTime();
    }

    public boolean addBlog(Blog blog) {
        return blogMapper.insert(blog) > 0;
    }

    public boolean updateBlog(Blog blog) {
        return blogMapper.updateById(blog) > 0;
    }

    public boolean deleteBlogs(List<Integer> ids) {
        return blogMapper.deleteBatchIds(ids) == ids.size();
    }

    public boolean publishBlogs(List<Integer> ids) {
        return blogMapper.publishBlogs(ids) == ids.size();
    }

    @Autowired
    public void setBlogMapper(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }
}