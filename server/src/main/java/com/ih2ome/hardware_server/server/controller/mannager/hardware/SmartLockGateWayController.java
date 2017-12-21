package com.ih2ome.hardware_server.server.controller.mannager.hardware;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
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
public class SmartLockGateWayController extends BaseController {


    @RequestMapping(value="/smartLockGateList",method = RequestMethod.POST,produces = {"application/json"})
    public String  smartLockGateList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject reqData=apiRequestVO.getDataRequestBodyVO().getDt();
        return null;
    }
}
