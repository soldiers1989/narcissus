package com.ih2ome.hardware_server.server.controller.alarm;

import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import org.springframework.web.bind.annotation.*;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/ammeterAlarm")
public class ammeterAlarmController extends BaseController {

    /**
     * 设置电表报警规则
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/setAmmeterAlarmRules",method = RequestMethod.POST,produces = {"application/json"})
    public String setAmmeterAlarmRules(@RequestBody ApiRequestVO apiRequestVO){
        return "";
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
    @RequestMapping(value="/ammeterAlarmRulesInfo/{apiRequestVO}",method = RequestMethod.GET,produces = {"application/json"})
    public String ammeterAlarmRulesInfo(@PathVariable String apiRequestVO){
        return "";
    }
}
