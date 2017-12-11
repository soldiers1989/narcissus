package com.ih2ome.hardware_server.server.callback;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_server.server.callback.vo.CallbackRequestVo;
import com.ih2ome.hardware_service.service.model.narcissus.SmartGateway;
import com.ih2ome.hardware_service.service.model.narcissus.SmartGatewayBind;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.watermeter.service.GatewayService;
import com.ih2ome.watermeter.service.WatermeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            case "deviceInstall" :

                Long created_by=3L;
                Date created_at =new Date(System.currentTimeMillis());
                Long updated_by=created_by;
                Long deleted_by=created_by;
                Date updated_at=created_at;
                Date deleted_at=created_at;
                String home_id =apiRequestVO.getHome_id();
                String str = home_id.substring(0,2);
                Long house_catalog=null;
                Long houseId=null;
                Long apartmentId=null;
                Long floorId=null;
                Long room_id= Long.parseLong(apiRequestVO.getRoom_id().substring(2));
                switch (str){
                    case "hm":
                        //1:分散式 caspain
                        house_catalog=1L;
                        houseId= Long.valueOf(home_id.substring(2));
                        break;
                    case "jz":
                        //2:集中式 volga
                        house_catalog=2L;
                        apartmentId =Long.valueOf(home_id.substring(2));
                        floorId = watermeterService.findFloorIdByRoomId(room_id);
                        break;
                }


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
                Double meter_type = Double.valueOf(jsonObject.getIntValue("meter_type"));
                Double onoff = Double.valueOf(jsonObject.getIntValue("onoff"));
                Date meter_updated_at=detail.getTimestamp("time");
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
                        iWatermeter.getWaterGatewayInfo(apiRequestVO.getGateway_uuid());
                    } catch (WatermeterException e) {
                        Log.error("获取水表网关信息失败",e);
                    }

                    smartGateway.setMac(null);
                    smartGateway.setSn(null);
                    smartGateway.setUuid(apiRequestVO.getGateway_uuid());
                    smartGateway.setModel(null);
                    smartGateway.setModelName(null);
                    smartGateway.setName(null);
                    smartGateway.setInstallTime(null);
                    smartGateway.setInstallName(null);
                    smartGateway.setInstallMobile(null);
                    smartGateway.setBrand(null);
                    smartGateway.setOperator(null);
                    smartGateway.setInstallStatus(null);
                    smartGateway.setOnoffStatus(null);
                    smartGateway.setRemark(null);
                    smartGateway.setHouseCatalog(null);
                    smartGateway.setApartmentId(null);
                    smartGateway.setFloor(null);
                    smartGateway.setHouseId(null);
                    smartGateway.setRoomId(null);

                }

                //绑定网关
                SmartGatewayBind smartGatewayBind =new SmartGatewayBind();
                smartGatewayBind.setSmartDeviceType(2D);
                smartGatewayBind.setSmartGatewayId(Long.valueOf(gatewayId));
                smartGatewayBind.setSmartId(smartWatermeter.getSmartWatermeterId());
                watermeterService.addSmartGatewayBind(smartGatewayBind);

                break;
            case "deviceReplace" :


                return null;
            case "deviceUninstall" :
                return null;
            case "waterGatewayOfflineAlarm" :
                return null;
            case "waterGatewayOnlineAlarm" :
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

}
