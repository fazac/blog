package com.yyft.blog.controller;

import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Label;
import com.yyft.blog.service.BlogService;
import com.yyft.blog.service.FeelingService;
import com.yyft.blog.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminBlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private FeelingService feelingService;
    @Autowired
    private LabelService labelService;

    @RequestMapping
    public String login() {
        return "login";
    }

    @RequestMapping("home")
    public String home(Model model) {
        return "admin/home";
    }

    @RequestMapping("edit")
    public String edit(Model model) {
        List<Label> labels = labelService.findByType("");
        model.addAttribute("labels", labels);
        return "admin/edit";
    }

    @RequestMapping(value = "/saveBlog", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String saveBlog(@RequestBody Blog blog) {
        blogService.addBlog(blog);
        return "success";
    }

    @GetMapping("/blogList/{page}/{pageSize}")
    public String getBlogs(Model model, @PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize) {
        if (page == null || pageSize == null) {
            page = 0;
            pageSize = 10;
        }
        model.addAttribute("blogs", blogService.findBlogsByQuery((page - 1) * pageSize, pageSize, null, ""));
        return "admin/blogList";
    }
}
