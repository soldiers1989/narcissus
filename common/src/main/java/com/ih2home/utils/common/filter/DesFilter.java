package com.ih2home.utils.common.filter;


import com.ih2home.utils.common.ConstUtils;
import com.ih2home.utils.common.base.WrapperedRequest;
import com.ih2home.utils.common.base.WrapperedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/23
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class DesFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(DesFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestBody = getRequestBody((HttpServletRequest) servletRequest);
        String requestBodyMw = ConstUtils.desDecode(requestBody);
        logger.debug("解密请求数据：" + requestBodyMw);
        WrapperedResponse wrapResponse = new WrapperedResponse(
                (HttpServletResponse) servletResponse);
        WrapperedRequest wrapRequest = new WrapperedRequest(
                (HttpServletRequest) servletRequest, requestBodyMw);
        filterChain.doFilter(wrapRequest, wrapResponse);

        byte[] data = wrapResponse.getResponseData();
        logger.debug("原始返回数据： " + new String(data, "utf-8"));
        // 加密返回报文
        String responseBodyMw = ConstUtils.desEncode(new String(data));
        logger.debug("加密返回数据： " + responseBodyMw);
        writeResponse(servletResponse, responseBodyMw);

    }

    @Override
    public void destroy() {

    }

    /**
     *
     * @param req
     * @return
     */
    private String getRequestBody(HttpServletRequest req) {
        try {
            BufferedReader reader = req.getReader();
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            return json;
        } catch (IOException e) {
            logger.error("验签时请求体读取失败", e);
        }
        return "";
    }

    /**
     *
     * @param response
     * @param responseString
     * @throws IOException
     */
    private void writeResponse(ServletResponse response, String responseString)
            throws IOException {
        PrintWriter out = response.getWriter();
        out.print(responseString);
        out.flush();
        out.close();
    }
}
