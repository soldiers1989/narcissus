package com.ih2ome.hardware_server.server.callback.help;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.service.*;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.entity.narcissus.*;
import com.ih2ome.sunflower.vo.pageVo.enums.AlarmTypeEnum;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseCatalogEnum;
import com.ih2ome.sunflower.vo.pageVo.enums.OnOffStatusEnum;
import com.ih2ome.sunflower.vo.pageVo.enums.SmartDeviceTypeEnum;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.enums.WATERMETER_FIRM;
import com.ih2ome.sunflower.vo.thirdVo.yunDingCallBack.CallbackRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

    @Resource
    private SmartLockDao smartLockDao;
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
        if (onOffStatus== OnOffStatusEnum.ON_OFF_STATUS_ENUM_OFF_Line.getCode()){
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
        for (String uuid:watermeterUuids) {
            SmartMistakeInfo smartMistakeInfo2 =new SmartMistakeInfo();
            smartMistakeInfo2.setCreatedAt(new Timestamp(apiRequestVO.getTime()));
            smartMistakeInfo2.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_WATERMETER.getCode());
            smartMistakeInfo2.setExceptionType(exceptionType);
            smartMistakeInfo2.setUuid(uuid);
            smartMistakeInfo2.setSn("");
            gatewayService.addSmartMistakeInfo(smartMistakeInfo2);
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
        JSONObject detail = JSONObject.parseObject(s);
        Integer amount = (Integer) detail.get("amount");
        Long time = apiRequestVO.getTime();
        //抄表成功
        if (amount >= 0) {
            SmartWatermeter watermeter = watermeterService.getWatermeterByUuId(apiRequestVO.getUuid());
            SmartDeviceV2 device = watermeterService.getSmartDeviceV2(watermeter.getSmartWatermeterId());

            SmartWatermeterRecord smartWatermeterRecord = new SmartWatermeterRecord();
            smartWatermeterRecord.setDeviceAmount(amount);
            smartWatermeterRecord.setSmartWatermeterId(watermeter.getSmartWatermeterId());
            //写入抄表记录
            watermeterRecordService.addWatermeterRecord(smartWatermeterRecord);
            //计算金额
            if (amount > watermeter.getLastAmount()) {
                watermeterService.changeBalance(Integer.parseInt(device.getRoomId()), Integer.parseInt(device.getHouseCatalog()), (amount - watermeter.getLastAmount()) * watermeter.getPrice(), "", "system_async", "");
            }
            if (amount < watermeter.getLastAmount()) {
                Log.warn("第三方返回电表读数小于最近读数，请关注！ deviceId:{};返回读数:{};最近读数:{}", watermeter.getSmartWatermeterId(), amount, watermeter.getLastAmount());
            }
            //更新最近读数
            watermeterService.updataWaterLastAmount(apiRequestVO.getUuid(), amount, time);
            if(watermeter.getMeterAmount() == null){
                watermeterService.updataWatermeterMeterAmount(watermeter.getSmartWatermeterId(),amount);
            }
            //水表状态离线
            if (watermeter.getOnoffStatus() == OnOffStatusEnum.ON_OFF_STATUS_ENUM_OFF_Line.getCode()) {
                //水表上线
                watermeterService.updataWatermerterOnoffStatus(apiRequestVO.getUuid(), OnOffStatusEnum.ON_OFF_STATUS_ENUM_ON_Line.getCode());
                //添加异常上线记录
                SmartMistakeInfo watermeterMistakeInfo = new SmartMistakeInfo();
                watermeterMistakeInfo.setCreatedAt(new Timestamp(apiRequestVO.getTime()));
                watermeterMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_WATERMETER.getCode());
                watermeterMistakeInfo.setExceptionType(String.valueOf(AlarmTypeEnum.YUN_DING_WATERMETER_EXCEPTION_TYPE_ON_LINE.getCode()));
                watermeterMistakeInfo.setUuid(apiRequestVO.getUuid());
                gatewayService.addSmartMistakeInfo(watermeterMistakeInfo);
            }
        } else {
            //水表离线
            watermeterService.updataWatermerterOnoffStatus(apiRequestVO.getUuid(), OnOffStatusEnum.ON_OFF_STATUS_ENUM_OFF_Line.getCode());
            //水表离线,添加水表异常
            SmartMistakeInfo watermeterMistakeInfo = new SmartMistakeInfo();
            watermeterMistakeInfo.setCreatedAt(new Timestamp(apiRequestVO.getTime()));
            watermeterMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.YUN_DING_WATERMETER.getCode());
            watermeterMistakeInfo.setExceptionType(String.valueOf(AlarmTypeEnum.YUN_DING_WATERMETER_EXCEPTION_TYPE_OFF_LINE.getCode()));
            watermeterMistakeInfo.setUuid(apiRequestVO.getUuid());
            gatewayService.addSmartMistakeInfo(watermeterMistakeInfo);
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
        String  factoryType=apiRequestVO.getManufactory();
        String gateway_uuid=apiRequestVO.getGateway_uuid();
        String room_id=apiRequestVO.getRoom_id();
        String meter_type=apiRequestVO.getMeter_type();
        //获取事件类型
        String type = String.valueOf(detail.get("type"));
        String time=String.valueOf(detail.get("time"));
        //网关安装
