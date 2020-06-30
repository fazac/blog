package com.yyft.blog.controller;

import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.ModuleType;
import com.yyft.blog.entity.query.BlogQuery;
import com.yyft.blog.service.BlogService;
import com.yyft.blog.service.FeelingService;
import com.yyft.blog.service.ModuleTypeService;
import com.yyft.common.model.BizResultVO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-19 16:33
 * @Version 1.0
 */
@Slf4j
@Controller
@RequestMapping(value = "/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private ModuleTypeService moduleTypeService;
    @Autowired
    private FeelingService feelingService;

    @RequestMapping("index")
    public ModelAndView index() {
        List<ModuleType> moduleTypes = moduleTypeService.getModuleTypes();
        Map<String, Object> res = new HashMap<>();
        List<Blog> blogs = blogService.findBlogsByQuery(new BlogQuery(0, 8, null));
        if (blogs.size() > 2) {
            res.put("blog0", blogs.get(0));
            res.put("blog1", blogs.get(1));
            res.put("blog2", blogs.get(2));
            res.put("blogs", blogs.subList(3, blogs.size()));
        } else {
            res.put("blogs", blogs);
        }
        List<String> archives = blogService.findCreateTime();
        res.put("archives", archives);
        res.put("moduleTypes", moduleTypes);
        res.put("feeling", feelingService.getLastFeeling());
        return new ModelAndView("index", "res", res);
    }

    @PostMapping("/blogs")
    @Transactional
    public ModelAndView saveBlog(@NonNull @RequestBody Blog blog) {
        if (blogService.saveBlog(blog)) {
            return new ModelAndView("blog", "result", new BizResultVO(1, "success", null));
        } else {
            return new ModelAndView("blog", "result", new BizResultVO(0, "fail", null));
        }
    }

    @GetMapping("/blogs")
    public ModelAndView getBlogs(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("type") Integer type) {
        return new ModelAndView("blog", "blogs", blogService.findBlogsByQuery(new BlogQuery((page - 1) * pageSize, pageSize, type)));
    }


}