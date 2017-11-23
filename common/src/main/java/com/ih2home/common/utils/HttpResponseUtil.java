package com.ih2home.common.utils;


import com.ih2home.common.api.enums.ApiErrorCodeEnum;
import com.ih2home.common.api.vo.request.ApiRequestVO;
import com.ih2home.common.api.vo.response.ApiResponseVO;
import com.ih2home.common.api.vo.response.DataResponseBodyVO;
import com.ih2home.common.api.vo.response.HeaderResponseDataVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpResponseUtil {


    public static void sendJson(HttpServletResponse response, String json) {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(json);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    public static ApiResponseVO getApiResponseVo(ApiRequestVO apiRequestVO, String token, String dataJson){
        //返回ApiResponseVO
        ApiResponseVO apiResponseVO = new ApiResponseVO();
        DataResponseBodyVO dataResponseVO = apiResponseVO.getDataResponseBodyVO();
        dataResponseVO.setDt(dataJson);
        HeaderResponseDataVO headerResponseVO = apiResponseVO.getHeaderResponseDataVO();
        //{"hd":{"rnd":"123456","crt":"20150102:040506","rtc":"0","eds":"","token":"XXXXXX"},"dt":
        headerResponseVO.setRnd(apiRequestVO.getHeaderRequestDataVO().getRnd());
        headerResponseVO.setCrt(apiRequestVO.getHeaderRequestDataVO().getCrt());
        headerResponseVO.setRtc(ApiErrorCodeEnum.Service_yewu_ok);
        headerResponseVO.setEds("");
        headerResponseVO.setToken(token);
        apiResponseVO.setHeaderResponseDataVO(headerResponseVO);
        apiResponseVO.setDataResponseBodyVO(dataResponseVO);
        return apiResponseVO;
    }


}
