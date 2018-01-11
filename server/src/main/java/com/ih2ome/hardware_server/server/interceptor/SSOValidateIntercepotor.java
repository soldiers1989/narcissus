package com.ih2ome.hardware_server.server.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/10
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class SSOValidateIntercepotor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    String userInfo = null;




    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        BodyReaderHttpServletRequestWrapper myRequestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
//        String body = myRequestWrapper.getBody();
//        if(body==null||body.equals("")){
//            ApiResponseVO apiResponseVO = new ApiResponseVO();
//            HeaderResponseDataVO headerResponseDataVO = new HeaderResponseDataVO();
//            headerResponseDataVO.setRtc("-1");
//            headerResponseDataVO.setEds("request param error");
//            apiResponseVO.setHeaderResponseDataVO(headerResponseDataVO);
//            response.getWriter().write(JSONObject.toJSONString(apiResponseVO));
//            return false;
//        }
//
//
//        ApiRequestVO apiRequestVO = JSONObject.parseObject(body, ApiRequestVO.class);
//        String token  = apiRequestVO.getHeaderRequestDataVO().getUtk();
//        if(token==null||token.trim().equals("")){
//            ApiResponseVO apiResponseVO = new ApiResponseVO();
//            HeaderResponseDataVO headerResponseDataVO = new HeaderResponseDataVO();
//            headerResponseDataVO.setRtc("-1");
//            headerResponseDataVO.setEds("login time out");
//            apiResponseVO.setHeaderResponseDataVO(headerResponseDataVO);
//            response.getWriter().write(JSONObject.toJSONString(apiResponseVO));
//            return false;
//        }
//        userInfo = CacheUtils.getStr(token);
//
//        if(userInfo==null){
//            ApiResponseVO apiResponseVO = new ApiResponseVO();
//            HeaderResponseDataVO headerResponseDataVO = new HeaderResponseDataVO();
//            headerResponseDataVO.setRtc("-1");
//            headerResponseDataVO.setEds("login time out");
//            apiResponseVO.setHeaderResponseDataVO(headerResponseDataVO);
//            response.getWriter().write(JSONObject.toJSONString(apiResponseVO));
//            return false;
//        }else{
//            return true;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {

    }
}
