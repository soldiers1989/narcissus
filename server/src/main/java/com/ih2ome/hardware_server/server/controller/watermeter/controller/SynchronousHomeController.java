package com.ih2ome.hardware_server.server.controller.watermeter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.peony.watermeterInterface.vo.YunDingResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 同步房源Controller
 */
@RestController
@RequestMapping("/watermeter")
public class SynchronousHomeController  extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SynchronousHomeService synchronousHomeService;


    /**
     * 集中式同步房源整栋
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/synchronous_housing/byapartment",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindApartment(@RequestBody ApiRequestVO apiRequestVO)  {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int apartmentId = dt.getIntValue("apartmentId");
        String home_id = null;
        try {
            home_id = synchronousHomeService.synchronousHousingByApartmenId(apartmentId);
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        }

        JSONObject responseJson = new JSONObject();
        responseJson.put("home_id",home_id);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;

    }

    /**
     * 同步房源一层一层同步
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/synchronous_housing/byfloor",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingByFloor(@RequestBody ApiRequestVO apiRequestVO) {
        //获取楼层id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int apartmentId = dt.getIntValue("apartmentId");
        int floorId = dt.getIntValue("floorId");
        String home_id = null;
        try {
            home_id = synchronousHomeService.synchronousHousingByFloorId(apartmentId,floorId);
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        }

        JSONObject responseJson = new JSONObject();
        responseJson.put("home_id",home_id);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 查询集中式房源是否已同步
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/synchronous_housing/FindjzHomeIsSynchronoused",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindJZHomesIsSynchronoused(@RequestBody ApiRequestVO apiRequestVO) {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        JSONArray json = dt.getJSONArray("apartmentIds");
        String[] homeIds = new String[json.size()];
        for (int i=0;i<json.size();i++) {
            homeIds[i] = json.get(i).toString();
        }

        List<YunDingResponseVo> yunDingResponseVos = null;
        try {
            yunDingResponseVos = synchronousHomeService.findHomeIsSynchronousedByHomeIds(homeIds);
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"查询失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"查询失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"查询失败"+e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"查询失败"+e.getMessage());
            return res;
        }
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(yunDingResponseVos));
        JSONObject responseJson = new JSONObject();
        responseJson.put("houseVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }




}
