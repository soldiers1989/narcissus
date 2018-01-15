package com.ih2ome.hardware_server.server.filter;

import com.ih2ome.hardware_server.server.handler.XssAndSqlHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/11
 * @Emial Lucius.li@ixiaoshuidi.com
 */
//@WebFilter(filterName="xssAndSqlFilter",urlPatterns = "/*")
public class XssAndSqlFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XssAndSqlHttpServletRequestWrapper xssRequest = new XssAndSqlHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(xssRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
