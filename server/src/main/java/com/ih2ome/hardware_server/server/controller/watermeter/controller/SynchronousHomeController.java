package com.ih2ome.hardware_server.server.controller.watermeter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.hardware_service.service.vo.HomeSyncVO;
import com.ih2ome.hardware_service.service.vo.HouseVO;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
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
        JSONArray jsonArray = dt.getJSONArray("apartmentIds");
        int[] apartmentIds = new int[jsonArray.size()];
        for (int i=0;i<=jsonArray.size();i++){
            apartmentIds[i]= (int) jsonArray.get(i);
        }

        String home_id = null;
        try {
            for (int apartmentId : apartmentIds) {
                home_id = synchronousHomeService.synchronousHousingByApartmenId(apartmentId);
            }
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
        responseJson.put("result",home_id);
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
        String result = null;
        try {
            result = synchronousHomeService.synchronousHousingByFloorId(apartmentId,floorId);
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
        responseJson.put("result",result);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 查询集中式房源是否已同步
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/synchronous_housing/FindjzHomeIsSynchronoused",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindJZHomesIsSynchronoused(@RequestBody ApiRequestVO apiRequestVO) {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = (int) dt.get("userId");

        List<HomeSyncVO> homeSyncVOS = null;

        homeSyncVOS = synchronousHomeService.findHomeIsSynchronousedByUserId(userId);

        JSONObject responseJson = new JSONObject();
        responseJson.put("homeSyncVOS",homeSyncVOS);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 查询房源是否已同步byhomeId（从第三方查询）
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/synchronous_housing/FindHomeIsSynchronoused",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindHomeIsSynchronoused(@RequestBody ApiRequestVO apiRequestVO)  {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int homeId = dt.getIntValue("homeId");
        int type = dt.getIntValue("type");
        YunDingResponseVo yunDingResponseVo = null;
        try {
            yunDingResponseVo = synchronousHomeService.findHomeIsSynchronousedByHomeId(homeId,type);
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (AmmeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        }
        JSONObject responseJson = new JSONObject();
        responseJson.put("home_state",yunDingResponseVo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 查询分散式房源是否已同步byuserId
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/synchronous_housing/FindHomeIsSynchronouseds",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindHomeIsSynchronouseds(@RequestBody ApiRequestVO apiRequestVO)  {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = (int) dt.get("id");

        List<HomeSyncVO> homeSyncVOS = null;

        homeSyncVOS = synchronousHomeService.findHmHomeIsSynchronousedByUserId(userId);

        JSONObject responseJson = new JSONObject();
        responseJson.put("homeSyncVOS",homeSyncVOS);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 分散式用户房源
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/synchronous_housing/houses",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindHouse(@RequestBody ApiRequestVO apiRequestVO){
        //获取用户id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int id = dt.getIntValue("userId");
        List<HouseVO> houseVOS = synchronousHomeService.findHouseByUserId(id);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(houseVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("houseVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 分散式用户房源同步
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/synchronous_housing/byhouse",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingByHouse(@RequestBody ApiRequestVO apiRequestVO) {
        //获取用户id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        JSONArray jsonArray = dt.getJSONArray("houseIds");
        int[] houseIds = new int[jsonArray.size()];
        for (int i=0;i<jsonArray.size();i++){
            houseIds[i]= (int) jsonArray.get(i);
        }

        String home_id = null;
        try {
            for (int houseId:houseIds) {
                home_id = synchronousHomeService.synchronousHousingByHouseId(houseId);
            }
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
        responseJson.put("result",home_id);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }


}
