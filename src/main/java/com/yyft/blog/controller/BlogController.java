package com.yyft.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Label;
import com.yyft.blog.service.BlogService;
import com.yyft.blog.service.FeelingService;
import com.yyft.blog.service.LabelService;
import com.yyft.common.model.BizResultVO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private FeelingService feelingService;
    @Autowired
    private LabelService labelService;

    @RequestMapping("index")
    public String index(Model model) {
        Map<String, Object> res = new HashMap<>();
        IPage<Blog> ipages = blogService.findBlogsByQuery(0, 8, null, "");
        List<Label> labels = labelService.findByType("0");
        List<String> labelNames;
        if (!labels.isEmpty()) {
            labelNames = labels.stream().map(Label::getName).collect(Collectors.toList());
        } else {
            labelNames = Collections.emptyList();
        }
        List<Blog> blogs = ipages.getRecords();
        if (blogs.size() > 2) {
            model.addAttribute("blog0", blogs.get(0));
            model.addAttribute("blog1", blogs.get(1));
            model.addAttribute("blog2", blogs.get(2));
            model.addAttribute("blogs", blogs.subList(3, blogs.size()));
        } else {
            model.addAttribute("blogs", blogs);
        }
        List<String> archives = blogService.findCreateTime();
        model.addAttribute("archives", archives);
        model.addAttribute("labels", labelNames);
        model.addAttribute("feeling", feelingService.getLastFeeling());
        return "index";
    }

    @PostMapping("/blog")
    @Transactional
    public ModelAndView saveBlog(@NonNull @RequestBody Blog blog) {
        if (blogService.addBlog(blog)) {
            return new ModelAndView("blog", "result", new BizResultVO(1, "success", null));
        } else {
            return new ModelAndView("blog", "result", new BizResultVO(0, "fail", null));
        }
    }

    @GetMapping("/blogs")
    public ModelAndView getBlogs(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
                                 @RequestParam("type") Integer type, @RequestParam("name") String name) {
        return new ModelAndView("blog", "blogs", blogService.findBlogsByQuery((page - 1) * pageSize, pageSize, type, name));
    }

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping("home")
    public String home(Model model) {
        return "admin/home";
    }

}