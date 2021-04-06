package com.yyft.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Comment;
import com.yyft.blog.entity.Constants;
import com.yyft.blog.entity.Label;
import com.yyft.blog.service.BlogService;
import com.yyft.blog.service.CommentService;
import com.yyft.blog.service.FeelingService;
import com.yyft.blog.service.LabelService;
import com.yyft.blog.tools.listener.ApplicationStartCacheListener;
import com.yyft.common.utils.mapper.JsonMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    private CommentService commentService;

    private ApplicationStartCacheListener ac;

    @GetMapping("index")
    public String index(Model model) {
        IPage<Blog> ipages = blogService.findBlogsByQuery(0, Constants.FONT_PAGE_SIZE, null, "", "", "PUBLISH");
        return getBlogElse(model, ipages);
    }

    @GetMapping("/blogs")
    public ModelAndView apiGetBlogs(@RequestParam("page") Integer page,
                                    @RequestParam("type") Integer type, @RequestParam("name") String name) {
        return new ModelAndView("blog", "blogs", blogService.findBlogsByQuery((page - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE, type, name, "", "PUBLISH"));
    }

    @GetMapping("/blog/{id}")
    public String findBlogById(Model model, @NonNull @PathVariable("id") Integer id) {
        Blog blog = blogService.findById(id);
        List<Comment> comments = commentService.findCommentsByBlogId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("blog", blog);
        model.addAttribute("feeling", feelingService.getLastFeeling());
        Comment comment = new Comment();
        comment.setReEmail(true);
        comment.setBid(id);
        model.addAttribute("nComment", comment);
        return "/blog/blog";
    }

    @GetMapping("search")
    public String search(Model model, @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                         @RequestParam(value = "labelid", required = false) Integer labelid,
                         @RequestParam(value = "archive", required = false) String archive) {
        IPage<Blog> ipages = blogService.findBlogsByQuery(page, Constants.FONT_PAGE_SIZE, labelid, name, archive, "PUBLISH");
        return getBlogElse(model, ipages);
    }

    private String getBlogElse(Model model, IPage<Blog> ipages) {
        if (ipages.getRecords() != null) {
            for (Blog b : ipages.getRecords()) {
                b.setLabelidsName(ac.getLablesCache(b.getLabelids()));
            }
        }
        List<Label> labels = labelService.findByType("0");
        List<Blog> blogs = ipages.getRecords();
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", ipages.getPages());
        model.addAttribute("current", ipages.getCurrent());
        List<String> archives = blogService.findCreateTime();
        model.addAttribute("archives", archives);
        model.addAttribute("labels", labels);
        model.addAttribute("showSearch", Boolean.TRUE);
        model.addAttribute("feeling", feelingService.getLastFeeling());
        List<Comment> comments = commentService.findFrontCommentByBlogId(null, 1).getRecords();
        model.addAttribute("comments", comments);
        return "index";
    }

    @PostMapping("/commitComment")
    public String commitComment(@ModelAttribute Comment comment) {
        commentService.addComment(comment);
        return "redirect:/blog/blog/" + comment.getBid();
//        return "/blog/blog";
    }

    @Autowired
    public void setAc(ApplicationStartCacheListener ac) {
        this.ac = ac;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

}