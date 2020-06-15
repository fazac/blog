package com.yyft.blog.tools.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-15 19:12
 * @Version 1.0
 */
@Slf4j
public class SelfDefineInvalidCharacterFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String parameterName = null;
        String parameterValue = null;
        // 获取请求的参数
        Enumeration<String> allParameter = request.getParameterNames();
        while (allParameter.hasMoreElements()) {
            parameterName = allParameter.nextElement();
            parameterValue = request.getParameter(parameterName);
            if (null != parameterValue) {
                for (String str : invalidCharacter) {
                    if (StringUtils.containsIgnoreCase(parameterValue, str)) {
                        request.setAttribute("errorMessage", "非法字符：" + str);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                        requestDispatcher.forward(request, response);
                        return;
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private static String[] invalidCharacter = new String[]{
            "script", "select", "insert", "document", "window", "function",
            "delete", "update", "prompt", "alert", "create", "alter",
            "drop", "iframe", "link", "where", "replace", "function", "onabort",
            "onactivate", "onafterprint", "onafterupdate", "onbeforeactivate",
            "onbeforecopy", "onbeforecut", "onbeforedeactivateonfocus",
            "onkeydown", "onkeypress", "onkeyup", "onload",
            "expression", "applet", "layer", "ilayeditfocus", "onbeforepaste",
            "onbeforeprint", "onbeforeunload", "onbeforeupdate",
            "onblur", "onbounce", "oncellchange", "oncontextmenu",
            "oncontrolselect", "oncopy", "oncut", "ondataavailable",
            "ondatasetchanged", "ondatasetcomplete", "ondeactivate",
            "ondrag", "ondrop", "onerror", "onfilterchange", "onfinish", "onhelp",
            "onlayoutcomplete", "onlosecapture", "onmouse", "ote",
            "onpropertychange", "onreadystatechange", "onreset", "onresize",
            "onresizeend", "onresizestart", "onrow", "onscroll",
            "onselect", "onstaronsubmit", "onunload", "IMgsrc", "infarction"
    };
}