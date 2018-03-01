package com.ih2ome.hardware_server.server.controller.constInfo;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.peony.smartlockInterface.yunding.util.YunDingSmartLockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/18
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/constInfo/constInfo")
@CrossOrigin
public class ConstInfoController extends BaseController{

    private final Logger Log = LoggerFactory.getLogger(this.getClass());
    @Value("${yd.login.url}")
    String yunDingLoginBaseUrl;
    @Value("${yd.client_id}")
    String yunDingClientId;
    @Value("${yd.smart_lock.permission_group}")
    String yunDingPermissionGroup;
    @Value("${yd.smart_lock.call_back.url}")
    String yunDingCallBackUrl;

    @RequestMapping(value="/setAmmeterAlarmRules",method = RequestMethod.POST,produces = {"application/json"})
    public String getYunDingLoginPage(@RequestBody ApiRequestVO apiRequestVO){
        String userId = apiRequestVO.getDataRequestBodyVO().getDt().getString("id");
        if(StringUtils.isNotBlank(userId)){
            StringBuilder url = new StringBuilder();
            String tokenKey = YunDingSmartLockUtil.ACCESS_TOKEN_KEY+"_"+userId;
            String codeKey = YunDingSmartLockUtil.TOKEN_YUNDING_USER_CODE+"_"+userId;
            String token = CacheUtils.getStr(tokenKey);
            String code = CacheUtils.getStr(codeKey);
            JSONObject urlObject = new JSONObject();
            if(StringUtils.isNotBlank(code)||StringUtils.isNotBlank(token)){
                Log.info(code+"*************************************"+token);
                urlObject.put("loginStatus","0");
                return structureSuccessResponseVO(urlObject,new Date().toString(),"获取成功");
            }else{
                url.append(yunDingLoginBaseUrl)
                        .append("?client_id=")
                        .append(yunDingClientId)
                        .append("&redirect_uri=")
                        .append(yunDingCallBackUrl)
                        .append("&scope=")
                        .append(yunDingPermissionGroup)
                        .append("&state=")
                        .append(userId);
                urlObject.put("url",url);
                urlObject.put("loginStatus","1");
                return structureSuccessResponseVO(urlObject,new Date().toString(),"获取成功");
            }
        }
        return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"userId为空");

    }

    @RequestMapping(value="/del/yunding/user/token/{userId}",method = RequestMethod.GET,produces = {"application/json"})
    public String delYunDingUserToken(@PathVariable String userId){
        Log.info("清除登陆缓存{}",userId);
        String tokenKey = YunDingSmartLockUtil.ACCESS_TOKEN_KEY+"_"+userId;
        String refrashTokenKey = YunDingSmartLockUtil.REFRESH_TOKEN_KEY+"_"+userId;
        String codeKey = YunDingSmartLockUtil.TOKEN_YUNDING_USER_CODE+"_"+userId;
        CacheUtils.del(tokenKey);
        CacheUtils.del(refrashTokenKey);
        CacheUtils.del(codeKey);
        return "success";
    }

    @RequestMapping(value="/getYunDingLoginStatus",method = RequestMethod.POST,produces = {"application/json"})
    public String getYunDingLoginStatus(@RequestBody ApiRequestVO apiRequestVO){
        String userId = apiRequestVO.getDataRequestBodyVO().getDt().getString("id");
        String result = "";
        if(StringUtils.isNotBlank(userId)){
            String tokenKey = YunDingSmartLockUtil.ACCESS_TOKEN_KEY+"_"+userId;
            String codeKey = YunDingSmartLockUtil.TOKEN_YUNDING_USER_CODE+"_"+userId;
            String token = CacheUtils.getStr(tokenKey);
            String code = CacheUtils.getStr(codeKey);
            if(StringUtils.isNotBlank(code)||StringUtils.isNotBlank(token)){
                result = structureSuccessResponseVO(new JSONObject(),new Date().toString(),"授权成功");
            }else{
                result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"授权失败");
            }

        }else{
            result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"授权失败");
        }
        Log.info("*********************************");
        Log.info(result);
        Log.info("*********************************");
        return result;

    }

}
