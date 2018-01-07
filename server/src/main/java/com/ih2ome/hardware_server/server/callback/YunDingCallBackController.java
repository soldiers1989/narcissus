package com.ih2ome.hardware_server.server.callback;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_server.server.callback.vo.CallbackRequestVo;
import com.ih2ome.hardware_service.service.enums.AlarmTypeEnum;
import com.ih2ome.hardware_service.service.enums.HouseCatalogEnum;
import com.ih2ome.hardware_service.service.enums.OnOffStatusEnum;
import com.ih2ome.hardware_service.service.enums.SmartDeviceTypeEnum;
import com.ih2ome.hardware_service.service.model.narcissus.*;
import com.ih2ome.hardware_service.service.service.*;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.sun.xml.internal.ws.api.model.ExceptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * 云丁回调接口
 */
@RestController
public class YunDingCallBackController extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    private static String CALLBACK_PATH="http://rose.ih2ome.cn/api"+"/callback/watermeter/yunding";

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
     * 水表回调接口
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/callback/watermeter/yunding",method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Object> watermeterAmountAsync(@RequestBody CallbackRequestVo apiRequestVO) {
        System.out.println(apiRequestVO.toString());
        Log.info("水表回调接口,apiRequestVO：{}",apiRequestVO.toString());
        //校验签名
        String sign = apiRequestVO.getSign();
        boolean flag=checkSign(sign,apiRequestVO);
        if(!flag){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("parameter error");
        }
        String event = apiRequestVO.getEvent();
        IWatermeter iWatermeter = getIWatermeter();
        switch (event){
            //绑定水表及水表网关设备事件
            case  "deviceInstall" :
                try {
                    deviceInstallEvent(apiRequestVO,iWatermeter);
                } catch (WatermeterException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
                }
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
                waterGatewayOnOfflineAlarm(apiRequestVO,OnOffStatusEnum.ON_OFF_STATUS_ENUM_OFF_Line.getCode());
                break;
            //水表网关在线事件
            case "waterGatewayOnlineAlarm" :
                waterGatewayOnOfflineAlarm(apiRequestVO,OnOffStatusEnum.ON_OFF_STATUS_ENUM_ON_Line.getCode());
                break;
            //抄表读数同步
            case "watermeterAmountAsync" :
                watermeterAmountAsyncEvent(apiRequestVO);
                break;
        }
        return ResponseEntity.ok().body("ok");
    }

    /**
     * 水表网关离线上线事件
     * @param apiRequestVO
     */
    private void waterGatewayOnOfflineAlarm(CallbackRequestVo apiRequestVO ,int onOffStatus) {
        String exceptionType = null;
        //离线
        if (onOffStatus==OnOffStatusEnum.ON_OFF_STATUS_ENUM_OFF_Line.getCode()){
            exceptionType=String.valueOf(AlarmTypeEnum.YUN_DING_WATERMETER_GATEWAY_EXCEPTION_TYPE_OFF_LINE.getCode());
        }else {
            exceptionType=String.valueOf(AlarmTypeEnum.YUN_DING_WATERMETER_GATEWAY_EXCEPTION_TYPE_ON_LINE.getCode());
        }
        //添加网关异常
        SmartMistakeInfo smartMistakeInfo =new SmartMistakeInfo();
        smartMistakeInfo.setCreatedAt(new Timestamp(apiRequestVO.getTime()));
        smartMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_WATERMETER_GATEWAY.getCode());
        smartMistakeInfo.setExceptionType(exceptionType);
        smartMistakeInfo.setUuid(apiRequestVO.getUuid());
        smartMistakeInfo.setSn(apiRequestVO.getUuid());
        gatewayService.addSmartMistakeInfo(smartMistakeInfo);
        //更改网关在线状态
        gatewayService.updataGatewayOnoffStatus(apiRequestVO.getUuid(),onOffStatus);

        //添加水表异常
        //查询水表id
        List<String> watermeterUuids = watermeterService.findWatermeterIdByGatewayUuid(apiRequestVO.getUuid());
        for (String uuid:watermeterUuids
             ) {
            SmartMistakeInfo smartMistakeInfo2 =new SmartMistakeInfo();
            smartMistakeInfo2.setCreatedAt(new Timestamp(apiRequestVO.getTime()));
            smartMistakeInfo2.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_WATERMETER.getCode());
            smartMistakeInfo2.setExceptionType(exceptionType);
            smartMistakeInfo2.setUuid(uuid);
            smartMistakeInfo2.setSn("");
            gatewayService.addSmartMistakeInfo(smartMistakeInfo);
            //添加水表离线
            watermeterService.updataWatermerterOnoffStatus(uuid,onOffStatus);
        }
    }

    /**
     * 抄表读数同步
     * @param apiRequestVO
     */
    private void watermeterAmountAsyncEvent(CallbackRequestVo apiRequestVO) {
        Object detailObj = apiRequestVO.getDetail();
        String s = JSONObject.toJSONString(detailObj);
        JSONObject detail=JSONObject.parseObject(s);
        Integer amount = (Integer) detail.get("amount");
        Long time = apiRequestVO.getTime();
        //抄表成功
        if(amount >= 0) {

            //更新或添加抄表记录
            //获取最近一次抄表时间
            Integer watermeterid = watermeterService.findWatermeterIdByUuid(apiRequestVO.getUuid());
//            Timestamp timestamp=watermeterService.findWatermeterMeterUpdatedAt(apiRequestVO.getUuid());
            Timestamp timestamp=watermeterRecordService.findWatermeterMeterUpdatedAtByWatermeterId(watermeterid);
            Timestamp nowTime = new Timestamp(time);

            SmartWatermeterRecord smartWatermeterRecord=new SmartWatermeterRecord();
            smartWatermeterRecord.setCreatedAt(nowTime);
            smartWatermeterRecord.setDeviceAmount(amount);
            smartWatermeterRecord.setSmartWatermeterId(watermeterid);
            //判断是否为同一天
            if(isTheSameDate(timestamp,nowTime)){
                //更新抄表记录
                watermeterRecordService.updataWatermeterRecord(smartWatermeterRecord);
            }else{
                //添加抄表记录
                watermeterRecordService.addWatermeterRecord(smartWatermeterRecord);
            }

            //存入数据库
            watermeterService.updataWaterLastAmount(apiRequestVO.getUuid(), amount, time);
            //查询水表在线状态
            Integer onOffStatus= watermeterService.findWatermeterOnOffStatusByUuid(apiRequestVO.getUuid());
            //水表状态离线
            if (onOffStatus.equals(OnOffStatusEnum.ON_OFF_STATUS_ENUM_OFF_Line.getCode())) {
                //水表在线
                watermeterService.updataWatermerterOnoffStatus(apiRequestVO.getUuid(), OnOffStatusEnum.ON_OFF_STATUS_ENUM_ON_Line.getCode());
                //添加异常上线记录
                SmartMistakeInfo watermeterMistakeInfo =new SmartMistakeInfo();
                watermeterMistakeInfo.setCreatedAt(new Timestamp(apiRequestVO.getTime()));
                watermeterMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_WATERMETER.getCode());
                watermeterMistakeInfo.setExceptionType(String.valueOf(AlarmTypeEnum.YUN_DING_WATERMETER_EXCEPTION_TYPE_ON_LINE.getCode()));
                watermeterMistakeInfo.setUuid(apiRequestVO.getUuid());
                //watermeterMistakeInfo.setSn(apiRequestVO.getUuid());
                gatewayService.addSmartMistakeInfo(watermeterMistakeInfo);
            }
        }else{
            //水表离线,添加水表异常
            SmartMistakeInfo watermeterMistakeInfo =new SmartMistakeInfo();
            watermeterMistakeInfo.setCreatedAt(new Timestamp(apiRequestVO.getTime()));
            watermeterMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_WATERMETER.getCode());
            watermeterMistakeInfo.setExceptionType(String.valueOf(AlarmTypeEnum.YUN_DING_WATERMETER_EXCEPTION_TYPE_OFF_LINE.getCode()));
            watermeterMistakeInfo.setUuid(apiRequestVO.getUuid());
            //watermeterMistakeInfo.setSn(apiRequestVO.getUuid());
            gatewayService.addSmartMistakeInfo(watermeterMistakeInfo);
            //水表离线
            watermeterService.updataWatermerterOnoffStatus(apiRequestVO.getUuid(),OnOffStatusEnum.ON_OFF_STATUS_ENUM_OFF_Line.getCode());
        }
    }


    /**
     * 绑定水表及水表网关设备事件
     * @param apiRequestVO
     * @param iWatermeter
     * @throws WatermeterException
     */
    public void deviceInstallEvent(CallbackRequestVo apiRequestVO,IWatermeter iWatermeter) throws WatermeterException {

        String uuid = apiRequestVO.getUuid();
        Object detailObj =  apiRequestVO.getDetail();
        String s = JSONObject.toJSONString(detailObj);
        JSONObject detail=JSONObject.parseObject(s);

        String home_id =apiRequestVO.getHome_id();
        String str = home_id.substring(0,2);
        Integer house_catalog=null;
        Long houseId=null;
        Long apartmentId=null;
        Integer floorId=null;
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
                house_catalog= HouseCatalogEnum.HOUSE_CATALOG_ENUM_VOLGA.getCode();
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

        watermeterInfo = iWatermeter.getWatermeterInfo(uuid, apiRequestVO.getManufactory());

        resJson = JSONObject.parseObject(watermeterInfo);

        String info =  resJson.getString("info");
        JSONObject jsonObject = JSONObject.parseObject(info);
        int meter_type = jsonObject.getIntValue("meter_type");
        int onoff = jsonObject.getIntValue("onoff");
        Long time=detail.getLong("time");
        Date meter_updated_at=new Date(System.currentTimeMillis());


        //绑定水表及水表网关设备事件

        SmartWatermeter smartWatermeter = new SmartWatermeter();

        smartWatermeter.setCreatedAt(new Date(System.currentTimeMillis()));
        smartWatermeter.setCreatedBy(created_by);
        smartWatermeter.setUpdatedAt(created_at);
        smartWatermeter.setUpdatedBy(created_by);
        smartWatermeter.setDeletedAt(created_at);
        smartWatermeter.setDeletedBy(0L);
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
        SmartGatewayBind smartGatewayBind =new SmartGatewayBind();
        //判断网关是否已存在
        int gatewayId = gatewayService.findGatewayIdByUuid(apiRequestVO.getGateway_uuid());
        if (gatewayId <= 0){
            //添加网关
            SmartGateway smartGateway = new SmartGateway();
            smartGateway.setCreatedAt(new Date(System.currentTimeMillis()));
            smartGateway.setCreatedBy(created_by);
            smartGateway.setUpdatedAt(created_at);
            smartGateway.setUpdatedBy(0L);
            smartGateway.setDeletedAt(created_at);
            smartGateway.setDeletedBy(0L);
            try {
                String waterGatewayInfo = iWatermeter.getWaterGatewayInfo(apiRequestVO.getGateway_uuid());
            } catch (WatermeterException e) {
                Log.error("获取水表网关信息失败",e);
            }
            resJson = JSONObject.parseObject(watermeterInfo);

            String gatewayInfo = resJson.getString("info");
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
            smartGateway.setOperator("云丁");
            //smartGateway.setInstallStatus(1);
            smartGateway.setOnoffStatus(gatewayOnoff);
            //smartGateway.setRemark(null);
            smartGateway.setHouseCatalog(Long.valueOf(house_catalog));
            smartGateway.setApartmentId(Long.valueOf(gatewayHome_id.substring(2)));
            smartGateway.setFloor(floorId);
            smartGateway.setHouseId(houseId);
            smartGateway.setRoomId(Long.valueOf(gatewayRoom_id.substring(2)));

            //添加网关
            gatewayService.addSmartGateway(smartGateway);
            gatewayId = gatewayService.findGatewayIdByUuid(apiRequestVO.getGateway_uuid());
        }

        //绑定网关
        smartGatewayBind.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_WATERMETER.getCode());
        smartGatewayBind.setSmartGatewayId(Long.valueOf(gatewayId));
        //查询水表id
        int watermeterId=watermeterService.findWatermeterIdByUuid(apiRequestVO.getUuid());
        smartGatewayBind.setSmartId((long) watermeterId);
        watermeterService.addSmartGatewayBind(smartGatewayBind);
    }

    /**
     * 判断两个时间戳(Timestamp)是否在同一天
     * @param time1
     * @param time2
     * @return
     */
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

    /**
     * 校验签名
     * @param sign
     * @param apiRequestVO
     * @return
     */
    private boolean checkSign(String sign, CallbackRequestVo apiRequestVO) {
        Map<String,Object> map=new HashMap<>();
        map.put("event",apiRequestVO.getEvent());
        map.put("time",apiRequestVO.getTime());
        map.put("uuid",apiRequestVO.getUuid());
        map.put("old_uuid",apiRequestVO.getOld_uuid());
        map.put("manufactory",apiRequestVO.getManufactory());
        map.put("home_id",apiRequestVO.getHome_id());
        map.put("gateway_uuid",apiRequestVO.getGateway_uuid());
        map.put("room_id",apiRequestVO.getRoom_id());
        map.put("detail",JSONObject.toJSONString(apiRequestVO.getDetail()));

        String sign1 = getSign(map);
        return sign.equals(sign1);

    }

    /**
     * map字典排序
     * @param map
     * @return
     */
    public static String getSign(Map map) {

        Collection<String> keyset= map.keySet();

        List list=new ArrayList<String>(keyset);

        Collections.sort(list);
        //这种打印出的字符串顺序和微信官网提供的字典序顺序是一致的
        String str = "";
        for(int i=0;i<list.size();i++){
            if (map.get(list.get(i)) != null && map.get(list.get(i)) != "") {
                str += list.get(i) + "=" + map.get(list.get(i))+"&";
            }
        }
        String stringA=str.substring(0,str.length()-1);
        String stringSignTemp=CALLBACK_PATH + stringA;
        String sign= DigestUtils.md5DigestAsHex(stringSignTemp.getBytes());
        return sign;
    }

    public static void main(String[] arge){
        Map<String,Object> map=new HashMap<>();
        map.put("event","watermeterAmountAsync");
        Long time = 1515136584045L;
        map.put("time",time);
        map.put("uuid","00000171476818");
        map.put("old_uuid",null);
        map.put("manufactory","ym");
        map.put("home_id","jz3670");
        map.put("gateway_uuid","000000485915");
        map.put("room_id","jz585921");
        map.put("detail","{\"amount\":0}");

        System.out.println(time);
        String sign= getSign(map);
        System.out.println(sign);
    }


}
