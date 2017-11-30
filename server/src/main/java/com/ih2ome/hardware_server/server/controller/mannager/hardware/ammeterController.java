package com.ih2ome.hardware_server.server.controller.mannager.hardware;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.AmmeterManagerService;
import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import com.ih2ome.hardware_service.service.vo.DeviceIdAndName;
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
 * create by 2017/11/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/ammeter")
public class ammeterController extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmmeterManagerService ammeterManagerService;

    /**
     * 分散式房源list
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/dispersedList",method = RequestMethod.POST,produces = {"application/json"})
    public String dispersedList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        AmmeterMannagerVo ammeterMannagerVo = resData.getObject("ammeterMannagerVo",AmmeterMannagerVo.class);
        List<AmmeterMannagerVo> ammeterMannagerVoList = ammeterManagerService.findConcentratAmmeter(ammeterMannagerVo);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(ammeterMannagerVoList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("ammeterMannagerVoList",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 集中式房源list
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/concentratedList",method = RequestMethod.POST,produces = {"application/json"})
    public String concentratedList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        AmmeterMannagerVo ammeterMannagerVo = resData.getObject("ammeterMannagerVo",AmmeterMannagerVo.class);
        List<AmmeterMannagerVo> ammeterMannagerVoList = ammeterManagerService.findDispersedAmmeter(ammeterMannagerVo);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(ammeterMannagerVoList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("ammeterMannagerVoList",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 查看电表关系
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/ammeterRelation",method = RequestMethod.POST,produces = {"application/json"})
    public String ammeterRelation(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        String id = resData.getString("id");
        String type = resData.getString("type");
        DeviceIdAndName deviceVo = ammeterManagerService.getAmmeterRelation(id,type);
        JSONObject responseJson = new JSONObject();
        responseJson.put("deviceVo",deviceVo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 电表信息查询
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/ammeterInfo/{apiRequestVO}",method = RequestMethod.GET,produces = {"application/json"})
    public String ammeterInfo(@PathVariable String apiRequestVO){
        return "";
    }

    /**
     * 使用场景变更
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/scenarioUpdate",method = RequestMethod.PUT,produces = {"application/json"})
    public String scenarioUpdate(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    /**
     * 付费模式变更
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/payMatureUpdate",method = RequestMethod.PUT,produces = {"application/json"})
    public String payMatureUpdate(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    /**
     * 修改电费单价
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/unitPriceOfElectricityUpdate",method = RequestMethod.PUT,produces = {"application/json"})
    public String unitPriceOfElectricityUpdate(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    /**
     * 电表通断电操作
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/operateAmmeter",method = RequestMethod.POST,produces = {"application/json"})
    public String operateAmmeter(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }


}
