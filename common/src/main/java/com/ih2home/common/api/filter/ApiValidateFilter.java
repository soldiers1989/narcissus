package com.ih2home.common.api.filter;


import com.ih2home.common.api.exception.ApiException;
import com.ih2home.common.api.vo.ApiReturnValueVO;
import com.ih2home.common.api.vo.request.ApiRequestVO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * api 接口数据验证过滤器
 */
public class ApiValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ApiRequestVO apiRequestVO = null;
        ApiReturnValueVO returnValueVO = new ApiReturnValueVO();
        String jsonString = null;
        try {
            // 数据解析
            apiRequestVO = ApiRequestVO.fromJson(jsonString);
            // 数据验证
            apiRequestVO.validate();
        } catch (ApiException e) {
            //没有通过验证,重定向到出错提示页面 TODO:需要实现
            //httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + noPermissionUrl);
            return;
        }

        //通过验证
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
