package com.yyft.blog.controller;

import com.yyft.blog.entity.YfUsr;
import com.yyft.blog.service.YfUserService;
import com.yyft.common.model.Result;
import com.yyft.common.utils.mapper.JsonMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-15 14:51
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/usr")
public class YfUserController {
    @Autowired
    private YfUserService yfUserService;

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public Result addUser(@NonNull @RequestBody YfUsr yfUsr) {
        boolean res = yfUserService.addUser(yfUsr);
        if (res) {
            return Result.createSuccess();
        } else {
            return Result.createError();
        }
    }
}