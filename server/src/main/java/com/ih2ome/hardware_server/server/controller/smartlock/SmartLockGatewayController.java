package com.ih2ome.hardware_server.server.controller.smartlock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayService;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockDetailVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayAndHouseInfoVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayHadBindVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockHadBindHouseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger Log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SmartLockGatewayService smartLockGatewayService;
    /**
     * 取当前房源下的所有网关
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/search/gatewayList", method = RequestMethod.POST, produces = {"application/json"})
    public String getGatewayList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        String homeId = dt.getString("homeId");
        String type = dt.getString("type");
        if(StringUtils.isEmpty(homeId)){
            return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"homeId为空");

        }
        List <SmartLockGatewayAndHouseInfoVO> smartLockGatewayAndHouseInfoVOList = smartLockGatewayService.getSmartLockGatewayList(homeId,type);
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartLockGatewayAndHouseInfoVOList", smartLockGatewayAndHouseInfoVOList);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;

    }

    /**
     * 查询当前网关绑定的门锁
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/search/smartLockHadBindGatewayList", method = RequestMethod.POST, produces = {"application/json"})
    public String getSmartLockHadBindGateway(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        String gatewayId = dt.getString("gatewayId");
        if(StringUtils.isEmpty(gatewayId)){
            return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"gatewayId为空");

        }
        SmartLockGatewayHadBindVO model = smartLockGatewayService.getSmartLockHadBindGateway(gatewayId);
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartLockGatewayHadBindVO", model);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;

    }

    /**
     * 查询当前网关的详细信息
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/search/getSmartLockGatewayDetailInfo", method = RequestMethod.POST, produces = {"application/json"})
    public String getSmartLockGatewayDetailInfo(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        String gatewayId = dt.getString("gatewayId");
        if(StringUtils.isEmpty(gatewayId)){
            return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"gatewayId为空");

        }
        SmartLockDetailVO smartLockDetailVO = smartLockGatewayService.getSmartLockGatewayDetailInfo(gatewayId);
        smartLockDetailVO.setVersionJson(null);
        String result = structureSuccessResponseVO((JSONObject) JSON.toJSON(smartLockDetailVO), new Date().toString(), "");
        return result;

    }

    /**
     * 查询已绑定的房源列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/search/getHadBindHouseList", method = RequestMethod.POST, produces = {"application/json"})
    public String getHadBindHouseList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        String type = dt.getString("type");
        String userId = dt.getString("id");
        if(StringUtils.isEmpty(type)){
            return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"type为空");

        }
        if(StringUtils.isEmpty(userId)){
            return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"userId为空");

        }
        List<SmartLockHadBindHouseVo> smartLockGatewayServiceHadBindHouseList = smartLockGatewayService.getHadBindHouseList(type,userId);
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartLockGatewayServiceHadBindHouseList", smartLockGatewayServiceHadBindHouseList);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;

    }



}
