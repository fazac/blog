package com.yyft.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Constants;
import com.yyft.blog.entity.Label;
import com.yyft.blog.service.BlogService;
import com.yyft.blog.service.FeelingService;
import com.yyft.blog.service.LabelService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
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

    @GetMapping("index")
    public String index(Model model) {
        IPage<Blog> ipages = blogService.findBlogsByQuery(0, Constants.PAGE_SIZE, null, "", "");
        return getBlogElse(model, ipages);
    }

    @GetMapping("/blogs")
    public ModelAndView apiGetBlogs(@RequestParam("page") Integer page,
                                    @RequestParam("type") Integer type, @RequestParam("name") String name) {
        return new ModelAndView("blog", "blogs", blogService.findBlogsByQuery((page - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE, type, name, ""));
    }

    @GetMapping("/blog/{id}")
    public String findBlogById(Model model, @NonNull @PathVariable("id") Integer id) {
        Blog blog = blogService.findById(id);
        model.addAttribute("blog", blog);
        model.addAttribute("feeling", feelingService.getLastFeeling());
        return "/blog/blog";
    }

    @GetMapping("search")
    public String search(Model model, @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                         @RequestParam(value = "labelid", required = false) Integer labelid,
                         @RequestParam(value = "archive", required = false) String archive) {
        IPage<Blog> ipages = blogService.findBlogsByQuery((page - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE, labelid, name, archive);
        return getBlogElse(model, ipages);
    }

    private String getBlogElse(Model model, IPage<Blog> ipages) {
        List<Label> labels = labelService.findByType("0");
        List<Blog> blogs = ipages.getRecords();
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", ipages.getSize());
        model.addAttribute("current", ipages.getCurrent());
        List<String> archives = blogService.findCreateTime();
        model.addAttribute("archives", archives);
        model.addAttribute("labels", labels);
        model.addAttribute("showSearch", Boolean.TRUE);
        model.addAttribute("feeling", feelingService.getLastFeeling());
        return "index";
    }

}