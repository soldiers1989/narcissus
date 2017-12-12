package com.ih2ome.hardware_server.server.callback;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_server.server.callback.vo.CallbackRequestVo;
import com.ih2ome.hardware_service.service.enums.AlarmTypeEnum;
import com.ih2ome.hardware_service.service.enums.SmartDeviceTypeEnum;
import com.ih2ome.hardware_service.service.model.narcissus.SmartGateway;
import com.ih2ome.hardware_service.service.model.narcissus.SmartGatewayBind;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.watermeter.enums.HouseCatalogEnum;
import com.ih2ome.watermeter.service.GatewayBindService;
import com.ih2ome.watermeter.service.GatewayService;
import com.ih2ome.watermeter.service.WatermeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 云丁回调接口
 */
@RestController
@RequestMapping("/yunding")
public class YunDingCallBackController extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WatermeterService watermeterService;

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private GatewayBindService gatewayBindService;

    private IWatermeter getIWatermeter(){
        IWatermeter iWatermeter = null;
        try {
            iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        } catch (InstantiationException e) {
            Log.error("获取水表类失败",e);
        } catch (IllegalAccessException e) {
            Log.error("获取水表类失败",e);
        } catch (ClassNotFoundException e) {
            Log.error("获取水表类失败",e);
        }
        return iWatermeter;
    }

    /**
     * 水表抄表回调接口
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/watermeter/callback",method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Object> watermeterAmountAsync(@RequestBody CallbackRequestVo apiRequestVO) {
        String even = apiRequestVO.getEven();
        String uuid = apiRequestVO.getUuid();
        JSONObject detail = (JSONObject) apiRequestVO.getDetail();
        IWatermeter iWatermeter = getIWatermeter();
        switch (even){
            //绑定水表及水表网关设备事件
            case "deviceInstall" :

                String home_id =apiRequestVO.getHome_id();
                String str = home_id.substring(0,2);
                Integer house_catalog=null;
                Long houseId=null;
                Long apartmentId=null;
                Long floorId=null;
                Long room_id= Long.parseLong(apiRequestVO.getRoom_id().substring(2));
                Long created_by=null;
                switch (str){
                    case "hm":
                        //1:分散式 caspain
                        house_catalog= HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode();
                        houseId= Long.valueOf(home_id.substring(2));
                        //查询created_by
                        created_by= Long.valueOf(watermeterService.findHouseCreatedByByHouseId(houseId));
                        break;
                    case "jz":
                        //2:集中式 volga
                        house_catalog=HouseCatalogEnum.HOUSE_CATALOG_ENUM_VOLGA.getCode();
                        apartmentId =Long.valueOf(home_id.substring(2));
                        floorId = watermeterService.findFloorIdByRoomId(room_id);
                        //查询created_by
                        created_by= Long.valueOf(watermeterService.findApartmentCreatedByByApartmentId(apartmentId));
                        break;
                }
                Date created_at =new Date(System.currentTimeMillis());
                Long updated_by=created_by;
                Long deleted_by=created_by;
                Date updated_at=created_at;
                Date deleted_at=created_at;

                JSONObject resJson = null;

                String watermeterInfo = null;
                try {
                    watermeterInfo = iWatermeter.getWatermeterInfo(uuid, apiRequestVO.getManufactory());
                } catch (WatermeterException e) {
                    Log.error("获取水表信息失败",e);
                }

                resJson = JSONObject.parseObject(watermeterInfo);

                String info = (String) resJson.get("info");
                JSONObject jsonObject = JSONObject.parseObject(info);
                int meter_type = jsonObject.getIntValue("meter_type");
                int onoff = jsonObject.getIntValue("onoff");
                Date meter_updated_at=detail.getDate("time");


                //绑定水表及水表网关设备事件

                SmartWatermeter smartWatermeter = new SmartWatermeter();

                smartWatermeter.setCreatedAt(new Date(System.currentTimeMillis()));
                smartWatermeter.setCreatedBy(created_by);
                smartWatermeter.setUpdatedAt(created_at);
                smartWatermeter.setUpdatedBy(created_by);
                smartWatermeter.setDeletedAt(created_at);
                smartWatermeter.setDeletedBy(created_by);
                smartWatermeter.setApartmentId(apartmentId);
                smartWatermeter.setFloorId(floorId);
                smartWatermeter.setHouseId(houseId);
                smartWatermeter.setRoomId(room_id);
                smartWatermeter.setHouseCatalog(house_catalog);
                smartWatermeter.setMeterType(meter_type);
                smartWatermeter.setUuid(uuid);
                smartWatermeter.setOnoffStatus(onoff);
                smartWatermeter.setLastAmount(0L);
                smartWatermeter.setMeterAmount(0L);
                smartWatermeter.setMeterUpdatedAt(meter_updated_at);
                smartWatermeter.setManufactory(apiRequestVO.getManufactory());

                //创建水表
                watermeterService.createSmartWatermeter(smartWatermeter);
                //判断网关是否已存在
                int gatewayId = gatewayService.findGatewayIdByUuid(apiRequestVO.getGateway_uuid());
                if (gatewayId <= 0){
                    //添加网关
                    SmartGateway smartGateway = new SmartGateway();
                    smartGateway.setCreatedAt(new Date(System.currentTimeMillis()));
                    smartGateway.setCreatedBy(created_by);
                    smartGateway.setUpdatedAt(created_at);
                    smartGateway.setUpdatedBy(created_by);
                    smartGateway.setDeletedAt(created_at);
                    smartGateway.setDeletedBy(created_by);
                    try {
                        String waterGatewayInfo = iWatermeter.getWaterGatewayInfo(apiRequestVO.getGateway_uuid());
                    } catch (WatermeterException e) {
                        Log.error("获取水表网关信息失败",e);
                    }
                    resJson = JSONObject.parseObject(watermeterInfo);

                    String gatewayInfo = (String) resJson.get("info");
                    JSONObject gatewayJsonObject = JSONObject.parseObject(gatewayInfo);
                    String manufactory = gatewayJsonObject.getString("manufactory");
                    int removed = gatewayJsonObject.getIntValue("removed");
                    Date mtime = gatewayJsonObject.getDate("mtime");
                    Date ctime = gatewayJsonObject.getDate("ctime");
                    Date bind_time = gatewayJsonObject.getDate("bind_time");
                    int gatewayOnoff = gatewayJsonObject.getIntValue("onoff");
                    String gatewayHome_id = gatewayJsonObject.getString("home_id");
                    String gatewayRoom_id = gatewayJsonObject.getString("room_id");

                    //smartGateway.setMac(null);
                    smartGateway.setSn(apiRequestVO.getGateway_uuid());
                    smartGateway.setUuid(apiRequestVO.getGateway_uuid());
                    //smartGateway.setModel(null);
                    smartGateway.setModelName("watermeterGateway");
                    smartGateway.setName("watermeter");
                    smartGateway.setInstallTime(ctime);
                    smartGateway.setInstallName("");
                    smartGateway.setInstallMobile("");
                    smartGateway.setBrand(manufactory);
                    smartGateway.setOperator("yungding");
                    //smartGateway.setInstallStatus(1);
                    smartGateway.setOnoffStatus(gatewayOnoff);
                    //smartGateway.setRemark(null);
                    smartGateway.setHouseCatalog(Long.valueOf(house_catalog));
                    smartGateway.setApartmentId(Long.valueOf(gatewayHome_id));
                    smartGateway.setFloor(floorId);
                    smartGateway.setHouseId(houseId);
                    smartGateway.setRoomId(Long.valueOf(gatewayRoom_id));

                    //添加网关
                    gatewayService.addSmartGateway(smartGateway);
                }

                //绑定网关
                SmartGatewayBind smartGatewayBind =new SmartGatewayBind();
                smartGatewayBind.setSmartDeviceType(2D);
                smartGatewayBind.setSmartGatewayId(Long.valueOf(gatewayId));
                smartGatewayBind.setSmartId((long) smartWatermeter.getSmartWatermeterId());
                watermeterService.addSmartGatewayBind(smartGatewayBind);

                break;
                //替换水表网关事件
            case "deviceReplace" :
                //更新网关uuid
                gatewayService.updataGatewayUuid(uuid,apiRequestVO.getOld_uuid(),apiRequestVO.getTime(),apiRequestVO.getManufactory());
                break;
            //解绑水表设备事件
            case "deviceUninstall" :
            /*{"event":"deviceUninstall","uuid":"284eb850970d","manufactory":"cy","home_id":"58ae9c4df82a2b7050
                43d2af","room_id":"58ae9c4df82a2b705043d2af","detail":{"type":7},"time":1493177264671}}*/
                //查询水表idbyuuid
                int watermeterId= watermeterService.findWatermeterIdByUuid(uuid);
                //网关绑定中删除watermeterId
                gatewayBindService.deleteGatewayBindByWatermeterId(watermeterId);
                break;
             //水表网关离线事件
            case "waterGatewayOfflineAlarm" :
                //添加网关异常
                SmartMistakeInfo smartMistakeInfo =new SmartMistakeInfo();

                smartMistakeInfo.setCreatedAt(new Timestamp(apiRequestVO.getTime()));
                smartMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_WATERMETER_GATEWAY.getCode());
                smartMistakeInfo.setExceptionType(String.valueOf(AlarmTypeEnum.YUN_DING_WATERMETER_GATEWAY_EXCEPTION_TYPE_OFF_LINE.getCode()));
                smartMistakeInfo.setUuid(apiRequestVO.getUuid());
                smartMistakeInfo.setSn(apiRequestVO.getUuid());
                gatewayService.addSmartMistakeInfo(smartMistakeInfo);
                //更改网关在线状态
                //gatewayService.updataGatewayOnoffStatus(apiRequestVO.getUuid(),AlarmTypeEnum.YUN_DING_WATERMETER_GATEWAY_EXCEPTION_TYPE_OFF_LINE.getCode());
                break;
            //水表网关在线事件
            case "waterGatewayOnlineAlarm" :
               /* 水表网关在线事件
            {"event":"waterGatewayOnlineAlarm","uuid":"284eb850970d","manufactory":"cy","home_id":"58ae9c4df
                82a2b705043d2af","room_id":"58ae9c4df82a2b705043d2af","detail":{},"time":1493177264671}}*/



                return null;
            case "watermeterAmountAsync" :
                int amount = detail.getIntValue("amount");
                int time = apiRequestVO.getTime();
                Date date=new Date();
                //存入数据库
                watermeterService.updataWaterLastAmount(uuid,amount,time);
                break;
        }
        return ResponseEntity.ok().body(null);
    }

    private void addGateway() {
        SmartWatermeter smartWatermeter = new SmartWatermeter();
    }


}
