package com.yyft.blog.tools.intercept;

import com.yyft.blog.annotation.NoAuth;
import com.yyft.blog.util.TokenUtil;
import com.yyft.common.model.BizException;
import com.yyft.common.model.ResultCode;
import com.yyft.common.utils.text.StringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JWTIntercept extends HandlerInterceptorAdapter {

    private TokenUtil tokenUtil;

    @Override
    @SneakyThrows
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            NoAuth noAuth = handlerMethod.getMethodAnnotation(NoAuth.class);
            if (noAuth != null) {
                return true;
            }
        }
        //判断是否存在cookie,有cookie时不检测token
        if (checkCookies(request)) {
            return true;
        }

        //判断session中是否有token
        if (checkSession(request)) {
            return true;
        }

        //判断参数中是否有token
        if (checkParams(request)) {
            return true;
        }

        final String authHeader = request.getHeader(TokenUtil.AUTH_HEADER_KEY);
        log.info("## authHeader= {}", authHeader);

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(TokenUtil.TOKEN_PREFIX)) {
            log.info("### 用户未登录，请先登录 ###");
            throw new BizException(ResultCode.USER_NOT_LOGGED_IN.message());
        }
        // 获取token
        final String token = authHeader.substring(7);
        tokenUtil.checkToken(token);
        return true;
    }

    private boolean checkCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        // 没有cookie信息，则重定向到登录界面
        if (null == cookies) {
            return false;
        }
        // 定义cookie_username，用户的一些登录信息，例如：用户名，密码等
        String cookie_username = null;
        // 获取cookie里面的一些用户信息
        for (Cookie item : cookies) {
            if ("cookie_username".equals(item.getName())) {
                cookie_username = item.getValue();
                break;
            }
        }
        // 如果cookie里面没有包含用户的一些登录信息，则重定向到登录界面
        if (StringUtils.isEmpty(cookie_username)) {
            return false;
        }
        return true;
    }

    private boolean checkParams(HttpServletRequest request) {
        String token = request.getParameter("access_token");
        if (StringUtils.isBlank(token)) {
            return false;
        }
        tokenUtil.checkToken(token);
        return true;
    }

    private boolean checkSession(HttpServletRequest request) {
        if (request.getSession().getAttribute("access_token") == null) {
            return false;
        }
        String token = request.getSession().getAttribute("access_token").toString();
        if (StringUtils.isBlank(token)) {
            return false;
        }
        tokenUtil.checkToken(token);
        return true;
    }

    @Autowired
    public void setTokenUtil(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }
}
