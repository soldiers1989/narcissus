package com.ih2ome.hardware_server.server.callback.help;

import com.alibaba.fastjson.JSONObject;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.*;

@Component
public class YunDingCallBackHelp {
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
     * 水表网关离线上线事件
     * @param apiRequestVO
     */
    public void waterGatewayOnOfflineAlarm(CallbackRequestVo apiRequestVO, int onOffStatus) {
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
    public void watermeterAmountAsyncEvent(CallbackRequestVo apiRequestVO) {
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
     * @throws WatermeterException
     */
    public void deviceInstallEvent(CallbackRequestVo apiRequestVO) throws WatermeterException {
        IWatermeter iWatermeter = getIWatermeter();
        //获取参数
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

        //获取事件类型
        String type = String.valueOf(detail.get("type"));

        //网关安装
        if(type.equals("7")){
            int gatewayId = gatewayService.findGatewayIdByUuid(apiRequestVO.getGateway_uuid());
            //判断网关是否已存在
            if (gatewayId <= 0){

            }
            //添加网关
            SmartGateway smartGateway = new SmartGateway();
            smartGateway.setCreatedAt(new Date(System.currentTimeMillis()));
            smartGateway.setCreatedBy(created_by);
            smartGateway.setUpdatedAt(created_at);
            smartGateway.setUpdatedBy(0L);
            smartGateway.setDeletedAt(created_at);
            smartGateway.setDeletedBy(0L);
            //查询网关信息
            String waterGatewayInfo = iWatermeter.getWaterGatewayInfo(apiRequestVO.getGateway_uuid());

            JSONObject resJson = JSONObject.parseObject(waterGatewayInfo);

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

        }else if(type.equals("8")){
            //创建水表
            SmartWatermeter smartWatermeter = new SmartWatermeter();
            //获取水表amount
            String amount = String.valueOf(detail.get("amount"));
            //查询水表信息
            String watermeterInfo = iWatermeter.getWatermeterInfo(uuid, apiRequestVO.getManufactory());

            JSONObject resJson = JSONObject.parseObject(watermeterInfo);

            String info =  resJson.getString("info");
            JSONObject jsonObject = JSONObject.parseObject(info);
            int meter_type = jsonObject.getIntValue("meter_type");
            int onoff = jsonObject.getIntValue("onoff");
            Long time=detail.getLong("time");
            Date meter_updated_at=new Date(System.currentTimeMillis());

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
            smartWatermeter.setLastAmount(Long.valueOf(amount));
            smartWatermeter.setMeterAmount(Long.valueOf(amount));
            smartWatermeter.setMeterUpdatedAt(meter_updated_at);
            smartWatermeter.setManufactory(apiRequestVO.getManufactory());

            //创建水表
            watermeterService.createSmartWatermeter(smartWatermeter);

            //绑定水表及水表网关设备事件
            SmartGatewayBind smartGatewayBind =new SmartGatewayBind();
            smartGatewayBind.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_WATERMETER.getCode());
            //查询网关id
            int gatewayId = gatewayService.findGatewayIdByUuid(apiRequestVO.getGateway_uuid());
            smartGatewayBind.setSmartGatewayId(Long.valueOf(gatewayId));
            //查询水表id
            int watermeterId=watermeterService.findWatermeterIdByUuid(apiRequestVO.getUuid());
            smartGatewayBind.setSmartId((long) watermeterId);
            watermeterService.addSmartGatewayBind(smartGatewayBind);
        }
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
    public boolean checkSign(String sign, CallbackRequestVo apiRequestVO) {
        Map<String,Object> map=new HashMap<>();
        map.put("event",apiRequestVO.getEvent());
        map.put("time",apiRequestVO.getTime());
        map.put("uuid",apiRequestVO.getUuid());
        map.put("old_uuid",apiRequestVO.getOld_uuid());
        map.put("manufactory",apiRequestVO.getManufactory());
        map.put("home_id",apiRequestVO.getHome_id());
        map.put("gateway_uuid",apiRequestVO.getGateway_uuid());
        map.put("room_id",apiRequestVO.getRoom_id());
        map.put("detail", JSONObject.toJSONString(apiRequestVO.getDetail()));

        String sign1 = getSign(map);
        return sign.equals(sign1);

    }

    /**
     * map字典排序
     * @param map
     * @return
     */
    public String getSign(Map map) {

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
        //String sign= this.getSign(map);
       // System.out.println(sign);
    }
}
