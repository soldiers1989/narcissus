package com.ih2ome.hardware_server.server.controller.watermeter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeterRecord;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import com.ih2ome.hardware_service.service.vo.*;
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
 * 水表Controller
 */
@RestController
@RequestMapping("/watermeter")
public class WaterMeterController extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WatermeterService watermeterService;

    /**
     * 查询分散式水表列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/list",method = RequestMethod.POST,produces = {"application/json"})
    public String distributedList(@RequestBody ApiRequestVO apiRequestVO)  {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int id = dt.getIntValue("id");

        List<WatermeterDetailVO> watermeterList = watermeterService.findWatermetersByid(id);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterList",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 水表网关详情
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/watermeter_gateway/detail",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterGatewayDetail(@RequestBody ApiRequestVO apiRequestVO){
        //通过网关id查询网关详情
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int smartGatewayId = dt.getIntValue("smartGatewayId");

        //查询网关详情
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterService.findGatewaybyId(smartGatewayId);
        //查询网关绑定的水表
        List<WatermeterDetailVO> watermeterDetailVOS = watermeterService.findWatermetersByGatewayId(smartGatewayId);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterDetailVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterGatewayDetailVO",watermeterGatewayDetailVO);
        responseJson.put("watermeterDetailVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 水费单价修改
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/detail/updata_water_price",method = RequestMethod.POST,produces = {"application/json"})
    public String updataWaterPrice(@RequestBody ApiRequestVO apiRequestVO ){
        //获取更改价格
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int price = dt.getIntValue("price");
        int watermeterId = dt.getIntValue("watermeterId");
        Boolean flag;
        try {
            flag = watermeterService.updataWaterPrice(price,watermeterId);
        } catch (AmmeterException e) {
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
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        }

       JSONObject responseJson = new JSONObject();
        if (flag == true) {
            responseJson.put("result", "success");
        }
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "哈哈哈");
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
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 水表读数明细当月实时累计水量
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/find_total_water",method = RequestMethod.POST,produces = {"application/json"})
    public String findTotalWater(@RequestBody ApiRequestVO apiRequestVO) throws ClassNotFoundException, WatermeterException, InstantiationException, IllegalAccessException {
        //access_token,uuid水表id,manufactory水表供应商.请求地址/openapi/v1/read_watermeter
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        int LastAmount =watermeterService.findWatermeterLastAmountByWatermeterId(watermeterId);
        //同步到数据库


        JSONObject responseJson = new JSONObject();
        responseJson.put("LastAmount",LastAmount);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 水表读数明细通过时间筛选查询
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
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 查询集中式水表列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/list",method = RequestMethod.POST,produces = {"application/json"})
    public String jzList(@RequestBody ApiRequestVO apiRequestVO)  {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int id = dt.getIntValue("id");
        //通过用户id查询用户公寓列表
        List<ApartmentVO> apartmentVOS = watermeterService.findApartmentIdByUserId(id);
        //通过公寓查询楼层id
        if (!apartmentVOS.isEmpty()) {
            int floorId = apartmentVOS.get(0).getFloorVOS().get(0).getFloorId();

        //通过楼层id列表查询水表信息列表
        List<JZWatermeterDetailVO> jzWatermeterDetailVOS = watermeterService.findWatermetersByFloorId(floorId);
        JZWatermeterListVo jzWatermeterListVo = new JZWatermeterListVo();
        jzWatermeterListVo.setApartmentVOS(apartmentVOS);
        jzWatermeterListVo.setJzWatermeterDetailVOS(jzWatermeterDetailVOS);

        JSONObject responseJson = new JSONObject();
        responseJson.put("JZWatermeterListVo",jzWatermeterListVo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
        }
        return null;

    }

    /**
     * 根据楼层查询集中式水表列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/list/find",method = RequestMethod.POST,produces = {"application/json"})
    public String jzListFind(@RequestBody ApiRequestVO apiRequestVO)  {
        //获取楼层id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int floorId = dt.getIntValue("floorId");

        //通过楼层id列表查询水表信息列表
        List<JZWatermeterDetailVO> jzWatermeterDetailVOS = watermeterService.findWatermetersByFloorId(floorId);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(jzWatermeterDetailVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("jzWatermeterDetailVOS",jsonArray);
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
        int id = dt.getIntValue("id");
        List<HouseVO> houseVOS = watermeterService.findHouseByUserId(id);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(houseVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("houseVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 查询房源是否已同步
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/synchronous_housing/FindHomeIsSynchronoused",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindHomeIsSynchronoused(@RequestBody ApiRequestVO apiRequestVO)  {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int homeId = dt.getIntValue("houseId");
        YunDingResponseVo yunDingResponseVo = null;
        try {
            yunDingResponseVo = watermeterService.findHomeIsSynchronousedByHomeId(homeId);
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
     * 分散式用户房源同步
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/hm/synchronous_housing/byhouse",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingByHouse(@RequestBody ApiRequestVO apiRequestVO) {
        //获取用户id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int houseId = dt.getIntValue("houseId");
        String home_id = null;
        try {
            home_id = watermeterService.synchronousHousingByHouseId(houseId);
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

        JSONObject responseJson = new JSONObject();
        responseJson.put("home_id",home_id);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
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
            yunDingResponseVos = watermeterService.findHomeIsSynchronousedByHomeIds(homeIds);
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
            home_id = watermeterService.synchronousHousingByApartmenId(apartmentId);
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
            home_id = watermeterService.synchronousHousingByFloorId(apartmentId,floorId);
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
     * 水表网关列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/watermeter_gateway/list",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterGatewayList(@RequestBody ApiRequestVO apiRequestVO){
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int apartmentId = dt.getIntValue("apartmentId");
        //网关列表
        List<JZWatermeterGatewayVO> jzWatermeterGatewayVOS = watermeterService.findGatewaysByApartmentId(apartmentId);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(jzWatermeterGatewayVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("jzWatermeterGatewayVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 智能水表异常记录
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
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }

    /**
     * 水表网关异常记录
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
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;

        //return "{['time':'2017-08-26T16:39:05','onoffStatus':'离线']}";
        //return structureSuccessResponseVO("{['time':'2017-08-26T16:39:05','onoffStatus':'离线']}","20171111","0","");
    }


}
