package com.ih2ome.hardware_server.server.controller.watermeter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeterRecord;
import com.ih2ome.sunflower.vo.pageVo.watermeter.*;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 水表Controller
 */
@RestController
@RequestMapping("/watermeter")
@CrossOrigin
@Api("水表Controller")
public class WaterMeterController extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WatermeterService watermeterService;
    @Autowired
    private SynchronousHomeService synchronousHomeService;

    /**
     * 查询分散式水表列表 D1/D2
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/list",method = RequestMethod.POST,produces = {"application/json"})
    public String distributedList(@RequestBody ApiRequestVO apiRequestVO)  {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int id = dt.getIntValue("id");

        List<HMWatermeterListVO> watermeterList = watermeterService.findWatermetersByid(id);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterList",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 分散水表网关详情 D3
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/watermeter_gateway/detail",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterGatewayDetail(@RequestBody ApiRequestVO apiRequestVO){
        //通过网关id查询网关详情
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int smartGatewayId = dt.getIntValue("gatewayId");

        //查询网关详情
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterService.findhmGatewaybyId(smartGatewayId);
        //查询网关绑定的水表
        List<HMWatermeterListVO> watermeterDetailVOS = watermeterService.findWatermetersByGatewayId(smartGatewayId);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterDetailVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterGatewayDetailVO",watermeterGatewayDetailVO);
        responseJson.put("watermeterDetailVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 水费单价修改  C6
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/detail/updata_water_price",method = RequestMethod.POST,produces = {"application/json"})
    public String updataWaterPrice(@RequestBody ApiRequestVO apiRequestVO ){
        //获取更改价格
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        Float price = dt.getFloatValue("price");

        int watermeterId = dt.getIntValue("watermeterId");
        Boolean flag;
        try {
            flag = watermeterService.updataWaterPrice((int) (price*100),watermeterId);
        } catch (AmmeterException e) {
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
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        }

       JSONObject responseJson = new JSONObject();
        if (flag == true) {
            responseJson.put("result", "success");
        }
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    /**
     * 水表读数明细每月每天累计水量列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/smartwatermeterrecords/list",method = RequestMethod.POST,produces = {"application/json"})
    public String findTotalWaterList(@RequestBody ApiRequestVO apiRequestVO){
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        int page= dt.getIntValue("page");
        int count= dt.getIntValue("count");
        //通过水表id查询水表读数列表
        PageResult<SmartWatermeterRecord> pageResult= watermeterService.findWatermeterRecordByWatermeterId(watermeterId,page,count);
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartWatermeterRecords",pageResult);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 水表读数抄表请求 D6-1/D7-1
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/readwatermeteramount",method = RequestMethod.POST,produces = {"application/json"})
    public String readWatermeterAmount(@RequestBody ApiRequestVO apiRequestVO){
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        try {
            String code =watermeterService.readWatermeterLastAmountByWatermeterId(watermeterId);
            //请求成功
            JSONObject responseJson = new JSONObject();

            responseJson.put("result",code);
            String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
            return res;
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"请求失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"请求失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"请求失败"+e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"请求失败"+e.getMessage());
            return res;
        }
    }

    /**
     * 水表读数明细当月累计水量 D6-1/D7-1
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/findwateramount",method = RequestMethod.POST,produces = {"application/json"})
    public String findTotalWater(@RequestBody ApiRequestVO apiRequestVO) {
        //access_token,uuid水表id,manufactory水表供应商.请求地址/openapi/v1/read_watermeter
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        int amount =watermeterService.findWatermeterLastAmountByWatermeterId(watermeterId);

        JSONObject responseJson = new JSONObject();
        responseJson.put("amount",amount);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 水表读数明细通过时间筛选查询 D8/D9
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/smartwatermeterrecords/filter/list",method = RequestMethod.POST,produces = {"application/json"})
    public String findTotalWaterFilterList(@RequestBody ApiRequestVO apiRequestVO){
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        String startTime= dt.getString("startTime");
        String endTime= dt.getString("endTime");
        //通过水表id查询水表读数列表
        List<SmartWatermeterRecord> smartWatermeterRecords= watermeterService.findWatermeterRecordByWatermeterIdAndTime(watermeterId,startTime,endTime);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(smartWatermeterRecords));
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartWatermeterRecords",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 查询集中式公寓列表 C3
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/apartmentlist",method = RequestMethod.POST,produces = {"application/json"})
    public String jzList(@RequestBody ApiRequestVO apiRequestVO)  {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int id = dt.getIntValue("id");
        //通过用户id查询用户公寓列表
        List<ApartmentVO> apartmentVOS = synchronousHomeService.findApartmentIdByUserId(id);
        //通过公寓id查询公寓
        //ApartmentVO apartmentVO = synchronousHomeService.findApartmentIdByApartmentId(apartmentVOS.get(0).getId());
        //通过公寓查询楼层id
        if (!apartmentVOS.isEmpty()|| apartmentVOS!=null) {
            JSONObject responseJson = new JSONObject();
            responseJson.put("apartmentVOS",apartmentVOS);
           /*responseJson.put("watermeterNum",watermeterNum);
            responseJson.put("watermeterOnLineNum",watermeterOnLineNum);*/
            String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
            return res;
        }
        return null;

    }

