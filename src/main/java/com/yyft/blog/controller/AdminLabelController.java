package com.yyft.blog.controller;

import com.yyft.blog.entity.Label;
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

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/3 20:42
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/label")
public class AdminLabelController {
    private LabelService labelService;

    @RequestMapping
    public String pageLable(Model model) {
        model.addAttribute("labels", labelService.findByType(""));
        return "admin/labels";
    }

    @PostMapping("modifyLabel")
    @ResponseBody
    public Result createLabel(Label label) {
        if (StringUtils.isBlank(label.getName())) {
            return Result.createBizError(PARAM_IS_BLANK.code(), "参数不能为空");
        }
        if (StringUtils.isBlank(label.getStatus())) {
            label.setType("0");
        }
        return Result.createSuccess("ok", labelService.createLabel(label));
    }

    @PostMapping("updateLabel")
    @ResponseBody
    public Result updateLabel(Label label) {
        if (StringUtils.isBlank(label.getName())) {
            return Result.createBizError(PARAM_IS_BLANK.code(), "参数不能为空");
        }
        if (StringUtils.isBlank(label.getStatus())) {
            label.setType("0");
        }
        return Result.createSuccess("ok", labelService.updateLabel(label));
    }

    @GetMapping("deleteLabels")
    @ResponseBody
    public Result deleteLabels(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return Result.createBizError(PARAM_IS_BLANK.code(), "参数不能为空");
        }
        List<Integer> param = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return Result.createSuccess("res", labelService.deleteLabels(param));
    }

    @Autowired
    public void setLabelService(LabelService labelService) {
        this.labelService = labelService;
    }
}
