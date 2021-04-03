package com.yyft.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Constants;
import com.yyft.blog.entity.Label;
import com.yyft.blog.entity.vo.TableQuery;
import com.yyft.blog.entity.vo.TableResult;
import com.yyft.blog.service.BlogService;
import com.yyft.blog.service.FeelingService;
import com.yyft.blog.service.LabelService;
import com.yyft.blog.tools.listener.ApplicationStartCacheListener;
import com.yyft.common.model.Result;
import com.yyft.common.utils.text.StringUtil;
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

    private ApplicationStartCacheListener ac;

    @RequestMapping
    public String login() {
        return "login";
    }

    @RequestMapping("home")
    public String home(Model model) {
        return "admin/home";
    }

    @RequestMapping("createBlog")
    public String edit(Model model) {
        List<Label> labels = labelService.findByType("");
        model.addAttribute("labels", labels);
        return "admin/edit";
    }

    @RequestMapping("editBlog")
    public String edit(Model model, @RequestParam("id") Integer id) {
        List<Label> labels = labelService.findByType("");
        model.addAttribute("labels", labels);
        Blog blog = blogService.findById(id);
        if (StringUtils.isNotBlank(blog.getLabelids())) {
            String lids = blog.getLabelids();
            String[] ids = lids.substring(1, lids.length() - 1).replace(Constants.TWO_LINE_SEPERATOR, ",").split(",");
            model.addAttribute("lids", Arrays.stream(ids).map(Integer::parseInt).collect(Collectors.toList()));
        }
        model.addAttribute("blog", blog);
        return "admin/edit";
    }

    @RequestMapping(value = "/saveBlog", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Result saveBlog(@RequestBody Blog blog) {
        if (StringUtils.isNotBlank(blog.getLabelids())) {
            blog.setLabelids(StringUtil.surroundSeperator(blog.getLabelids().replace(",", Constants.TWO_LINE_SEPERATOR), Constants.LINE_SEPERATOR));
        }
        if (blog.getBlogId() == null) {
            blogService.addBlog(blog);
        } else {
            blogService.updateBlog(blog);
        }
        return Result.createSuccess();
    }

//    @RequestMapping("blogList")
//    public String blogList() {
//        return "admin/blogList";
//    }

//    @GetMapping("/blogList/{page}")
//    public String getBlogs(Model model, @PathVariable("page") Integer page) {
//        if (page == null) {
//            page = 0;
//        }
//        model.addAttribute("blogs", blogService.findBlogsByQuery((page - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE, null, "", "", ""));
//        model.addAttribute("tip", new Tip("博客管理", "查询成功"));
//        return "admin/blogList";
//    }

    @GetMapping("/pageMain")
    public String pageMain() {
        return "admin/blogList";
    }

    @PostMapping("/getBlogs")
    @ResponseBody
    public Result getBlogs(@RequestBody TableQuery tq) {
        TableResult<Blog> tableResult = new TableResult<>();
        IPage<Blog> page = blogService.findByTableQuery(tq);
        if (page.getRecords() != null) {
            page.getRecords().forEach(x -> x.setLabelidsName(ac.getLablesCache(x.getLabelids())));
        }
        tableResult.setData(page.getRecords());
        tableResult.setDraw(tq.getDraw());
        tableResult.setRecordsFiltered(page.getTotal());
        tableResult.setRecordsTotal(page.getSize());
        return Result.createSuccess("ok", tableResult);
    }

//    @PostMapping("/getBlogs")
//    @ResponseBody
//    public Result getBlogs(@RequestParam("page") Integer page) {
//        if (page == null) {
//            page = 0;
//        }
//        IPage<Blog> blogIPage = blogService.findBlogsByQuery((page - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE, null, "", "");
//        return Result.createSuccess("ok", blogIPage);
//    }

    @GetMapping("deleteBlogs")
    @ResponseBody
    public Result deleteBlogs(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return Result.createBizError(PARAM_IS_BLANK.code(), "参数不能为空");
        }
        List<Integer> param = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return Result.createSuccess("res", blogService.deleteBlogs(param));
    }

    @GetMapping("publishBlogs")
    @ResponseBody
    public Result publishBlogs(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return Result.createBizError(PARAM_IS_BLANK.code(), "参数不能为空");
        }
        List<Integer> param = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return Result.createSuccess("res", blogService.publishBlogs(param));
    }

    @Autowired
    public void setAc(ApplicationStartCacheListener ac) {
        this.ac = ac;
    }
}
