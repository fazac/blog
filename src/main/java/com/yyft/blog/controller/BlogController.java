package com.yyft.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Label;
import com.yyft.blog.service.BlogService;
import com.yyft.blog.service.FeelingService;
import com.yyft.blog.service.LabelService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        IPage<Blog> ipages = blogService.findBlogsByQuery(0, 8, null, "");
        List<Label> labels = labelService.findByType("0");
        List<String> labelNames;
        if (!labels.isEmpty()) {
            labelNames = labels.stream().map(Label::getName).collect(Collectors.toList());
        } else {
            labelNames = Collections.emptyList();
        }
        List<Blog> blogs = ipages.getRecords();
        model.addAttribute("blogs", blogs);
        List<String> archives = blogService.findCreateTime();
        model.addAttribute("archives", archives);
        model.addAttribute("labels", labelNames);
        model.addAttribute("showSearch", Boolean.TRUE);
        model.addAttribute("feeling", feelingService.getLastFeeling());
        return "index";
    }

    @GetMapping("/blogs")
    public ModelAndView apiGetBlogs(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
                                    @RequestParam("type") Integer type, @RequestParam("name") String name) {
        return new ModelAndView("blog", "blogs", blogService.findBlogsByQuery((page - 1) * pageSize, pageSize, type, name));
    }

    @GetMapping("/blog/{id}")
    public String findBlogById(Model model, @NonNull @PathVariable("id") Integer id) {
        Blog blog = blogService.findById(id);
        model.addAttribute("blog", blog);
        model.addAttribute("feeling", feelingService.getLastFeeling());
        return "/blog/blog";
    }


}