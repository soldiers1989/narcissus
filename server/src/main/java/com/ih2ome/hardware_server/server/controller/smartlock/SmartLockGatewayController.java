package com.ih2ome.hardware_server.server.controller.smartlock;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayService;
import com.ih2ome.sunflower.model.house.HomeModel;
import com.ih2ome.sunflower.model.house.SmartLockGatewayModel;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayHadBindVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/smartLockGateway")
@CrossOrigin
public class SmartLockGatewayController extends BaseController{

    @Autowired
    SmartLockGatewayService smartLockGatewayService;
    /**
     * 取当前房源下的所有网关
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/search/gatewayList", method = RequestMethod.POST, produces = {"application/json"})
    public String getGatewayList(@RequestBody ApiRequestVO apiRequestVO){
        HomeModel homeModel = apiRequestVO.getDataRequestBodyVO().getDt().getObject("homeModel", HomeModel.class);
        if(StringUtils.isEmpty(homeModel.getHomeId())){
            return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"homeId为空");

        }
        List <SmartLockGatewayModel> smartLockGatewayModelList = smartLockGatewayService.getSmartLockGatewayList(homeModel.getHomeId());
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartLockGatewayList", smartLockGatewayModelList);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;

    }

    @RequestMapping(value = "/search/smartLockHadBindGatewayList", method = RequestMethod.POST, produces = {"application/json"})
    public String getSmartLockHadBindGateway(@RequestBody ApiRequestVO apiRequestVO){
        SmartLockGatewayModel smartLockGatewayModel = apiRequestVO.getDataRequestBodyVO().getDt().getObject("smartLockGatewayModel", SmartLockGatewayModel.class);
        if(StringUtils.isEmpty(smartLockGatewayModel.getGatewayId())){
            return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"gatewayId为空");

        }
        SmartLockGatewayHadBindVO model = smartLockGatewayService.getSmartLockHadBindGateway(smartLockGatewayModel.getGatewayId());
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartLockGatewayHadBindVO", model);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;

    }



}
