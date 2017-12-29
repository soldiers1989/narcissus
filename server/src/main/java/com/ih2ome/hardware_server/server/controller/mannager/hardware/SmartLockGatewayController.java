package com.ih2ome.hardware_server.server.controller.mannager.hardware;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayService;
import com.ih2ome.hardware_service.service.vo.SmartDoorLockGatewayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/21
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/mannager/smartLockGateWay")
@CrossOrigin
public class SmartLockGatewayController extends BaseController {

    @Autowired
    SmartLockGatewayService smartLockGatewayService;

    @RequestMapping(value="/smartLockGateList",method = RequestMethod.POST,produces = {"application/json"})
    public String  smartLockGateList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject reqData=apiRequestVO.getDataRequestBodyVO().getDt();
        SmartDoorLockGatewayVO smartDoorLockGatewayVO = reqData.getObject("smartDoorLockGatewayVO",SmartDoorLockGatewayVO.class);
        List<SmartDoorLockGatewayVO> smartLockGatewayVOList = smartLockGatewayService.gatewayList(smartDoorLockGatewayVO);
        PageInfo <SmartDoorLockGatewayVO> pageInfo = new PageInfo<>(smartLockGatewayVOList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartLockGatewayVOList",pageInfo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }
}
