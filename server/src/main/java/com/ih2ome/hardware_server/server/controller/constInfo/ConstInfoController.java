package com.ih2ome.hardware_server.server.controller.constInfo;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
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

    @Value("${yd.login.url}")
    String yunDingLoginBaseUrl;
    @Value("${yd.login.client_id}")
    String yunDingClientId;
    @Value("${yd.smart_lock.permission_group}")
    String yunDingPermissionGroup;
    @Value("${yd.smart_lock.call_back.url}")
    String yunDingCallBackUrl;

    final static String yunDingCallBackState = "success";

    @RequestMapping(value="/setAmmeterAlarmRules",method = RequestMethod.POST,produces = {"application/json"})
    public String getYunDingLoginPage(@RequestBody ApiRequestVO apiRequestVO){
        StringBuilder url = new StringBuilder();
        url.append(yunDingLoginBaseUrl)
                .append("?client_id=")
                .append(yunDingClientId)
                .append("&redirect_uri=")
                .append(yunDingCallBackUrl)
                .append("&scope=")
                .append(yunDingPermissionGroup)
                .append("&state=")
                .append(yunDingCallBackState);
        JSONObject urlObject = new JSONObject();
        urlObject.put("url",url);
        return structureSuccessResponseVO(urlObject,new Date().toString(),"获取成功");
    }
}
