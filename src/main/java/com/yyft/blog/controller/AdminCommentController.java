package com.yyft.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Comment;
import com.yyft.blog.entity.vo.TableQuery;
import com.yyft.blog.entity.vo.TableResult;
import com.yyft.blog.service.CommentService;
import com.yyft.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.yyft.common.model.ResultCode.PARAM_IS_BLANK;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/5 15:05
 */
@Slf4j
@Controller
@RequestMapping("/admin/comment")
public class AdminCommentController {
    private CommentService commentService;

    @RequestMapping
    public String pageComment() {
        return "/admin/comments";
    }

    @GetMapping("passComment")
    @ResponseBody
    public Result passComment(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return Result.createBizError(PARAM_IS_BLANK.code(), "参数不能为空");
        }
        List<Integer> param = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return Result.createSuccess("ok", commentService.updateComment(param, "1"));
    }

    @GetMapping("unpassComment")
    @ResponseBody
    public Result unpassComment(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return Result.createBizError(PARAM_IS_BLANK.code(), "参数不能为空");
        }
        List<Integer> param = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return Result.createSuccess("ok", commentService.updateComment(param, "0"));
    }

    @PostMapping("replyComment")
    @ResponseBody
    public Result replyComment(@RequestParam("id") Integer id, @RequestParam("reply") String reply) {
        commentService.replyComment(id, reply);
        return Result.createSuccess();
    }

    @PostMapping("/getComments")
    @ResponseBody
    public Result getComments(@RequestBody TableQuery tq) {
        TableResult<Comment> tableResult = new TableResult<>();
        IPage<Comment> page = commentService.findByTableQuery(tq);
        tableResult.setData(page.getRecords());
        tableResult.setDraw(tq.getDraw());
        tableResult.setRecordsFiltered(page.getTotal());
        tableResult.setRecordsTotal(page.getSize());
        return Result.createSuccess("ok", tableResult);
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
