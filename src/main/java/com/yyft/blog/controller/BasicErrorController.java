package com.yyft.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/3/24 10:27
 */
@Slf4j
@Controller
@RequestMapping("error")
public class BasicErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping
    public String error() {
        return getErrorPath();
    }
}
