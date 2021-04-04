package com.yyft.blog.controller;

import com.yyft.blog.entity.Feeling;
import com.yyft.blog.service.FeelingService;
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
 * @date 2021/4/4 10:09
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/feel")
public class AminFeelingController {

    private FeelingService feelingService;

    @RequestMapping
    public String pageFeeling(Model model) {
        model.addAttribute("feels", feelingService.findAllFeels());
        return "admin/feelings";
    }

    @PostMapping("createFeel")
    @ResponseBody
    public Result createFeel(@RequestBody Feeling feeling) {
        if (StringUtils.isBlank(feeling.getMarks()) || StringUtils.isBlank(feeling.getSource())) {
            return Result.createBizError(PARAM_IS_BLANK.code(), "参数不能为空");
        }
        return Result.createSuccess("ok", feelingService.createFeeling(feeling));
    }


    @GetMapping("deleteFeels")
    @ResponseBody
    public Result deleteFeels(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return Result.createBizError(PARAM_IS_BLANK.code(), "参数不能为空");
        }
        List<Integer> param = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return Result.createSuccess("res", feelingService.deleteFeelings(param));
    }

    @Autowired
    public void setFeelingService(FeelingService feelingService) {
        this.feelingService = feelingService;
    }
}
