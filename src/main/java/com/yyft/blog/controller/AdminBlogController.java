package com.yyft.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Constants;
import com.yyft.blog.entity.Label;
import com.yyft.blog.entity.model.Tip;
import com.yyft.blog.service.BlogService;
import com.yyft.blog.service.FeelingService;
import com.yyft.blog.service.LabelService;
import com.yyft.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.yyft.common.model.ResultCode.PARAM_IS_BLANK;

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
    public Result saveBlog(@RequestBody Blog blog) {
        blogService.addBlog(blog);
        return Result.createSuccess();
    }

    @RequestMapping("blogList")
    public String blogList() {
        return "admin/blogList";
    }

    @GetMapping("/blogList/{page}")
    public String getBlogs(Model model, @PathVariable("page") Integer page) {
        if (page == null) {
            page = 0;
        }
        model.addAttribute("blogs", blogService.findBlogsByQuery((page - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE, null, "", ""));
        model.addAttribute("tip", new Tip("博客管理", "查询成功"));
        return "admin/blogList";
    }

    @PostMapping("/getBlogs")
    @ResponseBody
    public Result getBlogs(@RequestParam("page") Integer page) {
        if (page == null) {
            page = 0;
        }
        IPage<Blog> blogIPage = blogService.findBlogsByQuery((page - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE, null, "", "");
        return Result.createSuccess("ok", blogIPage);
    }

    @GetMapping("deleteBlogs")
    @ResponseBody
    public Result deleteBlogs(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return Result.createBizError(PARAM_IS_BLANK.code(), "参数不能为空");
        }
        List<Integer> param = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return Result.createSuccess("res", blogService.deleteBlogs(param));
    }
}