//    /**
//     * 查询集中式楼层列表by公寓id C2-1
//     * @param apiRequestVO
//     * @return
//     */
//    @RequestMapping(value="/jz/floorlist/byapartmentid",method = RequestMethod.POST,produces = {"application/json"})
//    public String floorListByapartmentId(@RequestBody ApiRequestVO apiRequestVO)  {
//        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
//        int apartmentId = dt.getIntValue("apartmentId");
//        //通过用户id查询用户公寓
//        ApartmentVO apartmentVO = synchronousHomeService.findApartmentIdByApartmentId(apartmentId);
//        //通过公寓查询楼层id
//        if (apartmentVO != null) {
//            //计算水表总数和在线数
//            List<FloorVO> floorVOS = apartmentVO.getFloorVOS();
//            int watermeterNum=0;
//            int watermeterOnLineNum=0;
//            if(!floorVOS.isEmpty() || floorVOS!=null) {
//                for (FloorVO floor : floorVOS) {
//                    watermeterNum += floor.getWatermeterNum();
//                    watermeterOnLineNum += floor.getWatermeterOnLineNum();
//                }
//            }
//
//            JSONObject responseJson = new JSONObject();
//            responseJson.put("floorVO",apartmentVO.getFloorVOS());
//            responseJson.put("watermeterNum",watermeterNum);
//            responseJson.put("watermeterOnLineNum",watermeterOnLineNum);
//            responseJson.put("watermeterOffLineNum",watermeterNum - watermeterOnLineNum);
//            String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
//            return res;
//        }
//        return structureErrorResponse(null,new Date().toString(),"");
//    }

    /**
     * 根据楼层查询集中式水表列表 C2-2
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/list/byfloor",method = RequestMethod.POST,produces = {"application/json"})
    public String jzListFind(@RequestBody ApiRequestVO apiRequestVO)  {
        //获取楼层id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int floorId = dt.getIntValue("floorId");

        //通过楼层id列表查询水表信息列表
        List<JZWatermeterDetailVO> jzWatermeterDetailVOS = watermeterService.findWatermetersByFloorId(floorId);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(jzWatermeterDetailVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("jzWatermeterDetailVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 集中式水表网关列表 A1
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/watermeter_gateway/list",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterGatewayList(@RequestBody ApiRequestVO apiRequestVO){
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int apartmentId = dt.getIntValue("apartmentId");
        //网关列表
        List<JZWatermeterGatewayVO> jzWatermeterGatewayVOS = watermeterService.findGatewaysByApartmentId(apartmentId);
        //网关在线数
        int onLineNum=0;
        for (JZWatermeterGatewayVO jzWatermeterGatewayVO:jzWatermeterGatewayVOS) {
            if(jzWatermeterGatewayVO.getOnoffStatus()==1){
                onLineNum+=1;
            }
        }
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(jzWatermeterGatewayVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("jzWatermeterGatewayVOS",jsonArray);
        responseJson.put("onLineNum",onLineNum);
        responseJson.put("gatewayNum",jzWatermeterGatewayVOS.size());
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 分散式水表网关列表 D4
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/watermeter_gateway/list",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterGatewayHmList(@RequestBody ApiRequestVO apiRequestVO){
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = dt.getIntValue("id");
        //网关列表
        List<JZWatermeterGatewayVO> jzWatermeterGatewayVOS = watermeterService.findGatewaysByUserId(userId);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(jzWatermeterGatewayVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("jzWatermeterGatewayVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }


    /**
     * 集中式水表网关详情 A3
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/watermeter_gateway/detail",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterGatewayJzDetail(@RequestBody ApiRequestVO apiRequestVO){
        //通过网关id查询网关详情
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int smartGatewayId = dt.getIntValue("gatewayId");

        //查询网关详情
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterService.findGatewaybyId(smartGatewayId);
        //查询网关绑定的水表
        List<JZWatermeterDetailVO> watermeterDetailVOS = watermeterService.findJzWatermetersByGatewayId(smartGatewayId);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterDetailVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterGatewayDetailVO",watermeterGatewayDetailVO);
        responseJson.put("watermeterDetailVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }


    /**
     * 智能水表异常记录 D4
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/exception/watermeter",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterException(@RequestBody ApiRequestVO apiRequestVO){
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        List<ExceptionVO> exceptionVOS= watermeterService.findWatermeterException(watermeterId);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(exceptionVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("exceptionVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 水表网关异常记录 A4
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/exception/watermeter_gateway",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterGatewayException(@RequestBody ApiRequestVO apiRequestVO){
        //获取网关id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int gatewayId = dt.getIntValue("gatewayId");
        List<ExceptionVO> exceptionVOS= watermeterService.findWatermeterGatewayException(gatewayId);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(exceptionVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("exceptionVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;

    }

    /**
     * 根据用户Id查询有水表（或水表网关）的房源列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/home/list",method = RequestMethod.POST,produces = {"application/json"})
    public String getHomeList(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = dt.getIntValue("userId");
        String brand = dt.getString("brand") == null ? "dding" : dt.getString("brand");
        List<HomeVO> homeList = watermeterService.getHomeListByUserId(userId, brand);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(homeList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("homeList", homeList);
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    /**
     * 集中式：查询公寓下楼层水表数和总水表数
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/floor/list",method = RequestMethod.POST,produces = {"application/json"})
    public String getFloorWithWater(@RequestBody ApiRequestVO apiRequestVO)  {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int apartmentId = dt.getIntValue("apartmentId");
        List<FloorVO> floorList = watermeterService.getFloorWithWater(apartmentId);

        if (floorList != null) {
            int watermeterNum = 0;
            int watermeterOnLineNum = 0;
            for (FloorVO floor : floorList) {
                watermeterNum += floor.getWatermeterNum();
                watermeterOnLineNum += floor.getWatermeterOnLineNum();
            }
            JSONObject responseJson = new JSONObject();
            responseJson.put("floorList", floorList);
            responseJson.put("watermeterNum", watermeterNum);
            responseJson.put("watermeterOnLineNum", watermeterOnLineNum);
            responseJson.put("watermeterOffLineNum", watermeterNum - watermeterOnLineNum);
            String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
            return res;
        }
        return structureErrorResponse(null,new Date().toString(),"");
    }

    /**
     * 根据楼层查询集中式水表列表 C2-2
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/room/list",method = RequestMethod.POST,produces = {"application/json"})
    public String getRoomWithWater(@RequestBody ApiRequestVO apiRequestVO)  {
        //获取楼层id
//        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
//        int floorId = dt.getIntValue("floorId");
//
//        //通过楼层id列表查询水表信息列表
//        List<JZWatermeterDetailVO> jzWatermeterDetailVOS = watermeterService.findWatermetersByFloorId(floorId);
//
//        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(jzWatermeterDetailVOS));
//        JSONObject responseJson = new JSONObject();
//        responseJson.put("jzWatermeterDetailVOS",jsonArray);
//        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
//        return res;
        return "";
        //TODO
    }
}