//        if(type.equals("7")){
//            //添加网关
//            SmartGateway smartGateway = new SmartGateway();
//            smartGateway.setCreatedAt(new Date(System.currentTimeMillis()));
//            smartGateway.setCreatedBy(created_by);
//            smartGateway.setUpdatedAt(created_at);
//            smartGateway.setUpdatedBy(0L);
//            smartGateway.setDeletedAt(created_at);
//            smartGateway.setDeletedBy(0L);
//            //查询网关信息
//            String waterGatewayInfo = iWatermeter.getWaterGatewayInfo(apiRequestVO.getUuid());
//
//            JSONObject resJson = JSONObject.parseObject(waterGatewayInfo);
//
//            String gatewayInfo = resJson.getString("info");
//            JSONObject gatewayJsonObject = JSONObject.parseObject(gatewayInfo);
//            String manufactory = gatewayJsonObject.getString("manufactory");
//            int removed = gatewayJsonObject.getIntValue("removed");
//            Date mtime = gatewayJsonObject.getDate("mtime");
//            Date ctime = gatewayJsonObject.getDate("ctime");
//            Date bind_time = gatewayJsonObject.getDate("bind_time");
//            int gatewayOnoff = gatewayJsonObject.getIntValue("onoff");
//            String gatewayHome_id = gatewayJsonObject.getString("home_id");
//            String gatewayRoom_id = gatewayJsonObject.getString("room_id");
////            String description=gatewayJsonObject.getString("description");
//            //smartGateway.setMac(null);
//            smartGateway.setSn(apiRequestVO.getUuid());
//            smartGateway.setUuid(apiRequestVO.getUuid());
//            //smartGateway.setModel(null);
//            smartGateway.setModelName("gateway");
//            smartGateway.setName("watermeterGateway");
//            smartGateway.setInstallTime(ctime);
//            smartGateway.setInstallName("");
//            smartGateway.setInstallMobile("");
//            smartGateway.setBrand(manufactory);
//            smartGateway.setOperator("云丁");
//            //smartGateway.setInstallStatus(1);
//            smartGateway.setOnoffStatus(gatewayOnoff);
//            //smartGateway.setRemark(null);
//            smartGateway.setHouseCatalog(Long.valueOf(house_catalog));
//            smartGateway.setApartmentId(Long.valueOf(gatewayHome_id.substring(2)));
//            smartGateway.setFloor(floorId);
//            smartGateway.setHouseId(houseId);
//            smartGateway.setRoomId(Long.valueOf(gatewayRoom_id.substring(2)));
//
//            //查询网关id
//            Integer gatewayId = gatewayService.findGatewayIdByUuid(apiRequestVO.getGateway_uuid());
//            if(gatewayId == null){
//                //添加网关
//                gatewayService.addSmartGateway(smartGateway);
//            }else {
//                //更新网关
//                gatewayService.updataSmartGateway(smartGateway);
//            }
//
//        }else
            if(type.equals("8")){
            //获取水表amount
            String amount = String.valueOf(detail.get("amount"));


            String name=null;
            if("1".equals(meter_type)){
                name="冷水表";
            }else if("2".equals(meter_type)){
                name="热水表";
            }
            SmartDeviceV2 smartDeviceV2=new SmartDeviceV2();
            smartDeviceV2.setBrand("dding");
            smartDeviceV2.setConnectionStatus("1");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            smartDeviceV2.setConnectionStatusUpdateTime(df.format(new Date()));
            String h2omeID=watermeterService.findGateWay(home_id);
            if(h2omeID!=null){
                String userId=smartLockDao.findUserIds(h2omeID);
                smartDeviceV2.setCreatedBy(userId);
                smartDeviceV2.setHouseCatalog(type);
                smartDeviceV2.setName(name);
                smartDeviceV2.setProviderCode(factoryType);
                smartDeviceV2.setRoomId(h2omeID);
                smartDeviceV2.setSmartDeviceType("2");
                smartDeviceV2.setThreeId(uuid);
                String publicZoneId= smartLockDao.findConcentratePublicZoneByRoomId(h2omeID);
                //新增水表关联记录
                smartLockDao.addSmartDevice(smartDeviceV2);
                SmartWatermeter smartWatermeter=new SmartWatermeter();
                smartWatermeter.setSmartWatermeterId(Long.parseLong(smartDeviceV2.getSmartDeviceId()));
                smartWatermeter.setCreatedAt(new Date());
                smartWatermeter.setCreatedBy(Long.parseLong(userId));
                smartWatermeter.setUpdatedAt(new Date());
                smartWatermeter.setUpdatedBy(Long.parseLong(userId));
                smartWatermeter.setRoomId(Long.parseLong(h2omeID));
                smartWatermeter.setHouseCatalog(Long.parseLong(type));
                smartWatermeter.setMeter(meter_type);
                smartWatermeter.setUuid(uuid);
                smartWatermeter.setOnoffStatus(Long.parseLong("1"));
                smartWatermeter.setManufactory(factoryType);
                smartLockDao.saveWaterMeter(smartWatermeter);
                String smartGatWayid=smartLockDao.querySmartGatWayid(publicZoneId);
                smartLockDao.addSmartDeviceBind(smartDeviceV2.getSmartDeviceId(), smartGatWayid);
                SmartHouseMappingVO houseMapping = new SmartHouseMappingVO();
                houseMapping.setDataType("4");
                houseMapping.setProviderCode(factoryType);
                houseMapping.setH2omeId(h2omeID);
                houseMapping.setHouseCatalog("0");
                //查询该关联关系原先是否存在
                SmartHouseMappingVO houseMappingRecord = smartLockDao.findHouseMappingRecord(houseMapping);
                //该记录存在，修改该映射记录
                if (houseMappingRecord != null) {
                    smartLockDao.updateAssociation(houseMapping);
                } else {
                    smartLockDao.addAssociation(houseMapping);
                }
            }

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

    /**
     * 设备解绑事件
     * @param apiRequestVO
     */
    public void deviceUninstall(CallbackRequestVo apiRequestVO) {
        Object detailObj =  apiRequestVO.getDetail();
        String s = JSONObject.toJSONString(detailObj);
        JSONObject detail=JSONObject.parseObject(s);
        String type = String.valueOf(detail.get("type"));
        //水表网关解绑事件
        if(type.equals("7")) {
            //查询网关id
            int gatewayId = gatewayService.findGatewayIdByUuid(apiRequestVO.getUuid());
            //网关绑定中删除watermeterId
            gatewayBindService.deleteGatewayBindByGatewayId(gatewayId);
        }else if(type.equals("8")){
            //查询水表id
            SmartWatermeter watermeter = watermeterService.getWatermeterByUuId(apiRequestVO.getUuid());
            //解绑水表
            gatewayBindService.deleteGatewayBindByWatermeterId(watermeter.getSmartWatermeterId());
        }

    }
}
