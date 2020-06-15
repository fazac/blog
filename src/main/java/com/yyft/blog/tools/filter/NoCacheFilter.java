package com.yyft.blog.tools.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Description 禁用缓存
 * @Author fzc
 * @Date 2020-06-15 19:07
 * @Version 1.0
 */
@Slf4j
public class NoCacheFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rep = (HttpServletResponse) servletResponse;
        rep.setDateHeader("Expires", -1);
        rep.setHeader("Cache-Control", "no-cache");
        chain.doFilter(req, rep);
    }

    @Override
    public void destroy() {

    }
}