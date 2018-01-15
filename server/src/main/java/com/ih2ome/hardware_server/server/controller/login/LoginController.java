package com.ih2ome.hardware_server.server.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.common.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/9
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/user/login")
@CrossOrigin
public class LoginController extends BaseController{

    @Value("${sso.host}")
    String host;

    @RequestMapping(value="/login",method = RequestMethod.POST,produces = {"application/json"})
    public String login(@RequestBody ApiRequestVO apiRequestVO){
        return HttpClientUtil.doPost(host+"/sso/api", JSONObject.toJSON(apiRequestVO).toString());
    }
}
