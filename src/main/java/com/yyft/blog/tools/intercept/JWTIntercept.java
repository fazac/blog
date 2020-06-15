package com.yyft.blog.tools.intercept;

import com.yyft.blog.annotation.NoAuth;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JWTIntercept extends HandlerInterceptorAdapter {
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


        return true;
    }
}
