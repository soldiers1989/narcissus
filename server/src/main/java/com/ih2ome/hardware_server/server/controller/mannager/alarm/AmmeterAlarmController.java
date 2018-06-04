package com.ih2ome.hardware_server.server.controller.mannager.alarm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.AmmeterAlarmService;
import com.ih2ome.hardware_service.service.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.sunflower.entity.narcissus.SmartAlarmRule;
import com.ih2ome.sunflower.vo.pageVo.ammeter.AmmeterAlarmVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

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
        try {
            ammeterAlarmService.saveAmmeterAlarmRules(smartAlarmRuleList);
        } catch (AmmeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"创建失败");
            return res;
        }
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
        List<AmmeterAlarmVo> ammeterAlarmVoList = null;
        try {
            ammeterAlarmVoList = ammeterAlarmService.findAmmeterAlarmInfoList(ammeterAlarmVo);
        } catch (AmmeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"获取失败");
            return res;
        }
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
