package com.ih2ome.common.base;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.api.vo.response.ApiResponseVO;
import com.ih2ome.common.api.vo.response.DataResponseBodyVO;
import com.ih2ome.common.api.vo.response.HeaderResponseDataVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/23
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    /**
     * 从请求中获取数据字符串
     * @param requestVoStrCode
     * @return
     */
    public static String getDataStr(String requestVoStrCode){
        ApiRequestVO apiRequestVO = JSONObject.parseObject(requestVoStrCode,ApiRequestVO.class);
        return apiRequestVO.getDataRequestBodyVO().getDt();
    }

    /**
     * 从请求中获取数据对象
     * @param requestVoStrCode
     * @param clazz
     * @return
     */
    public static Object getDataObject(String requestVoStrCode,Class clazz){
        String objectStr = getDataStr(requestVoStrCode);
        return JSONObject.parseObject(objectStr,clazz);
    }

    /**
     * 构建成功响应数据
     * @param data
     * @param reqTime
     * @param eds
     * @param salt
     * @return
     */
    public static String structureSuccessResponseVO(String data,String reqTime,String eds,String salt){
        return randerMsg(ApiErrorCodeEnum.Service_yewu_ok,data,reqTime,eds,salt);
    }

    /**
     * 构建数据
     * @param apiErrorCodeEnum
     * @param data
     * @param reqTime
     * @param eds
     * @param salt
     * @return
     */
    public static String randerMsg(ApiErrorCodeEnum apiErrorCodeEnum, String data,String reqTime,String eds,String salt){
        ApiResponseVO apiResponseVO = new ApiResponseVO();
        HeaderResponseDataVO headerDataVO = new HeaderResponseDataVO();
        DataResponseBodyVO dataBodyVO = new DataResponseBodyVO();
        String rnd = com.ih2ome.common.utils.ConstUtils.getResponseRandomStr();
        headerDataVO.setRnd(rnd);
        headerDataVO.setCrt(reqTime);
        headerDataVO.setEds(eds);
        headerDataVO.setRtc(apiErrorCodeEnum.getCode()+"");
        headerDataVO.setToken(com.ih2ome.common.utils.ConstUtils.md5(rnd+reqTime+apiErrorCodeEnum.getCode()+salt+data));
        dataBodyVO.setDt(data);
        apiResponseVO.setDataResponseBodyVO(dataBodyVO);
        apiResponseVO.setHeaderResponseDataVO(headerDataVO);
        String responseVoStr = JSONObject.toJSONString(apiResponseVO);
        return responseVoStr;

    }

    /**
     * 渲染输出数据
     *
     * @param content
     *            输出内容字符串
     * @param contentType
     *            内容类型
     * @param response
     *            响应对象
     */
    private void render(String content, String contentType, HttpServletResponse response) {
        try {
            HttpServletResponse localHttpServletResponse = response;
            if (localHttpServletResponse != null) {
                localHttpServletResponse.setHeader("Pragma", "No-cache");
                localHttpServletResponse.setHeader("Cache-Control", "no-cache");
                localHttpServletResponse.setDateHeader("Expires", 0L);
                localHttpServletResponse.setContentType(contentType);
                localHttpServletResponse.getWriter().write(content);
            }
        } catch (IOException e) {
            Log.error(e.getMessage(), e);
        }
    }

    /**
     * 渲染输出Text文本
     *
     * @param content
     *            输出内容字符串
     * @param response
     *            响应对象
     */
    protected void renderText(String content, HttpServletResponse response) {
        render(content, "text/plain; charset=UTF-8", response);
    }

    /**
     * 渲染输出JSON文本
     *
     * @param content
     *            输出内容字符串
     * @param response
     *            响应对象
     */
    protected void renderJson(String content, HttpServletResponse response) {
        render(content, "application/json; charset=UTF-8", response);
    }

    /**
     * 渲染输出HTML文本
     *
     * @param content
     *            输出内容字符串
     * @param response
     *            响应对象
     */
    protected void renderHtml(String content, HttpServletResponse response) {
        render(content, "text/html; charset=UTF-8", response);
    }

    /**
     * 渲染输出Xml文本
     *
     * @param content
     *            输出内容字符串
     * @param response
     *            响应对象
     */
    protected void renderXml(String content, HttpServletResponse response) {
        render(content, "text/xml; charset=UTF-8", response);
    }


}
