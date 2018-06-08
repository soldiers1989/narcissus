package com.ih2ome.hardware_server.server.controller.watermeter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.enums.ExpireTime;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import com.ih2ome.hardware_service.service.peony.watermeterInterface.IWatermeter;
import com.ih2ome.hardware_service.service.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.entity.narcissus.SmartDeviceV2;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeter;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeterRecord;
import com.ih2ome.sunflower.vo.pageVo.watermeter.*;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.enums.WATERMETER_FIRM;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
     */
    @RequestMapping(value = "/hm/list", method = RequestMethod.POST, produces = {"application/json"})
    public String distributedList(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int id = dt.getIntValue("id");

        List<HMWatermeterListVO> watermeterList = watermeterService.findWatermetersByid(id);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterList", jsonArray);
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    /**
     * 分散水表网关详情 D3
     */
    @RequestMapping(value = "/hm/watermeter_gateway/detail", method = RequestMethod.POST, produces = {"application/json"})
    public String watermeterGatewayDetail(@RequestBody ApiRequestVO apiRequestVO) {
        //通过网关id查询网关详情
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int smartGatewayId = dt.getIntValue("gatewayId");

        //查询网关详情
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterService.findhmGatewaybyId(smartGatewayId);
        //查询网关绑定的水表
        List<HMWatermeterListVO> watermeterDetailVOS = watermeterService.findWatermetersByGatewayId(smartGatewayId);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterDetailVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterGatewayDetailVO", watermeterGatewayDetailVO);
        responseJson.put("watermeterDetailVOS", jsonArray);
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    /**
     * 水表读数明细每月每天累计水量列表
     */
    @RequestMapping(value = "/smartwatermeterrecords/list", method = RequestMethod.POST, produces = {"application/json"})
    public String findTotalWaterList(@RequestBody ApiRequestVO apiRequestVO) {
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        int page = dt.getIntValue("page");
        int count = dt.getIntValue("count");
        //通过水表id查询水表读数列表
        PageResult<SmartWatermeterRecord> pageResult = watermeterService.findWatermeterRecordByWatermeterId(watermeterId, page, count);
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartWatermeterRecords", pageResult);
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    /**
     * 水表读数抄表请求 D6-1/D7-1
     */
    @RequestMapping(value = "/readwatermeteramount", method = RequestMethod.POST, produces = {"application/json"})
    public String readWatermeterAmount(@RequestBody ApiRequestVO apiRequestVO) {
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        String userId = dt.getString("userId");
        try {

            String code = watermeterService.readWatermeterLastAmountByWatermeterId(watermeterId, userId);
            //请求成功
            JSONObject responseJson = new JSONObject();

            responseJson.put("result", code);
            if(code.equals("success")) {
                return structureSuccessResponseVO(responseJson, new Date().toString(), "");
            }
            else {
                return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), code);
            }
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(), e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "请求失败" + e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(), e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "请求失败" + e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(), e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "请求失败" + e.getMessage());
            return res;
        } catch (WatermeterException e) {
            Log.error(e.getMessage(), e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "请求失败" + e.getMessage());
            return res;
        }
    }

    /**
     * 水表读数明细当月累计水量 D6-1/D7-1
     */
    @RequestMapping(value = "/findwateramount", method = RequestMethod.POST, produces = {"application/json"})
    public String findTotalWater(@RequestBody ApiRequestVO apiRequestVO) {
        //access_token,uuid水表id,manufactory水表供应商.请求地址/openapi/v1/read_watermeter
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        int amount = watermeterService.findWatermeterLastAmountByWatermeterId(watermeterId);

        JSONObject responseJson = new JSONObject();
        responseJson.put("amount", amount);
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    /**
     * 水表读数明细通过时间筛选查询 D8/D9
     */
    @RequestMapping(value = "/smartwatermeterrecords/filter/list", method = RequestMethod.POST, produces = {"application/json"})
    public String findTotalWaterFilterList(@RequestBody ApiRequestVO apiRequestVO) {
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        String startTime = dt.getString("startTime");
        String endTime = dt.getString("endTime");
        //通过水表id查询水表读数列表
        List<SmartWatermeterRecord> smartWatermeterRecords = watermeterService.findWatermeterRecordByWatermeterIdAndTime(watermeterId, startTime, endTime);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(smartWatermeterRecords));
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartWatermeterRecords", jsonArray);
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    /**
     * 集中式水表网关列表 A1
     */
    @RequestMapping(value = "/jz/watermeter_gateway/list", method = RequestMethod.POST, produces = {"application/json"})
    public String watermeterGatewayList(@RequestBody ApiRequestVO apiRequestVO) {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int apartmentId = dt.getIntValue("apartmentId");
        //网关列表
        List<JZWatermeterGatewayVO> jzWatermeterGatewayVOS = watermeterService.findGatewaysByApartmentId(apartmentId);
        //网关在线数
        int onLineNum = 0;
        for (JZWatermeterGatewayVO jzWatermeterGatewayVO : jzWatermeterGatewayVOS) {
            if (jzWatermeterGatewayVO.getOnoffStatus() == 1) {
                onLineNum += 1;
            }
        }
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(jzWatermeterGatewayVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("jzWatermeterGatewayVOS", jsonArray);
        responseJson.put("onLineNum", onLineNum);
        responseJson.put("gatewayNum", jzWatermeterGatewayVOS.size());
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    /**
     * 分散式水表网关列表 D4
     */
    @RequestMapping(value = "/hm/watermeter_gateway/list", method = RequestMethod.POST, produces = {"application/json"})
    public String watermeterGatewayHmList(@RequestBody ApiRequestVO apiRequestVO) {
        //获取公寓id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = dt.getIntValue("id");
        //网关列表
        List<JZWatermeterGatewayVO> jzWatermeterGatewayVOS = watermeterService.findGatewaysByUserId(userId);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(jzWatermeterGatewayVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("jzWatermeterGatewayVOS", jsonArray);
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    /**
     * 集中式水表网关详情 A3
     */
    @RequestMapping(value = "/jz/watermeter_gateway/detail", method = RequestMethod.POST, produces = {"application/json"})
    public String watermeterGatewayJzDetail(@RequestBody ApiRequestVO apiRequestVO) {
        //通过网关id查询网关详情
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int smartGatewayId = dt.getIntValue("gatewayId");

        //查询网关详情
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterService.findGatewaybyId(smartGatewayId);
        //查询网关绑定的水表
        List<JZWaterMeterVO> watermeterDetailVOS = watermeterService.findJzWatermetersByGatewayId(smartGatewayId);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterDetailVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterGatewayDetailVO", watermeterGatewayDetailVO);
        responseJson.put("watermeterDetailVOS", jsonArray);
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    /**
     * 智能水表异常记录 D4
     */
    @RequestMapping(value = "/exception/watermeter", method = RequestMethod.POST, produces = {"application/json"})
    public String watermeterException(@RequestBody ApiRequestVO apiRequestVO) {
        //获取水表id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        List<ExceptionVO> exceptionVOS = watermeterService.findWatermeterException(watermeterId);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(exceptionVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("exceptionVOS", jsonArray);
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    /**
     * 水表网关异常记录 A4
     */
    @RequestMapping(value = "/exception/watermeter_gateway", method = RequestMethod.POST, produces = {"application/json"})
    public String watermeterGatewayException(@RequestBody ApiRequestVO apiRequestVO) {
        //获取网关id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int gatewayId = dt.getIntValue("gatewayId");
        List<ExceptionVO> exceptionVOS = watermeterService.findWatermeterGatewayException(gatewayId);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(exceptionVOS));
        JSONObject responseJson = new JSONObject();
        responseJson.put("exceptionVOS", jsonArray);
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;

    }

    /**
     * 根据用户Id查询有水表（或水表网关）的集中式房源列表
     */
    @RequestMapping(value = "/jz/number", method = RequestMethod.POST, produces = {"application/json"})
    public String getWaterNumber(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = dt.getIntValue("userId");
        JSONObject responseJson = new JSONObject();
        responseJson.put("waterMeterNumber", watermeterService.getDeviceNumber(userId, 2));
        responseJson.put("gateWayNumber", watermeterService.getDeviceNumber(userId, 5));
        return structureSuccessResponseVO(responseJson, new Date().toString(), "");
    }

    /**
     * 根据用户Id查询有水表（或水表网关）的集中式房源列表
     */
    @RequestMapping(value = "/apartment/list", method = RequestMethod.POST, produces = {"application/json"})
    public String getApartmentList(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = dt.getIntValue("userId");
        String brand = dt.getString("brand") == null ? "dding" : dt.getString("brand");

        // 获取用户整个主+子账号下所有有水表的公寓
        List<HomeVO> homeList = watermeterService.getApartmentListByUserId(userId, brand);

        // 如果用户是子账号则剔除无权限查看的公寓
        if(watermeterService.isEmployer(userId)) {
            List<Integer> apartmentIdList = watermeterService.queryEmployerApartment(userId);
            for (HomeVO home : homeList) {
                if (!apartmentIdList.contains(home.getId())) {
                    homeList.remove(home);
                }
            }
        }

        JSONObject responseJson = new JSONObject();
        responseJson.put("homeList", homeList);
        Thread t = new Thread(() -> {
            List<SmartDeviceV2> smartDeviceList = watermeterService.getSmartDeviceV2List(userId, brand);
            try {
                Calendar beforeTime = Calendar.getInstance();
                beforeTime.add(Calendar.HOUR, -3);
                Date beforeDate = beforeTime.getTime();
                IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
                for (SmartDeviceV2 device : smartDeviceList) {
                    SmartWatermeter watermeter = watermeterService.getWatermeterByDeviceId(Integer.parseInt(device.getSmartDeviceId()));
                    if (watermeter.getMeterUpdatedAt() == null || watermeter.getMeterUpdatedAt().before(beforeDate)) {
                        iWatermeter.readWatermeter(device.getThreeId(), device.getProviderCode(), device.getCreatedBy());
                    }
                }
            } catch (Exception ex) {
                Log.error("auto read amount error!", ex);
            }
        });
        t.start();

        return structureSuccessResponseVO(responseJson, new Date().toString(), "");
    }

    /**
     * 集中式：查询公寓下楼层水表数和总水表数
     */
    @RequestMapping(value = "/jz/floor/list", method = RequestMethod.POST, produces = {"application/json"})
    public String getFloorWithWater(@RequestBody ApiRequestVO apiRequestVO) {
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
        return structureErrorResponse(null, new Date().toString(), "");
    }

    /**
     * 集中式：根据楼层Id查询楼层下房间水表列表
     */
    @RequestMapping(value = "/jz/room/list", method = RequestMethod.POST, produces = {"application/json"})
    public String getRoomWithWater(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int floorId = dt.getIntValue("floorId");

        List<RoomSimpleVO> roomSimpleList = watermeterService.getRoomWithWater(floorId);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(roomSimpleList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("roomSimpleList", jsonArray);
        return structureSuccessResponseVO(responseJson, new Date().toString(), "");
    }

    /**
     * 集中式：根据房间Id查询房间详细信息
     */
    @RequestMapping(value = "/jz/room/detail", method = RequestMethod.POST, produces = {"application/json"})
    public String getRoomDetail(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int roomId = dt.getIntValue("roomId");

        List<WaterDetailVO> waterDetailList = watermeterService.getWaterInRoom(roomId);
        RoomDetailVO roomDetail = watermeterService.getRoomDetail(roomId);

        for (WaterDetailVO waterDetail : waterDetailList) {
            if (waterDetail.getMeterType() == 1) {
                roomDetail.setColdPrice(waterDetail.getPrice());
            } else if (waterDetail.getMeterType() == 2) {
                roomDetail.setHotPrice(waterDetail.getPrice());
            }
        }

        JSONObject responseJson = new JSONObject();
        responseJson.put("waterDetailList", waterDetailList);
        responseJson.put("roomDetail", roomDetail);

        return structureSuccessResponseVO(responseJson, new Date().toString(), "");
    }

    /**
     * 按房间修改冷热水费单价
     */
    @RequestMapping(value = "/price/update/room", method = RequestMethod.POST, produces = {"application/json"})
    public String updateRoomPrice(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        Float price = dt.getFloatValue("price");
        int roomId = dt.getIntValue("roomId");
        int meterType = dt.getIntValue("meterType");
        Boolean flag = watermeterService.updateRoomPrice((int) (price * 100), roomId, meterType);

        JSONObject responseJson = new JSONObject();
        if (flag) {
            responseJson.put("result", "success");
        }
        return structureSuccessResponseVO(responseJson, new Date().toString(), "");
    }

    /**
     * 按水表查询时间范围内读数明细
     */
    @RequestMapping(value = "/record/list", method = RequestMethod.POST, produces = {"application/json"})
    public String getWaterRecordList(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int watermeterId = dt.getIntValue("watermeterId");
        String startTime = dt.getString("startTime");
        String endTime = dt.getString("endTime");
        List<RecordVO> recordList = watermeterService.getRecordList(watermeterId, startTime, endTime + " 23:59:59.000");
        JSONObject responseJson = new JSONObject();
        responseJson.put("recordList", recordList);
        return structureSuccessResponseVO(responseJson, new Date().toString(), "");
    }

    /**
     * 按水表查询时间范围内读数描点
     */
    @RequestMapping(value = "/record/chart", method = RequestMethod.POST, produces = {"application/json"})
    public String getWaterRecordChart(@RequestBody ApiRequestVO apiRequestVO) {
        try {
            JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
            int chartNum = dt.getIntValue("chartNum") == 0 ? 7 : dt.getIntValue("chartNum");
            int watermeterId = dt.getIntValue("watermeterId");

            Date startTime = dt.getDate("startTime");
            Date endTime = dt.getDate("endTime");
            List<RecordVO> recordList = watermeterService.getRecordList(watermeterId, dt.getString("startTime"), dt.getString("endTime") + " 23:59:59.999");
            List<ChartVO> chartList = new ArrayList<>();
            watermeterService.makeChartList(chartNum, startTime, endTime, recordList, chartList);
            JSONObject responseJson = new JSONObject();
            responseJson.put("recordChart", chartList);
            return structureSuccessResponseVO(responseJson, new Date().toString(), "");
        } catch (Exception ex) {
            Log.error("getWaterRecordChart error");
            return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "请求失败" + ex.getMessage());
        }
    }

    /**
     * 按房间查询时间范围内读数明细
     */
    @RequestMapping(value = "/room/record/list", method = RequestMethod.POST, produces = {"application/json"})
    public String getRoomWaterRecordList(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int roomId = dt.getIntValue("roomId");
        int houseCatalog = dt.getIntValue("houseCatalog");
        int meterType = dt.getIntValue("meterType");
        String startTime = dt.getString("startTime");
        String endTime = dt.getString("endTime");

        List<RecordVO> recordList = watermeterService.getRoomRecordList(roomId, houseCatalog, meterType, startTime, endTime + " 23:59:59.000");
        JSONObject responseJson = new JSONObject();
        responseJson.put("recordList", recordList);
        return structureSuccessResponseVO(responseJson, new Date().toString(), "");
    }

    /**
     * 按房间查询时间范围内读数描点
     */
    @RequestMapping(value = "/room/record/chart", method = RequestMethod.POST, produces = {"application/json"})
    public String getRoomWaterRecordChart(@RequestBody ApiRequestVO apiRequestVO) {
        try {
            JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
            int chartNum = dt.getIntValue("chartNum") == 0 ? 7 : dt.getIntValue("chartNum");
            int roomId = dt.getIntValue("roomId");
            int houseCatalog = dt.getIntValue("houseCatalog");
            int meterType = dt.getIntValue("meterType");
            Date startTime = dt.getDate("startTime");
            Date endTime = dt.getDate("endTime");

            List<RecordVO> recordList = watermeterService.getRoomRecordList(roomId,houseCatalog,meterType, dt.getString("startTime"), dt.getString("endTime") + " 23:59:59.999");
            List<ChartVO> chartList = new ArrayList<>();
            watermeterService.makeChartList(chartNum, startTime, endTime, recordList, chartList);
            JSONObject responseJson = new JSONObject();
            responseJson.put("recordChart", chartList);
            return structureSuccessResponseVO(responseJson, new Date().toString(), "");
        } catch (Exception ex) {
            Log.error("getWaterRecordChart error");
            return structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "请求失败" + ex.getMessage());
        }
    }

    /**
     * 健康检查
     */
    @RequestMapping(value = "/hs", method = RequestMethod.GET)
    public String testMethod() {

        return "OK";

//        CacheUtils.set("test_key_1","2", ExpireTime.FIVE_MIN);
//        CacheUtils.set("test_key_1","3", ExpireTime.FIVE_MIN);
//        CacheUtils.set("test_key_1","4", ExpireTime.FIVE_MIN);
//        return CacheUtils.getStr("test_key_1");

//        Set<String> tokens = CacheUtils.keys("refresh_token_key*");
//        StringBuilder sb = new StringBuilder();
//        for (String token:tokens) {
//            Log.info(token);
//            sb.append(token).append("\r\n");
//        }
//        return sb.toString();
    }
}
