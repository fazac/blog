package com.yyft.blog.controller;

import com.yyft.blog.entity.YfUsr;
import com.yyft.blog.service.YfUserService;
import com.yyft.common.model.Result;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "register")
    public Result register(@NonNull @RequestBody YfUsr yfUsr) {
        boolean res = yfUserService.register(yfUsr);
        if (res) {
            return Result.createSuccess();
        } else {
            return Result.createError();
        }
    }

    @GetMapping(value = "login")
    public Result login(@RequestParam("mobile") String mobile, @RequestParam("pass") String pass) {
        return Result.createSuccess("ok", yfUserService.login(mobile, pass));
    }
}