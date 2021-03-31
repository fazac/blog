package com.yyft.blog.tools.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-24 11:18
 * @Version 1.0
 */
@Slf4j
public class RedirectFilter implements Filter {
    private String[] ignorePath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ignorePath = new String[]{
                "/resources/",
                "/static/",
                "/api/",
                "/blog/",
                "/admin",
                "/admin/",
                "/user/"
        };
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String srvPath = getRequestPath(req);

        //需忽略的链接
        if (!checkPathStarts(srvPath, ignorePath)) {
            rep.sendRedirect(req.getContextPath()
                    + "/blog/index");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private String getRequestPath(HttpServletRequest request) {
        String p = request.getRequestURI();
        String cp = request.getContextPath();
        if (cp == null || cp.isEmpty() || "/".endsWith(cp)) {
            return p;
        } else {
            return p.substring(cp.length());
        }
    }

    private boolean checkPathStarts(String path, String[] urlRules) {
        if (urlRules != null && urlRules.length > 0) {
            for (String s : urlRules) {
                if (path.startsWith(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkSinglePathStart(String path, String url) {
        return path.startsWith(url);
    }
}