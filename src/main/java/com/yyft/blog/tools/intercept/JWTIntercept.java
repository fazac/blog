package com.yyft.blog.tools.intercept;

import com.yyft.blog.annotation.NoAuth;
import com.yyft.blog.util.TokenUtil;
import com.yyft.common.model.BizException;
import com.yyft.common.model.ResultCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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

    @Autowired
    public void setTokenUtil(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }
}
