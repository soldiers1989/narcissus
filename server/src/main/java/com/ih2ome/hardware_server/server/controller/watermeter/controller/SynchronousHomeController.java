package com.ih2ome.hardware_server.server.controller.watermeter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.sunflower.vo.pageVo.watermeter.HomeSyncVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.HouseVO;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.YunDingResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 同步房源Controller
 */
@RestController
@RequestMapping("/watermeter")
@CrossOrigin
public class SynchronousHomeController  extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SynchronousHomeService synchronousHomeService;


    /**
     * 集中式同步房源整栋 B3
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/synchronous_housing/byapartment",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindApartment(@RequestBody ApiRequestVO apiRequestVO)  {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        JSONArray jsonArray = dt.getJSONArray("apartmentIds");
        int[] apartmentIds = new int[jsonArray.size()];
        for (int i=0;i<jsonArray.size();i++){
            apartmentIds[i]= (int) jsonArray.get(i);
        }

        String home_id = null;
        try {
            for (int apartmentId : apartmentIds) {
                home_id = synchronousHomeService.synchronousHousingByApartmenId(apartmentId);
            }
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        }

        JSONObject responseJson = new JSONObject();
        responseJson.put("result",home_id);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;

    }

    /**
     * 同步房源一层一层同步 B4-2
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/synchronous_housing/byfloor",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingByFloor(@RequestBody ApiRequestVO apiRequestVO) {
        //获取楼层id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int apartmentId = dt.getIntValue("apartmentId");
        JSONArray floorIds = dt.getJSONArray("floorIds");
        String result = null;
        List<String> list =new ArrayList<>();
        try {
            for (int i=0;i<floorIds.size();i++ ) {
                Integer floorId = (Integer) floorIds.get(i);
                result = synchronousHomeService.synchronousHousingByFloorId(apartmentId,floorId);
                list.add(result);
            }

        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        }

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
        JSONObject responseJson = new JSONObject();
        responseJson.put("result",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 查询集中式房源s同步状态 B3-1
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/synchronous_housing/FindjzHomeIsSynchronoused",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindJZHomesIsSynchronoused(@RequestBody ApiRequestVO apiRequestVO) {
        //获取id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = (int) dt.get("id");

        List<HomeSyncVO> homeSyncVOS = null;

        homeSyncVOS = synchronousHomeService.findHomeIsSynchronousedByUserId(userId);

        JSONObject responseJson = new JSONObject();
        responseJson.put("homeSyncVOS",homeSyncVOS);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 查询集中式房源floors同步状态 B4-1
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/synchronous_housing/FindjzFloorsIsSynchronoused",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindJZFloorsIsSynchronoused(@RequestBody ApiRequestVO apiRequestVO) {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int apartmentId = (int) dt.get("apartmentId");

        List<HomeSyncVO> homeSyncVOS = null;

        homeSyncVOS = synchronousHomeService.findFloorsIsSynchronousedByApartmentId(apartmentId);

        JSONObject responseJson = new JSONObject();
        responseJson.put("homeSyncVOS",homeSyncVOS);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
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
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (AmmeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        }
        JSONObject responseJson = new JSONObject();
        responseJson.put("home_state",yunDingResponseVo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 查询分散式房源是否已同步byuserId
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/synchronous_housing/FindHomeIsSynchronouseds",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindHomeIsSynchronouseds(@RequestBody ApiRequestVO apiRequestVO)  {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = (int) dt.get("id");

        List<HomeSyncVO> homeSyncVOS = null;

        homeSyncVOS = synchronousHomeService.findHmHomeIsSynchronousedByUserId(userId);

        JSONObject responseJson = new JSONObject();
        responseJson.put("homeSyncVOS",homeSyncVOS);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 分散式用户房源 D3
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/synchronous_housing/houses",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindHouse(@RequestBody ApiRequestVO apiRequestVO){
        //获取用户id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int id = dt.getIntValue("id");
        List<HouseVO> houseVOS = synchronousHomeService.findHouseByUserId(id);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(houseVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("houseVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 分散式用户房源同步 D10
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
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        }

        JSONObject responseJson = new JSONObject();
        responseJson.put("result",home_id);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 查询集中式未同步room
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/synchronous_housing/FindjzRoomsIsSynchronoused",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindJZRoomsIsSynchronoused(@RequestBody ApiRequestVO apiRequestVO) {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int apartmentId = (int) dt.get("apartmentId");

        List<HomeSyncVO> homeSyncVOS = null;

        homeSyncVOS = synchronousHomeService.findRoomsIsSynchronousedByApartmentId(apartmentId);

        JSONObject responseJson = new JSONObject();
        responseJson.put("homeSyncVOS",homeSyncVOS);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 查询分散式未同步room
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/synchronous_housing/FindhmRoomsIsSynchronoused",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindHMRoomsIsSynchronoused(@RequestBody ApiRequestVO apiRequestVO) {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = (int) dt.get("houseId");

        List<HomeSyncVO> homeSyncVOS = null;

        homeSyncVOS = synchronousHomeService.findHmRoomsIsSynchronousedByUserId(userId);

        JSONObject responseJson = new JSONObject();
        responseJson.put("homeSyncVOS",homeSyncVOS);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 集中式同步房源byrooms
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/synchronous_housing/byrooms",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingByRooms(@RequestBody ApiRequestVO apiRequestVO) {
        //获取楼层id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int apartmentId = dt.getIntValue("apartmentId");
        JSONArray jsonArray = dt.getJSONArray("roomIds");
        int[] roomIds = new int[jsonArray.size()];
        for (int i=0;i<jsonArray.size();i++){
            roomIds[i]= (int) jsonArray.get(i);
        }
        String reslut=null;
        try {
            reslut = synchronousHomeService.synchronousHousingByRooms(apartmentId, roomIds);

        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        }

        JSONObject responseJson = new JSONObject();
        responseJson.put("result",reslut);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }


    /**
     * 分散式同步房源byrooms
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/synchronous_housing/byrooms",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingByHmRooms(@RequestBody ApiRequestVO apiRequestVO) {
        //获取楼层id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        //int apartmentId = dt.getIntValue("apartmentId");
        JSONArray jsonArray = dt.getJSONArray("roomIds");
        int[] roomIds = new int[jsonArray.size()];
        for (int i=0;i<jsonArray.size();i++){
            roomIds[i]= (int) jsonArray.get(i);
        }
        String reslut=null;
        try {
            for (int roomId:roomIds) {
                reslut = synchronousHomeService.synchronousHousingByHmRooms(roomId);
            }
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
            return res;
        }

        JSONObject responseJson = new JSONObject();
        responseJson.put("result",reslut);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

}
