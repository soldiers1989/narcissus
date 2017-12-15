package com.ih2ome.hardware_server.server.callback;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_server.server.callback.vo.CallbackRequestVo;
import com.ih2ome.hardware_service.service.enums.AlarmTypeEnum;
import com.ih2ome.hardware_service.service.enums.HouseCatalogEnum;
import com.ih2ome.hardware_service.service.enums.SmartDeviceTypeEnum;
import com.ih2ome.hardware_service.service.model.narcissus.*;
import com.ih2ome.hardware_service.service.service.*;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;
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

    @Autowired
    private WatermeterRecordService watermeterRecordService;

    @Autowired
    private SynchronousHomeService synchronousHomeService;

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
        IWatermeter iWatermeter = getIWatermeter();
        switch (even){
            //绑定水表及水表网关设备事件
            case  "deviceInstall" :
                deviceInstallEvent(apiRequestVO,iWatermeter);
                break;
            //替换水表网关事件
            case "deviceReplace" :
                //更新网关uuid
                gatewayService.updataGatewayUuid(apiRequestVO.getUuid(),apiRequestVO.getOld_uuid(),apiRequestVO.getTime(),apiRequestVO.getManufactory());
                break;
            //解绑水表设备事件
            case "deviceUninstall" :

                //查询网关id
                int gatewayId = gatewayService.findGatewayIdByUuid(apiRequestVO.getUuid());
                //网关绑定中删除watermeterId
                gatewayBindService.deleteGatewayBindByGatewayId(gatewayId);
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
                gatewayService.updataGatewayOnoffStatus(apiRequestVO.getUuid(),AlarmTypeEnum.YUN_DING_WATERMETER_GATEWAY_EXCEPTION_TYPE_OFF_LINE.getCode());
                break;
            //水表网关在线事件
            case "waterGatewayOnlineAlarm" :
                //更改网关在线离线状态
                gatewayService.updataGatewayOnoffStatus(apiRequestVO.getUuid(),AlarmTypeEnum.YUN_DING_WATERMETER_GATEWAY_EXCEPTION_TYPE_ON_LINE.getCode());
                break;
            //抄表读数同步
            case "watermeterAmountAsync" :
                watermeterAmountAsyncEvent(apiRequestVO,iWatermeter);
                break;
        }
        return ResponseEntity.ok().body(null);
    }

    private void watermeterAmountAsyncEvent(CallbackRequestVo apiRequestVO,IWatermeter iWatermeter) {
        JSONObject detail = (JSONObject) apiRequestVO.getDetail();
        Integer amount = (Integer) detail.get("amount");
        int time = apiRequestVO.getTime();
        Date date=new Date();
        if(amount > 0) {
            //存入数据库
            watermeterService.updataWaterLastAmount(apiRequestVO.getUuid(), amount, time);
            //更新或添加抄表记录
            //获取最近一次抄表时间
            Timestamp timestamp=watermeterService.findWatermeterMeterUpdatedAt(apiRequestVO.getUuid());
            Timestamp nowTime = new Timestamp(time);

            SmartWatermeterRecord smartWatermeterRecord=new SmartWatermeterRecord();
            smartWatermeterRecord.setCreatedAt(new Date(time));
            smartWatermeterRecord.setDeviceAmount(amount);
            int watermeterid = watermeterService.findWatermeterIdByUuid(apiRequestVO.getUuid());
            smartWatermeterRecord.setSmartWatermeterId(watermeterid);
            //判断是否为同一天
            if(isTheSameDate(timestamp,nowTime)){
                //更新抄表记录
                watermeterRecordService.updataWatermeterRecord(smartWatermeterRecord);
            }else{
                //添加抄表记录
                watermeterRecordService.addWatermeterRecord(smartWatermeterRecord);
            }

            //水表在线
            watermeterService.updataWatermerterOnoffStatus(apiRequestVO.getUuid(),AlarmTypeEnum.YUN_DING_WATERMETER_EXCEPTION_TYPE_ON_LINE.getCode());
        }else{
            //水表离线,添加水表异常
            SmartMistakeInfo watermeterMistakeInfo =new SmartMistakeInfo();
            watermeterMistakeInfo.setCreatedAt(new Timestamp(apiRequestVO.getTime()));
            watermeterMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_WATERMETER.getCode());
            watermeterMistakeInfo.setExceptionType(String.valueOf(AlarmTypeEnum.YUN_DING_WATERMETER_EXCEPTION_TYPE_OFF_LINE.getCode()));
            watermeterMistakeInfo.setUuid(apiRequestVO.getUuid());
            watermeterMistakeInfo.setSn(apiRequestVO.getUuid());
            gatewayService.addSmartMistakeInfo(watermeterMistakeInfo);
            //水表离线
            watermeterService.updataWatermerterOnoffStatus(apiRequestVO.getUuid(),AlarmTypeEnum.YUN_DING_WATERMETER_EXCEPTION_TYPE_OFF_LINE.getCode());
        }
    }


    public void deviceInstallEvent(CallbackRequestVo apiRequestVO,IWatermeter iWatermeter){

        String uuid = apiRequestVO.getUuid();
        JSONObject detail = (JSONObject) apiRequestVO.getDetail();

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
                floorId = synchronousHomeService.findFloorIdByRoomId(room_id);
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
    }

    //判断两个时间戳(Timestamp)是否在同一天
    public  boolean isTheSameDate(Timestamp time1,Timestamp time2 ) {
        if(time1!=null&&time2!=null){
            Calendar c1=Calendar.getInstance();
            c1.setTime(time1);
            int y1=c1.get(Calendar.YEAR);
            int m1=c1.get(Calendar.MONTH);
            int d1=c1.get(Calendar.DATE);
            Calendar c2=Calendar.getInstance();
            c2.setTime(time2);
            int y2=c2.get(Calendar.YEAR);
            int m2=c2.get(Calendar.MONTH);
            int d2=c2.get(Calendar.DATE);
            if(y1==y2&&m1==m2&&d1==d2)
            {
                return true;
            }
        }
        else
        {
            if(time1==null&&time2==null)
            {
                return true;
            }
        }
        return false;
    }

}
