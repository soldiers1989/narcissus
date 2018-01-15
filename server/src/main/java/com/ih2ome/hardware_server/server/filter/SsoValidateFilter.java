package com.ih2ome.hardware_server.server.filter;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ExpireTime;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.api.vo.response.ApiResponseVO;
import com.ih2ome.common.api.vo.response.HeaderResponseDataVO;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.hardware_server.server.handler.WrapperedRequest;
import com.ih2ome.hardware_server.server.handler.WrapperedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/10
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class SsoValidateFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(SsoValidateFilter.class);

    protected static List<Pattern> patterns = new ArrayList<Pattern>();

    private String notFilterDir;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        notFilterDir = filterConfig.getInitParameter("notFilterDir");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        String _url = httpRequest.getHeader("Origin");
        if (url.contains(this.notFilterDir)) {
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", _url);
            httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
            filterChain.doFilter(httpRequest, httpResponse);
        }else{
            String body = getRequestBody(httpRequest);
            if (body == null || body.equals("")) {
                ApiResponseVO apiResponseVO = new ApiResponseVO();
                HeaderResponseDataVO headerResponseDataVO = new HeaderResponseDataVO();
                headerResponseDataVO.setRtc("-1");
                headerResponseDataVO.setEds("request param error");
                apiResponseVO.setHeaderResponseDataVO(headerResponseDataVO);
                httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
                httpResponse.setHeader("Access-Control-Allow-Origin", _url);
                httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
                httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
                response.getWriter().write(JSONObject.toJSONString(apiResponseVO));
            }else{
                ApiRequestVO apiRequestVO = JSONObject.parseObject(body, ApiRequestVO.class);
                String token = apiRequestVO.getHeaderRequestDataVO().getUtk();
                if (token == null || token.trim().equals("")) {
                    ApiResponseVO apiResponseVO = new ApiResponseVO();
                    HeaderResponseDataVO headerResponseDataVO = new HeaderResponseDataVO();
                    headerResponseDataVO.setRtc("-1");
                    headerResponseDataVO.setEds("login time out");
                    apiResponseVO.setHeaderResponseDataVO(headerResponseDataVO);
                    httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
                    httpResponse.setHeader("Access-Control-Allow-Origin", _url);
                    httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
                    httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
                    response.getWriter().write(JSONObject.toJSONString(apiResponseVO));
                }else{
                    String userInfo = CacheUtils.getStr(token);

                    if (userInfo == null) {
                        ApiResponseVO apiResponseVO = new ApiResponseVO();
                        HeaderResponseDataVO headerResponseDataVO = new HeaderResponseDataVO();
                        headerResponseDataVO.setRtc("-1");
                        headerResponseDataVO.setEds("login time out");
                        apiResponseVO.setHeaderResponseDataVO(headerResponseDataVO);
                        response.getWriter().write(JSONObject.toJSONString(apiResponseVO));
                    } else {
                        WrapperedResponse wrapResponse = new WrapperedResponse(
                                httpResponse);
                        WrapperedRequest wrapRequest = new WrapperedRequest(
                                httpRequest, body);
                        filterChain.doFilter(wrapRequest, wrapResponse);
                        byte[] data = wrapResponse.getResponseData();
                        String responseBodyMw = new String(data);
                        //请求后刷新redis中的token过期时间
                        CacheUtils.set(token,userInfo, ExpireTime.ONE_HOUR);
                        wrapResponse.setHeader("Access-Control-Allow-Credentials", "true");
                        wrapResponse.setHeader("Access-Control-Allow-Origin", _url);
                        wrapResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
                        wrapResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
                        writeResponse(response, responseBodyMw);
                    }
                }

            }

        }



    }

    @Override
    public void destroy() {

    }

    /**
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

    /**
     * 是否需要过滤
     *
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
}
