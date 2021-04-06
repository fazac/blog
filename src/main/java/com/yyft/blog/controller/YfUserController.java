package com.yyft.blog.controller;

import com.yyft.blog.entity.Constants;
import com.yyft.blog.service.YfUserService;
import com.yyft.common.model.BizException;
import com.yyft.common.model.BizResultVO;
import com.yyft.common.model.Result;
import com.yyft.common.model.ResultCode;
import com.yyft.common.utils.collection.type.Pair;
import com.yyft.common.utils.mapper.JsonMapper;
import com.yyft.common.utils.number.RandomUtil;
import com.yyft.common.utils.text.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.*;
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
@RequestMapping(value = "/user")
public class YfUserController {
    @Autowired
    private YfUserService yfUserService;


    @RequestMapping("doLogin")
    @ResponseBody
    public Result login(@RequestParam("mobile") String mobile, @RequestParam("pass") String pass,
                        HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(pass)) {
            return Result.createBizError(-1, ResultCode.PARAM_IS_INVALID.message());
        }
        Pair<String, String> res = yfUserService.login(mobile, pass);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("res", res);
        resMap.put("url", "/admin/home");
        //todo 将cookie和token放到redis
        if (res != null) {
            request.getSession().setAttribute("access_token", res.getFirst());
            Cookie cookie_username = new Cookie("cookie_username", RandomUtil.randomLetterFixLength(12));
            cookie_username.setMaxAge(30 * 24 * 60 * 60);
            log.info(request.getContextPath());
            cookie_username.setPath(Constants.ADMIN_PATH);
            // 向客户端发送cookie
            response.addCookie(cookie_username);
        } else {
            return Result.createBizError(-ResultCode.USER_LOGIN_ERROR.code(), "账号不存在或密码错误");
        }
        return Result.createSuccess("ok", resMap);
    }

    @RequestMapping(value = "logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 保存cookie，实现自动登录
        Cookie cookie_username = new Cookie("cookie_username", "");
        // 设置cookie的持久化时间，0
        cookie_username.setMaxAge(0);
        // 设置为当前项目下都携带这个cookie
        cookie_username.setPath(Constants.ADMIN_PATH);
        // 向客户端发送cookie
        response.addCookie(cookie_username);
        session.invalidate();
        return "redirect:/admin";
    }
}