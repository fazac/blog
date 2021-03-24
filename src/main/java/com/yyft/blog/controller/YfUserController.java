package com.yyft.blog.controller;

import com.yyft.blog.service.YfUserService;
import com.yyft.common.model.Result;
import com.yyft.common.model.ResultCode;
import com.yyft.common.utils.collection.type.Pair;
import com.yyft.common.utils.mapper.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-24 16:52
 * @Version 1.0
 */
@Slf4j
@Controller
@RequestMapping(value = "/blog/user")
public class YfUserController {
    @Autowired
    private YfUserService yfUserService;



//    @RequestMapping("doLogin")
//    @ResponseBody
//    public String login(Model model, @RequestParam("mobile") String mobile,
//                        @RequestParam("pass") String pass) {
//        log.info("mobile" + mobile + ",pass" + pass);
//        Pair<String, String> res = yfUserService.login(mobile, pass);
//        model.addAttribute("res", res);
//        return "home";
//    }

    @RequestMapping("doLogin")
    @ResponseBody
    public Result login(@RequestParam("mobile") String mobile, @RequestParam("pass") String pass) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(pass)) {
            return Result.createBizError(-1, ResultCode.PARAM_IS_INVALID.message());
        }
        Pair<String, String> res = yfUserService.login(mobile, pass);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("res", res);
        resMap.put("url", "home");
        return Result.createSuccess("ok", resMap);
    }
}