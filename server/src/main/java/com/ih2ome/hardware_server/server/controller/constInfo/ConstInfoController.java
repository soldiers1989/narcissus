package com.ih2ome.hardware_server.server.controller.constInfo;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
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
            //String refrashToken = CacheUtils.getStr(YunDingSmartLockUtil.REFRESH_TOKEN_KEY+"_"+userId);

            JSONObject urlObject = new JSONObject();

            if(StringUtils.isBlank(code)&&StringUtils.isBlank(token)){
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
            }else{
                try {
                    YunDingSmartLockUtil.flushRefreshToken(userId);
                    urlObject.put("loginStatus","0");
                } catch (SmartLockException e) {
                    e.getMessage();
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

                }
                return structureSuccessResponseVO(urlObject,new Date().toString(),"获取成功");
            }

        }
        return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"userId为空");

    }

    @RequestMapping(value="/del/yunding/user/token/",method = RequestMethod.POST,produces = {"application/json"})
    public String delYunDingUserToken(@RequestBody ApiRequestVO apiRequestVO){
        String userId = apiRequestVO.getDataRequestBodyVO().getDt().getString("id");
        String result = "";
        if(StringUtils.isNotBlank(userId)) {
            String tokenKey = YunDingSmartLockUtil.ACCESS_TOKEN_KEY + "_" + userId;
            String refrashTokenKey = YunDingSmartLockUtil.REFRESH_TOKEN_KEY + "_" + userId;
            String codeKey = YunDingSmartLockUtil.TOKEN_YUNDING_USER_CODE + "_" + userId;
            CacheUtils.del(tokenKey);
            CacheUtils.del(refrashTokenKey);
            CacheUtils.del(codeKey);
            result = structureSuccessResponseVO(new JSONObject(),new Date().toString(),"清除登陆缓存成功");
        }else{
            result = structureSuccessResponseVO(new JSONObject(),new Date().toString(),"userId为空");
        }
        return result;
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

        return result;

    }

}
