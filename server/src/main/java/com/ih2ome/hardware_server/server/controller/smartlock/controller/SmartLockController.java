package com.ih2ome.hardware_server.server.controller.smartlock.controller;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SmartLockService;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sky
 * @create 2018/01/22
 * @email sky.li@ixiaoshuidi.com
 **/
@RestController
@RequestMapping("/smartlock")
@CrossOrigin
public class SmartLockController extends BaseController {
    private final Logger Log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SmartLockService smartLockService;

    /**
     * 关联房源查询房屋信息
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "search/home", method = RequestMethod.POST, produces = {"application/json"})
    public String searchHome(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        
        return null;
    }
}