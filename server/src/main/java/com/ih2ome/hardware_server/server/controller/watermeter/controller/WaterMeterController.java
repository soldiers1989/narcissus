package com.ih2ome.hardware_server.server.controller.watermeter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.watermeter.model.Watermeter;
import com.ih2ome.watermeter.service.IWatermeterService;
import com.ih2ome.watermeter.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 水表Controller
 */
@RestController
@RequestMapping("/watermeter")
public class WaterMeterController extends BaseController {
    @Autowired(required = false)
    private IWatermeterService watermeterService;

    /**
     * 查询分散式水表列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/distributed/list",method = RequestMethod.POST,produces = {"application/json"})
    public String distributedList(@RequestBody ApiRequestVO apiRequestVO)  {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        String id = dt.getString("id");
        //通过用户id查询用户房源id
        List<Integer> roomIds = watermeterService.findRoomIdByUserId(id);
        //通过房源id查询水表信息
        List<WatermeterDetailVO> watermeterList = watermeterService.findWatermetersByids(roomIds);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterList",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;


        //假数据
        //水表序列号，房间号，楼层，房源名称，产品类型，水表型号，绑定时间，当月累计水表量，电费单价，通讯状态，绑定网关，
       /* WatermeterDetailVO watermeterDetailVO =new WatermeterDetailVO();
        watermeterDetailVO.setSmartWatermeterId("23432432432");
        watermeterDetailVO.setRoomName("001");
//        watermeterDetailVO.setFloorName("1");
//        watermeterDetailVO.setFloorNum(12);
//        watermeterDetailVO.setApartmentName("海纳公寓");
        watermeterDetailVO.setMeterType(1);
        watermeterDetailVO.setCreatedAt("2017-04-05T15:11");
        watermeterDetailVO.setLastAmount(200.7f);
        watermeterDetailVO.setPrice(200);
        watermeterDetailVO.setOnoffStatus(1);
        watermeterDetailVO.setSmartGatewayId("125EE454546");

        ArrayList<WatermeterDetailVO> list = new ArrayList<WatermeterDetailVO>();
        list.add(watermeterDetailVO);
        list.add(watermeterDetailVO);

        WatermeterListVo watermeterListVo=new WatermeterListVo();
        watermeterListVo.setList(list);
        //String data,String reqTime,String eds,String salt
        //System.out.println(JsonUtils.toString(watermeterListVo));
        return structureSuccessResponseVO(JSONObject.(watermeterListVo),"20171111","0","");*/

    }

    /**
     * 查询单个水表详情
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/detail",method = RequestMethod.POST,produces = {"application/json"})
    public String detail(@RequestBody ApiRequestVO apiRequestVO)  {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        String id = dt.getString("id");

        //通过水表id查询水表信息
        Watermeter watermeter = watermeterService.findWatermeterByid(id);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeter));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeter",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;

        //假数据
        /*WatermeterDetailVO watermeterDetailVO =new WatermeterDetailVO();
        //水表序列号，房间号，楼层，房源名称，产品类型-水表型号，绑定时间，当月累计水表量，电费单价，通讯状态，绑定网关，
        watermeterDetailVO.setSmartWatermeterId("23432432432");
        watermeterDetailVO.setRoomName("001");
//        watermeterDetailVO.setFloorName("1");
//        watermeterDetailVO.setFloorNum(0);
//        watermeterDetailVO.setApartmentName("海纳公寓");
        watermeterDetailVO.setMeterType(1);
        watermeterDetailVO.setCreatedAt("2017-04-05T15:11");
        watermeterDetailVO.setLastAmount(200.7f);
        watermeterDetailVO.setPrice(200);
        watermeterDetailVO.setOnoffStatus(1);
        watermeterDetailVO.setSmartGatewayId("125EE454546");
        return structureSuccessResponseVO(JsonUtils.toString(watermeterDetailVO),"20171111","0","");*/
    }

    /**
     * 查询集中式水表列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/list",method = RequestMethod.POST,produces = {"application/json"})
    public String jzList(@RequestBody ApiRequestVO apiRequestVO)  {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        String id = dt.getString("id");
        //通过用户id查询用户公寓列表
        List<ApartmentVO> apartmentVOS = watermeterService.findApartmentIdByUserId(id);
        //通过公寓查询楼层id
        int floorId=apartmentVOS.get(0).getFloorId();
        //通过楼层id列表查询水表信息列表
        List<JZWatermeterDetailVO> jzWatermeterDetailVOS = watermeterService.findWatermetersByFloorId(floorId);
        JZWatermeterListVo JZWatermeterListVo = new JZWatermeterListVo();
        JZWatermeterListVo.setApartmentVOS(apartmentVOS);
        JZWatermeterListVo.setJzWatermeterDetailVOS(jzWatermeterDetailVOS);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(JZWatermeterListVo));
        JSONObject responseJson = new JSONObject();
        responseJson.put("JZWatermeterListVo",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;

       /* //假数据
        JZWatermeterDetailVO watermeterDetailVO =new JZWatermeterDetailVO();
        //水表序列号，房间号，楼层，房源名称，产品类型-水表型号，绑定时间，当月累计水表量，电费单价，通讯状态，绑定网关，
        watermeterDetailVO.setSmartWatermeterId(23432432432);
        watermeterDetailVO.setRoomName("001");
        watermeterDetailVO.setFloorName("1");
        watermeterDetailVO.setFloorNum(12);
        watermeterDetailVO.setApartmentName("海纳公寓");
        watermeterDetailVO.setMeterType(1);
        watermeterDetailVO.setCreatedAt("2017-04-05T15:11");
        watermeterDetailVO.setLastAmount(200.7f);
        watermeterDetailVO.setPrice(200);
        watermeterDetailVO.setOnoffStatus(1);
        watermeterDetailVO.setSmartGatewayId("125EE454546");

        ArrayList<JZWatermeterDetailVO> list = new ArrayList<JZWatermeterDetailVO>();
        list.add(watermeterDetailVO);
        list.add(watermeterDetailVO);

        String json = JSON.toJSONString(list);

        WatermeterListVo watermeterListVo=new WatermeterListVo();
        //watermeterListVo.setList(list);
*/
        //return JsonUtils.toString(watermeterListVo);

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
     * 智能水表异常记录
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/exception",method = RequestMethod.POST,produces = {"application/json"})
    public String exception(@RequestBody ApiRequestVO apiRequestVO){


        return "";
    }

    /**
     * 水费单价修改
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/detail/updata_water_price",method = RequestMethod.GET,produces = {"application/json"})
    public String updataWaterPrice(@RequestBody ApiRequestVO apiRequestVO ){
        //获取更改价格
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int price = dt.getIntValue("price");
        int watermeterId = dt.getIntValue("watermeterId");
        Boolean flag = watermeterService.updataWaterPrice(price,watermeterId);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString("ture"));
        JSONObject responseJson = new JSONObject();
        responseJson.put("price",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;
    }


    /**
     * 水表读数明细当月实时累计水量
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/find_total_water",method = RequestMethod.POST,produces = {"application/json"})
    public String findTotalWater(@RequestBody ApiRequestVO apiRequestVO){
        //access_token,uuid水表id,manufactory水表供应商.请求地址/openapi/v1/read_watermeter

        //同步到数据库

        return "200.9";
    }

    /**
     * 水表读数明细每月每天累计水量列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/find_total_water/list",method = RequestMethod.POST,produces = {"application/json"})
    public String findTotalWaterList(@RequestBody ApiRequestVO apiRequestVO){
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        //通过水表id查询水表读数列表
        List<WaterMeterRecordVO> waterMeterRecordVOS= watermeterService.findWatermeterRecordByWatermeterId(watermeterId);
        return "";
    }

    /**
     * 水表读数明细通过时间筛选查询
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/find_total_water/filter/list",method = RequestMethod.POST,produces = {"application/json"})
    public String findTotalWaterFilterList(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    /**
     * 同步房源整栋同步
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/synchronous_housing/build",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingByBuild(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    /**
     * 同步房源一层一层同步
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/synchronous_housing/floor",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingByFloor(@RequestBody ApiRequestVO apiRequestVO){
        return "";
    }

    /**
     * 水表网关列表
     * @param id
     * @return
     */
    @RequestMapping(value="/watermeter_gateway/list",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterGatewayList(@RequestParam(value = "id") String id){

        //假数据
        WatermeterGatewayVO watermeterGatewayVO = new WatermeterGatewayVO();
        watermeterGatewayVO.setSmartGatewayId("200121212");
        watermeterGatewayVO.setBindNum(10);
        watermeterGatewayVO.setOnoffNum(5);
        watermeterGatewayVO.setOnoffStatus(1);
        //return JsonUtils.toString(watermeterGatewayVO);
        return null;
    }

    /**
     * 水表网关详情
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/watermeter_gateway/detail",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterGatewayDetail(@RequestBody ApiRequestVO apiRequestVO){
        //通过网关id查询网关详情
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        String smartGatewayId = dt.getString("smartGatewayId");

        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterService.findGatewaybyId(smartGatewayId);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterGatewayDetailVO));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterGatewayDetailVO",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;

        //假数据
       /* WatermeterGatewayDetailVO watermeterGatewayDetailVO = new WatermeterGatewayDetailVO();
        //网关编码，房源名称，更新时间，绑定时间，绑定型号，所绑定的水表列表
        watermeterGatewayDetailVO.setSmartGatewayId("284eb850970d");
        watermeterGatewayDetailVO.setApartmentName("海纳公寓");
        watermeterGatewayDetailVO.setUpdatedAt("2017-04-26T06:03:01.179Z");
        watermeterGatewayDetailVO.setCreatedAt("2017-04-25T06:43:48.697Z");
        watermeterGatewayDetailVO.setBrand("JDYDM120");

        WatermeterDetailVO watermeterDetailVO =new WatermeterDetailVO();
        //水表序列号，房间号，楼层，房源名称，产品类型-水表型号，绑定时间，当月累计水表量，电费单价，通讯状态，绑定网关，
        watermeterDetailVO.setSmartWatermeterId("23432432432");
        watermeterDetailVO.setRoomName("001");
//        watermeterDetailVO.setFloorName("1");
//        watermeterDetailVO.setFloorNum(12);
//        watermeterDetailVO.setApartmentName("海纳公寓");
        watermeterDetailVO.setMeterType(1);
        watermeterDetailVO.setCreatedAt("2017-04-05T15:11");
        watermeterDetailVO.setLastAmount(200.7f);
        watermeterDetailVO.setPrice(200);
        watermeterDetailVO.setOnoffStatus(1);
        watermeterDetailVO.setSmartGatewayId("125EE454546");

        ArrayList<WatermeterDetailVO> list = new ArrayList<WatermeterDetailVO>();
        list.add(watermeterDetailVO);
        list.add(watermeterDetailVO);

        watermeterGatewayDetailVO.setWatermeterList(list);*/


        //return ResponseEntity.status(HttpStatus.OK).body(watermeterGatewayDetailVO);


    }

    /**
     * 水表网关绑定水表列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/watermeter_gateway/watermeterlist",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterGatewayWatermeter(@RequestBody ApiRequestVO apiRequestVO){
        //通过网关id查询水表列表
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        String smartGatewayId = dt.getString("smartGatewayId");

        List<WatermeterDetailVO> watermeterDetailVOS = watermeterService.findWatermetersByGatewayId(smartGatewayId);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterDetailVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterDetailVOS",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"哈哈哈");
        return res;

    }

    /**
     * 水表网关异常记录
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/watermeter_gateway/exception",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterGatewayException(@RequestBody ApiRequestVO apiRequestVO){


        //return "{['time':'2017-08-26T16:39:05','onoffStatus':'离线']}";
        //return structureSuccessResponseVO("{['time':'2017-08-26T16:39:05','onoffStatus':'离线']}","20171111","0","");
        return null;
    }





}
