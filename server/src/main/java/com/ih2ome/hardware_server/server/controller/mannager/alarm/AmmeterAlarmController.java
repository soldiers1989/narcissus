package com.ih2ome.hardware_server.server.controller.mannager.alarm;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.model.narcissus.SmartAlarmRule;
import com.ih2ome.hardware_service.service.service.AmmeterAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/ammeterAlarm")
public class AmmeterAlarmController extends BaseController {

    @Autowired
    AmmeterAlarmService ammeterAlarmService;
    /**
     * 设置电表报警规则
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/setAmmeterAlarmRules",method = RequestMethod.POST,produces = {"application/json"})
    public String setAmmeterAlarmRules(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        SmartAlarmRule smartReport = resData.getObject("smartReport", SmartAlarmRule.class);
        ammeterAlarmService.saveAmmeterAlarmRules(smartReport);
        return structureSuccessResponseVO(null,new Date().toString(),"创建成功");
    }

    /**
     * 查看所有报警信息
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/ammeterAlarmInfoList/{apiRequestVO}",method = RequestMethod.GET,produces = {"application/json"})
    public String ammeterAlarmInfoList(@PathVariable String apiRequestVO){
        return "";
    }

    /**
     * 查看电表报警规则
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/ammeterAlarmRulesInfo",method = RequestMethod.POST,produces = {"application/json"})
    public String ammeterAlarmRulesInfo(@RequestBody ApiRequestVO apiRequestVO){

        return "";
    }
}
