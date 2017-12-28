package com.ih2ome.hardware_server.server.controller.mannager.hardware;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        return null;
    }
}
