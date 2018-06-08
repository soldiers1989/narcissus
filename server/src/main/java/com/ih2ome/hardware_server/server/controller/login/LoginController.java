package com.ih2ome.hardware_server.server.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
public class LoginController extends BaseController {

    @Value("${sso.host}")
    String host;

    @Autowired
    private WatermeterService watermeterService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {"application/json"})
    public String login(@RequestBody ApiRequestVO apiRequestVO) {
        return HttpClientUtil.doPost(host + "/sso/api", JSONObject.toJSON(apiRequestVO).toString());
    }

    @RequestMapping(value = "/yunding/first", method = RequestMethod.POST, produces = {"application/json"})
    public String getYundingFirst(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = resData.getIntValue("userId");
        Integer yundingFirst = watermeterService.getYundingFirst(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isYundingFirstLogin", yundingFirst == null || yundingFirst.equals(0));
        return structureSuccessResponseVO(jsonObject, new Date().toString(), "查询成功");
    }
}
