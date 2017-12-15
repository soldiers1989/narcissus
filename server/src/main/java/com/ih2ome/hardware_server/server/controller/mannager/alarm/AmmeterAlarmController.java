package com.ih2ome.hardware_server.server.controller.mannager.alarm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.model.narcissus.SmartAlarmRule;
import com.ih2ome.hardware_service.service.service.AmmeterAlarmService;
import com.ih2ome.hardware_service.service.vo.AmmeterAlarmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@CrossOrigin
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
        JSONArray smartReportArr = resData.getJSONArray("smartReportList");
        List <SmartAlarmRule> smartAlarmRuleList = new ArrayList<>();
        for(Object o:smartReportArr){
            smartAlarmRuleList.add((JSONObject.parseObject(o.toString(),SmartAlarmRule.class)));
        }
        ammeterAlarmService.saveAmmeterAlarmRules(smartAlarmRuleList);
        return structureSuccessResponseVO(null,new Date().toString(),"创建成功");
    }

    /**
     * 查看报警信息
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/ammeterAlarmInfoList",method = RequestMethod.POST,produces = {"application/json"})
    public String ammeterAlarmInfoList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        AmmeterAlarmVo ammeterAlarmVo = resData.getObject("ammeterAlarmVo",AmmeterAlarmVo.class);
        List<AmmeterAlarmVo> ammeterAlarmVoList = ammeterAlarmService.findAmmeterAlarmInfoList(ammeterAlarmVo);
        PageInfo <AmmeterAlarmVo> pageInfo = new PageInfo<>(ammeterAlarmVoList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("ammeterAlarmVoList",pageInfo);
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
