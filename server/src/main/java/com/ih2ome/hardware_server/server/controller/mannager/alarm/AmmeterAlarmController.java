package com.ih2ome.hardware_server.server.controller.mannager.alarm;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.model.narcissus.SmartAlarmRule;
import com.ih2ome.hardware_service.service.service.AmmeterAlarmService;
import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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
     * 查看报警信息
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/ammeterAlarmInfoList",method = RequestMethod.POST,produces = {"application/json"})
    public String dispersedAmmeterAlarmInfoList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        AmmeterMannagerVo ammeterMannagerVo = resData.getObject("ammeterMannagerVo",AmmeterMannagerVo.class);
        List<AmmeterMannagerVo> ammeterMannagerVoList = ammeterAlarmService.findAmmeterAlarmInfoList(ammeterMannagerVo);
        PageInfo <AmmeterMannagerVo> pageInfo = new PageInfo<>(ammeterMannagerVoList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("ammeterMannagerVoList",pageInfo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 查看电表报警规则
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/ammeterAlarmRulesInfo",method = RequestMethod.POST,produces = {"application/json"})
    public String ammeterAlarmRulesInfo(@RequestBody ApiRequestVO apiRequestVO){
        List<SmartAlarmRule> smartAlarmRuleList = ammeterAlarmService.getAllSmartAlarmRules();
        JSONObject resJson = new JSONObject();
        resJson.put("smartAlarmRuleList",smartAlarmRuleList);
        return structureSuccessResponseVO(resJson,new Date().toString(),"创建成功");
    }
}
