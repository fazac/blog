package com.yyft.blog.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/{name}")
    @ResponseBody
    public String getUser(@PathVariable String name) {
        return "hello,aa11" + name;
    }
}
