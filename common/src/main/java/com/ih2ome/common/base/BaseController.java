package com.ih2ome.common.base;

import com.alibaba.fastjson.JSONObject;
import com.ih2home.common.api.enums.ApiErrorCodeEnum;
import com.ih2home.common.api.vo.request.ApiRequestVO;
import com.ih2home.common.api.vo.response.ApiResponseVO;
import com.ih2home.common.api.vo.response.DataResponseBodyVO;
import com.ih2home.common.api.vo.response.HeaderResponseDataVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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


}
