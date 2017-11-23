package com.ih2home.utils.common.base;

import com.alibaba.fastjson.JSONObject;
import com.ih2home.utils.common.ConstUtils;
import com.ih2home.utils.common.enums.ApiErrorCodeEnum;
import com.ih2home.utils.common.vo.request.ApiRequestVO;
import com.ih2home.utils.common.vo.response.ApiResponseVO;
import com.ih2home.utils.common.vo.response.DataBodyVO;
import com.ih2home.utils.common.vo.response.HeaderDataVO;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/23
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class BaseController {

    /**
     * 从请求中获取数据字符串
     * @param requestVoStrCode
     * @return
     */
    public static String getDataStr(String requestVoStrCode){
        ApiRequestVO apiRequestVO = JSONObject.parseObject(requestVoStrCode,ApiRequestVO.class);
        return apiRequestVO.getDataBodyVO().getDt();
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


    public static String structureSuccessResponseVO(String data,String reqTime,String eds,String salt){
        ApiResponseVO apiResponseVO = new ApiResponseVO();
        HeaderDataVO headerDataVO = new HeaderDataVO();
        DataBodyVO dataBodyVO = new DataBodyVO();
        String rnd = ConstUtils.getResponseRandomStr();
        headerDataVO.setRnd(rnd);
        headerDataVO.setCrt(reqTime);
        headerDataVO.setEds(eds);
        headerDataVO.setRtc(ApiErrorCodeEnum.Service_yewu_ok);
        headerDataVO.setToken(ConstUtils.md5(rnd+reqTime+ApiErrorCodeEnum.Service_yewu_ok+salt+data));
        dataBodyVO.setDt(data);
        apiResponseVO.setDataBodyVO(dataBodyVO);
        apiResponseVO.setHeaderDataVO(headerDataVO);
        String responseVoStr = JSONObject.toJSONString(apiResponseVO);
        return responseVoStr;

    }


}
