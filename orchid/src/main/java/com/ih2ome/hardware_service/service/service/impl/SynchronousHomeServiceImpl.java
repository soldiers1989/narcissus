package com.ih2ome.hardware_service.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.dao.SynchronousHomeMapper;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.factory.SmartLockOperateFactory;
import com.ih2ome.sunflower.entity.narcissus.*;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.model.backup.RoomVO;
import com.ih2ome.sunflower.vo.pageVo.enums.*;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGateWayHadBindInnerLockVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayAndHouseInfoVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.ApartmentVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.HomeSyncVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.HouseVO;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.SmartLockFirmEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.YunDingHomeTypeEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingDeviceInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingHomeInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingRoomInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.AddHomeVo;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.AddRoomVO;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.YunDingResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SynchronousHomeServiceImpl implements SynchronousHomeService{

    @Resource
    private SynchronousHomeMapper synchronousHomeMapper;

    private static final Logger Log = LoggerFactory.getLogger(SynchronousHomeServiceImpl.class);
    @Resource
    private SmartLockDao smartLockDao;

    /**
     * 查询floorIdbyRoomid
     * @param room_id
     * @return
     */
    @Override
    public Integer findFloorIdByRoomId(Long room_id) {
        Log.info("查询楼层id,房间roomId：{}",room_id);
        return synchronousHomeMapper.findFloorIdByRoomId(room_id);
    }

    /**
     * 查询公寓信息
     * @param id
     * @return
     */

    @Override
    public List<ApartmentVO> findApartmentIdByUserId(int id) {
        Log.info("查询公寓信息,用户id：{}",id);
        //查询公寓信息
        List<ApartmentVO> apartmentVOS=synchronousHomeMapper.findApartmentByUserId(id);
        return apartmentVOS;
    }

    /**
     * 分散式用户房源
     * @param id
     * @return
     */
    @Override
    public List<HouseVO> findHouseByUserId(int id) {
        Log.info("分散式用户房源,用户id：{}",id);
        return synchronousHomeMapper.findHouseByUserId(id);
    }

    /**
     * 集中式公寓查询by公寓id
     * @param apartmentId
     * @return
     */
    @Override
    public ApartmentVO findApartmentIdByApartmentId(int apartmentId) {
        Log.info("集中式公寓信息,公寓apartmentId：{}",apartmentId);
        return synchronousHomeMapper.selectApartmentIdByApartmentId(apartmentId);
    }

    /**
     * 查询集中式roomIdsbyFloorIds
     * @param floorList
     * @return
     */
    @Override
    public List<Integer> findRoomIdsByfloorIds(List<Integer> floorList) {
        return synchronousHomeMapper.selectRoomIdsByfloorIds(floorList);
    }

    @Override
    public Map<String, List<HomeVO>> serchWater(String userId, String type, String factoryType)throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException {
        Log.info("==========查询房源信息userId:{}",userId);
        //查询第三方房源信息
        SmartLockFirmEnum smartLockFirmEnum = SmartLockFirmEnum.getByCode(factoryType);
        if (smartLockFirmEnum == null) {
            throw new SmartLockException("该厂商不存在");
        }
        //第三方房源信息(包括了分散式和集中式)
        List<HomeVO> thirdHomeList = new ArrayList<HomeVO>();
        if (smartLockFirmEnum != null && smartLockFirmEnum.getCode().equals(SmartLockFirmEnum.YUN_DING.getCode())) {
            ISmartLock iSmartLock = (ISmartLock) Class.forName(smartLockFirmEnum.getClazz()).newInstance();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userId", userId);
            String result = iSmartLock.searchHomeInfo(params);
            List<YunDingHomeInfoVO> yunDingHomes = JSONObject.parseArray(result, YunDingHomeInfoVO.class);
            //处理云丁的房源数据，将房源下房间中没有设备的房间移除
            removeNoDevicesRoom(yunDingHomes);

            for (YunDingHomeInfoVO yunDingHomeInfoVO : yunDingHomes) {
                HomeVO homeVO = YunDingHomeInfoVO.toH2ome(yunDingHomeInfoVO);
                thirdHomeList.add(homeVO);
            }

        }
        //水滴的房源信息
        List<HomeVO> localHomeList = new ArrayList<>();
        List<String> list;
        //判断是分散式(0是集中式，1是分散式)
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            //查询子账号信息
            String employerId=smartLockDao.queryEmployer(userId);
            if(employerId==null){
                list=smartLockDao.findDispersedHomesAndPublicZone(userId);
                //查询没有公共区域的分散式房源id并给它添加公共区域
                if(list.size()!=0){
                    for(String roomId:list){
                        smartLockDao.dispersiveAddition(roomId);
                    }
                }
                localHomeList = smartLockDao.findDispersedHomes(userId);
            }else{
                list=smartLockDao.queryDispersedHomesAndPublicZone(employerId);
                if(list.size()!=0){
                    for(String roomId:list){
                        smartLockDao.dispersiveAddition(roomId);
                    }
                }
                //查询子账号可控房源
                List<String> housesIdList=smartLockDao.queryEmployerHouses(employerId);
                for(String housesId : housesIdList){
                    localHomeList.addAll(smartLockDao.findDispersedSubaccountHomes(housesId));
                }
            }
            Iterator<HomeVO> iterator = thirdHomeList.iterator();
            while (iterator.hasNext()) {
                HomeVO homeVO = iterator.next();
                if (!homeVO.getHomeType().equals(YunDingHomeTypeEnum.DISPERSED.getCode())) {
                    iterator.remove();
                }
            }
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            //查询子账号信息
            String employerapatmentsid=smartLockDao.findEmployer(userId);
            if(employerapatmentsid==null){
                //查询没有公共区域的集中式房源id并给它添加公共区域
                list=smartLockDao.centralizedFindDispersedHomes(userId);
                if(list.size()!=0){
                    for(String roomId:list){
                        smartLockDao.centralizedAddition(roomId);
                    }
                }
                localHomeList = smartLockDao.findConcentrateHomes(userId);
            }else{
                list=smartLockDao.centralizedqueryDispersedHomes(employerapatmentsid);
                if(list.size()!=0){
                    for(String roomId:list){
                        smartLockDao.centralizedAddition(roomId);
                    }
                }
                //子账号，根据子账号查询apatment
                List<String> apatmentIdList=smartLockDao.findEmployerApatments(employerapatmentsid);
                for(String apatmentId : apatmentIdList){
                    localHomeList.addAll(smartLockDao.findCentralizedHomes(apatmentId));
                }
            }
            Iterator<HomeVO> iterator = thirdHomeList.iterator();
            while (iterator.hasNext()) {
                HomeVO homeVO = iterator.next();
                if (!homeVO.getHomeType().equals(YunDingHomeTypeEnum.CONCENTRAT.getCode())) {
                    iterator.remove();
                }
            }

        }
        //房间关联数据处理
        for (HomeVO localHomeVO : localHomeList) {
            List<RoomVO> localRooms = localHomeVO.getRooms();
            for (RoomVO localRoom : localRooms) {
                String thirdRoomId = localRoom.getThirdRoomId();
                if (thirdRoomId != null) {
                    for (HomeVO thirdHomeVO : thirdHomeList) {
                        List<RoomVO> thirdRooms = thirdHomeVO.getRooms();
                        //遍历判断第三方房源是否关联并添加关联信息
                        for(RoomVO thirdRoom:thirdRooms ){
                            if (thirdRoomId.equals(thirdRoom.getThirdRoomId())) {
                                thirdRoom.setRoomId(localRoom.getRoomId());
                                thirdRoom.setRoomName(localRoom.getRoomName());
                                thirdRoom.setDataType(localRoom.getDataType());
                                thirdRoom.setRoomAssociationStatus("1");
                                break;
                            }
                        }
                        Iterator<RoomVO> iterator = thirdRooms.iterator();
                        while (iterator.hasNext()) {
                            RoomVO roomVO = iterator.next();
                            if (thirdRoomId.equals(roomVO.getThirdRoomId())) {
                                localRoom.setThirdRoomName(roomVO.getThirdRoomName());
                                localHomeVO.setThirdHomeId(thirdHomeVO.getHomeId());
                                thirdHomeVO.setLocalHomeId(localHomeVO.getHomeId());
//                                roomVO.setRoomAssociationStatus("1");
//                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
            }
        }
        Map<String, List<HomeVO>> map = new HashMap<>();
        map.put("thirdHomeList", thirdHomeList);
        map.put("localHomeList", localHomeList);
        return map;
    }
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
     * 关联
     * @param smartHouseMappingVO
     * @throws SmartLockException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ParseException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmAssociation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException, ClassNotFoundException, IllegalAccessException, InstantiationException, ParseException {
        ///// 1.1 获取数据
        //集中式或者分散式类型
        String type = smartHouseMappingVO.getType();
        //用户id
        String userId = smartHouseMappingVO.getUserId();
        //房屋或者公共区域类型
        String dataType = smartHouseMappingVO.getDataType();
        //第三方房源Id
        String thirdHomeId = smartHouseMappingVO.getThirdHomeId();
        //第三方房间Id
        String thirdRoomId = smartHouseMappingVO.getThirdRoomId();
        //获取本地公共区域或房间的Id
        String roomId = smartHouseMappingVO.getRoomId();
        //获得厂商
        String providerCode = smartHouseMappingVO.getFactoryType();

        String gateWayuuid=smartHouseMappingVO.getGateWayuuid();

        String Uuids =smartHouseMappingVO.getUuid();
        String[]  strs=Uuids.split(",");
        for(int i=0,len=strs.length;i<len;i++){
            if(strs[i].toString()!=null){
                String Uuid=strs[i];
                String publicZoneId = null;

                //1.2 获取公区
                //判断是否是公共区域
                if (HouseMappingDataTypeEnum.PUBLICZONE.getCode().equals(dataType)) {
                    publicZoneId = roomId;
                }
                //判断是否是房间
                else if (HouseMappingDataTypeEnum.ROOM.getCode().equals(dataType)) {
                    //判断是分散式
                    if (HouseStyleEnum.DISPERSED.getCode().equals(type)) {
                        //查询该房间所属房源的公共区域
                        publicZoneId = smartLockDao.findDispersedPublicZoneByRoomId(roomId);
                        //判断是集中式
                    } else if (HouseStyleEnum.CONCENTRAT.getCode().equals(type)) {
                        //查询该房间所属房源的公共区域
                        publicZoneId = smartLockDao.findConcentratePublicZoneByRoomId(roomId);
                    } else {
                        throw new SmartLockException("参数异常");
                    }
                } else {
                    throw new SmartLockException("参数异常");
                }



                List<SmartLockGateWayHadBindInnerLockVO> gatewayBindInnerLocks = smartLockDao.findGatewayBindInnerLock(type, publicZoneId, providerCode);
                IWatermeter iWatermeter = getIWatermeter();
                try {
                    Date day=new Date();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SmartDeviceV2 smartDeviceV2=new SmartDeviceV2();
                    if(publicZoneId==roomId){
                        saveGatWay(iWatermeter,Uuid,userId,type,publicZoneId,providerCode);
                    }else {
                        String watermeterInfo=iWatermeter.getWatermeterInfo(Uuid,providerCode,userId);
                        JSONObject resJson = JSONObject.parseObject(watermeterInfo);
                        String info =  resJson.getString("info");
                        JSONObject jsonObject = JSONObject.parseObject(info);
                        String meter_type = jsonObject.getString("meter_type");
                        String name=null;
                        if("1".equals(meter_type)){
                            name="冷水表";
                        }else if("2".equals(meter_type)){
                            name="热水表";
                        }
                        String onoff = jsonObject.getString("onoff");
                        String manufactory=jsonObject.getString("manufactory");
                        smartDeviceV2.setBrand("dding");
                        smartDeviceV2.setConnectionStatus(onoff);
                        smartDeviceV2.setConnectionStatusUpdateTime(df.format(day));
                        smartDeviceV2.setCreatedBy(userId);
                        smartDeviceV2.setHouseCatalog(type);
                        smartDeviceV2.setName(name);
                        smartDeviceV2.setProviderCode(providerCode);
                        smartDeviceV2.setRoomId(roomId);
                        smartDeviceV2.setSmartDeviceType("2");
                        smartDeviceV2.setThreeId(Uuid);
                        //新增水表关联记录
                        smartLockDao.addSmartDevice(smartDeviceV2);
                        SmartWatermeter smartWatermeter=new SmartWatermeter();
                        smartWatermeter.setSmartWatermeterId(Long.parseLong(smartDeviceV2.getSmartDeviceId()));
                        smartWatermeter.setCreatedAt(new Date());
                        smartWatermeter.setCreatedBy(Long.parseLong(userId));
                        smartWatermeter.setUpdatedAt(new Date());
                        smartWatermeter.setUpdatedBy(Long.parseLong(userId));
                        smartWatermeter.setRoomId(Long.parseLong(roomId));
                        smartWatermeter.setHouseCatalog(Long.parseLong(type));
                        smartWatermeter.setMeter(meter_type);
                        smartWatermeter.setUuid(Uuid);
                        smartWatermeter.setOnoffStatus(Long.parseLong(onoff));
                        smartWatermeter.setManufactory(manufactory);
                        smartLockDao.saveWaterMeter(smartWatermeter);
                        String smartGatWayid=smartLockDao.querySmartGatWayid(publicZoneId);
                        if(smartGatWayid==null){
                            smartGatWayid= saveGatWay(iWatermeter,gateWayuuid,userId,type,publicZoneId,providerCode);
                            //3.3 门锁房间关联
                            SmartHouseMappingVO houseMapping = SmartHouseMappingVO.toH2ome(smartHouseMappingVO);
                            houseMapping.setH2omeId(publicZoneId);
                            houseMapping.setDataType("5");
                            houseMapping.setThreeId(thirdHomeId);

                            //查询该关联关系原先是否存在
                            SmartHouseMappingVO houseMappingRecord = smartLockDao.findHouseMappingRecord(houseMapping);
                            //该记录存在，修改该映射记录
                            if (houseMappingRecord != null) {
                                smartLockDao.updateAssociation(houseMapping);
                            } else {
                                smartLockDao.addAssociation(houseMapping);
                            }
                        }
                        smartLockDao.addSmartDeviceBind(smartDeviceV2.getSmartDeviceId(), smartGatWayid);
                        SmartHouseMappingVO houseMapping = SmartHouseMappingVO.toH2ome(smartHouseMappingVO);
                        //查询该关联关系原先是否存在
                        SmartHouseMappingVO houseMappingRecord = smartLockDao.findHouseMappingRecord(houseMapping);
                        //该记录存在，修改该映射记录
                        if (houseMappingRecord != null) {
                            smartLockDao.updateAssociation(houseMapping);
                        } else {
                            smartLockDao.addAssociation(houseMapping);
                        }
                    }
                } catch (WatermeterException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 取消关联
     * @param smartHouseMappingVO
     * @throws SmartLockException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelRelation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException {
        String type = smartHouseMappingVO.getType();
        SmartHouseMappingVO houseMapping = SmartHouseMappingVO.toH2ome(smartHouseMappingVO);
        String dataType = houseMapping.getDataType();
        List<SmartHouseMappingVO> roomMappingList = new ArrayList<SmartHouseMappingVO>();
        //判断取消关联的是公共区域之间的关联
        if (HouseMappingDataTypeEnum.PUBLICZONE.getCode().equals(dataType)) {
            //判断是分散式
            if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
                roomMappingList = smartLockDao.findDispersedRoomMappingByPublicZone(houseMapping);
                //判断是集中式
            } else {
                roomMappingList = smartLockDao.findConcentrateRoomMappingByPublicZone(houseMapping);
            }
            //取消该公共区域所属房源下的房间的关联
            for (SmartHouseMappingVO smartHouseMappingVO1 : roomMappingList) {
                smartLockDao.cancelAssociation(smartHouseMappingVO1);
                //清除该房间下的设备
                smartLockDao.clearDevicesByRoomId(smartHouseMappingVO1.getHouseCatalog(),
                        smartHouseMappingVO1.getH2omeId(), smartHouseMappingVO1.getProviderCode());
            }
            //取消公共区域的关联
            smartLockDao.cancelAssociation(houseMapping);
            //清除该公共区域下的设备
            smartLockDao.clearDevicesByPublicZoneId(houseMapping.getHouseCatalog(),
                    houseMapping.getH2omeId(), houseMapping.getProviderCode());
            //判断取消关联的是房间之间的关联
        } else if (HouseMappingDataTypeEnum.ROOM.getCode().equals(dataType)) {
            smartLockDao.cancelAssociation(houseMapping);
            smartLockDao.clearDevicesByRoomId(houseMapping.getHouseCatalog(),
                    houseMapping.getH2omeId(), houseMapping.getProviderCode());

        }

    }



    public String  saveGatWay(IWatermeter iWatermeter,String Uuid,String userId,String type,String publicZoneId,String providerCode){
        String res= null;
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            res = iWatermeter.getWaterGatewayInfo(Uuid,userId);
        } catch (WatermeterException e) {
            e.printStackTrace();
        }
        JSONObject resJsonObject = JSONObject.parseObject(res);
        String resInfo =  resJsonObject.getString("info");
        JSONObject json = JSONObject.parseObject(resInfo);
        SmartGatewayV2 smartGatewayV2=new SmartGatewayV2();
        String off=json.getString("onoff");
        String description=json.getString("description");
        String mtime=json.getString("mtime");
        String createTime=json.getString("ctime");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf1.parse(createTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        createTime=sdf2.format(date);
        SmartDeviceV2 smartDevice=new SmartDeviceV2();
        smartDevice.setBrand("dding");
        smartDevice.setConnectionStatus(off);
        smartDevice.setConnectionStatusUpdateTime(df.format(day));
        smartDevice.setCreatedBy(userId);
        smartDevice.setHouseCatalog(type);
        smartDevice.setName(description);
        smartDevice.setProviderCode(providerCode);
        smartDevice.setPublicZoneId(publicZoneId);
        smartDevice.setSmartDeviceType("5");
        smartDevice.setThreeId(Uuid);
        smartLockDao.addSmartDevice(smartDevice);
        String smartGatWayid=smartDevice.getSmartDeviceId();
        smartGatewayV2.setSmartGatewayId(smartDevice.getSmartDeviceId());
        smartGatewayV2.setUuid(Uuid);
        smartGatewayV2.setInstallTime(createTime);
        smartGatewayV2.setBrand("dding");
        smartGatewayV2.setModel(description);
        smartLockDao.saveGatWay(smartGatewayV2);
        return smartGatWayid;
    }

    //处理云丁的房源数据，将房源下房间中没有设备的房间移除
    private void removeNoDevicesRoom(List<YunDingHomeInfoVO> yunDingHomes) {
        for (YunDingHomeInfoVO yunDingHomeInfoVO : yunDingHomes) {
            List<YunDingRoomInfoVO> rooms = yunDingHomeInfoVO.getRooms();
            List<YunDingDeviceInfoVO> devices = yunDingHomeInfoVO.getDevices();
            Iterator<YunDingRoomInfoVO> roomIterator = rooms.iterator();
            while (roomIterator.hasNext()) {
                boolean flag = true;
                YunDingRoomInfoVO roomInfo = roomIterator.next();
                for (YunDingDeviceInfoVO deviceInfo : devices) {
                    if (roomInfo.getRoomId().equals(deviceInfo.getRoomId())&&deviceInfo.getUuid()!=null&&(deviceInfo.getType().equals("watermeter")||deviceInfo.getType().equals("water_gateway"))) {
//                        Log.info("========roomInfo:{}", roomInfo);
//                        Log.info("========deviceInfo:{}", deviceInfo);
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    roomIterator.remove();
                }
            }
        }
    }

}
