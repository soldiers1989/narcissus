package com.ih2ome.hardware_service.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ExpireTime;
import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.service.SmartLockService;
import com.ih2ome.hardware_service.service.peony.SMSInterface.SMSUtil;
import com.ih2ome.hardware_service.service.peony.smartlockInterface.ISmartLock;
import com.ih2ome.hardware_service.service.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.hardware_service.service.peony.smartlockInterface.factory.SmartLockOperateFactory;
import com.ih2ome.sunflower.entity.caspain.*;
import com.ih2ome.sunflower.entity.narcissus.*;
import com.ih2ome.sunflower.entity.narcissus.SmartLock;
import com.ih2ome.sunflower.entity.narcissus.SmartLockPassword;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.model.backup.RoomVO;
import com.ih2ome.sunflower.vo.pageVo.enums.*;
import com.ih2ome.sunflower.vo.pageVo.smartLock.*;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.SmartLockFirmEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.YunDingHomeTypeEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingDeviceInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingHomeInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingRoomInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.sms.enums.SMSCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Sky
 * @create 2018/01/22
 * @email sky.li@ixiaoshuidi.com
 **/
@Service
public class SmartLockServiceImpl implements SmartLockService {
    private static final Logger Log = LoggerFactory.getLogger(SmartLockServiceImpl.class);

    @Resource
    private SmartLockDao smartLockDao;


    /**
     * 查询第三方和水滴的房源信息
     *
     * @param userId
     * @param type
     * @param factoryType
     * @return
     */
    @Override
    public Map<String, List<HomeVO>> searchHome(String userId, String type, String factoryType) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException {
        Log.info("==========查询房源信息userId:{}", userId);
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
            String employerId = smartLockDao.queryEmployer(userId);
            if (employerId == null) {
                list = smartLockDao.findDispersedHomesAndPublicZone(userId);
                //查询没有公共区域的分散式房源id并给它添加公共区域
                if (list.size() != 0) {
                    for (String houseId : list) {
                        smartLockDao.dispersiveAddition(houseId);
                    }
                }
                localHomeList = smartLockDao.findDispersedHomes(userId);
            } else {
                list = smartLockDao.queryDispersedHomesAndPublicZone(employerId);
                if (list.size() != 0) {
                    for (String roomId : list) {
                        smartLockDao.dispersiveAddition(roomId);
                    }
                }
                //查询子账号可控房源
                List<String> housesIdList = smartLockDao.queryEmployerHouses(employerId);
                for (String housesId : housesIdList) {
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
            String employerapatmentsid = smartLockDao.findEmployer(userId);
            if (employerapatmentsid == null) {
                //查询没有公共区域的集中式房源id并给它添加公共区域
                list = smartLockDao.centralizedFindDispersedHomes(userId);
                if (list.size() != 0) {
                    for (String roomId : list) {
                        smartLockDao.centralizedAddition(roomId);
                    }
                }
                localHomeList = smartLockDao.findConcentrateHomes(userId);
            } else {
                list = smartLockDao.centralizedqueryDispersedHomes(employerapatmentsid);
                if (list.size() != 0) {
                    for (String roomId : list) {
                        smartLockDao.centralizedAddition(roomId);
                    }
                }
                //子账号，根据子账号查询apatment
                List<String> apatmentIdList = smartLockDao.findEmployerApatments(employerapatmentsid);
                for (String apatmentId : apatmentIdList) {
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
                        for (RoomVO thirdRoom : thirdRooms) {
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
                    if (roomInfo.getRoomId().equals(deviceInfo.getRoomId()) && deviceInfo.getUuid() != null && (deviceInfo.getType().equals("gateway") || deviceInfo.getType().equals("lock"))) {
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

    /**
     * 取消房间关联
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAssociation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException {
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

    /**
     * 房间或公共区域关联
     *
     * @param smartHouseMappingVO
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

        String Uuid = smartHouseMappingVO.getUuid();
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
        List<SmartLockGateWayHadBindInnerLockVO> gatewayBindInnerLocks =new ArrayList<SmartLockGateWayHadBindInnerLockVO>();
        //2 清除该房间下的所有设备信息和该房间所在房源的公共区域的设备信息
        //2.1 清除该非公共区域下的设备信息(外门锁,网关设备)
        if (HouseMappingDataTypeEnum.ROOM.getCode().equals(dataType)) {
            //清除 该房间下的设备信息(内门锁)
            smartLockDao.clearDevicesByRoomId(type, roomId, providerCode);
            //公共区域之间建立映射。
            SmartHouseMappingVO houseMapping = SmartHouseMappingVO.toH2ome(smartHouseMappingVO);
            houseMapping.setH2omeId(publicZoneId);
            houseMapping.setThreeId(thirdHomeId);
            houseMapping.setDataType(HouseMappingDataTypeEnum.PUBLICZONE.getCode());
            //查询该关联关系原先是否存在
            SmartHouseMappingVO houseMappingRecord = smartLockDao.findHouseMappingRecord(houseMapping);
            //该记录存在，修改该映射记录
            if (houseMappingRecord != null) {
                smartLockDao.updateAssociation(houseMapping);
            } else {
                smartLockDao.addAssociation(houseMapping);
            }
            gatewayBindInnerLocks=smartLockDao.findGatewayBindInnerLock(type, publicZoneId, providerCode);
            //2.2 清除该公共区域下的设备信息(外门锁,网关设备)
            smartLockDao.clearDevicesByPublicZoneId(type, publicZoneId, providerCode);
        }


//        JSONObject resJson = JSONObject.parseObject(watermeterInfo);
//
//        String info =  resJson.getString("info");
//        JSONObject jsonObject = JSONObject.parseObject(info);
//        int meter_type = jsonObject.getIntValue("meter_type");
//        int onoff = jsonObject.getIntValue("onoff");
//        Long time=detail.getLong("time");
        //3 根据厂商建立关联
        //3.1 获取云丁相关信息

        SmartLockFirmEnum lockFirmEnum = SmartLockFirmEnum.getByCode(providerCode);
        ISmartLock iSmartLock = SmartLockOperateFactory.createSmartLock(lockFirmEnum.getCode());
        Map<String, Object> houseDeviceInfoMap = iSmartLock.searchHouseDeviceInfo(userId, thirdHomeId);
        //获得云丁该房屋下的网关信息
        List<SmartGatewayV2> publicGatewayList = (List<SmartGatewayV2>) houseDeviceInfoMap.get("gatewayInfoVOList");
        //获得云丁该房屋下的外门锁信息
        List<SmartLock> publicLockList = (List<SmartLock>) houseDeviceInfoMap.get("lockVOList");
        //3.2 房间下的设备关联
        //3.2.1 将网关信息插入数据库
        for (SmartGatewayV2 smartGatewayV2 : publicGatewayList) {
            SmartDeviceV2 gatewayDevice = smartGatewayV2.getSmartDeviceV2();
            gatewayDevice.setPublicZoneId(publicZoneId);
            gatewayDevice.setCreatedBy(userId);
            gatewayDevice.setHouseCatalog(type);
            gatewayDevice.setProviderCode(providerCode);
//           新增设备(网关)记录绑定公共区域
            smartLockDao.addSmartDevice(gatewayDevice);
            String gatewayDeviceId = gatewayDevice.getSmartDeviceId();
            smartGatewayV2.setSmartGatewayId(gatewayDeviceId);
            //新增网关记录
            smartLockDao.addSmartGateway(smartGatewayV2);
        }
        //3.2.2 修改内门锁与网关的绑定信息
        for (SmartLockGateWayHadBindInnerLockVO gateWayHadBindInnerLockVO : gatewayBindInnerLocks) {
            String gatewayThirdId = gateWayHadBindInnerLockVO.getGatewayThirdId();
            for (SmartGatewayV2 smartGatewayV2 : publicGatewayList) {
                String uuid = smartGatewayV2.getUuid();
                if (gatewayThirdId.equals(uuid)) {
                    String smartGatewayId = smartGatewayV2.getSmartGatewayId();
                    Long gatewayId = gateWayHadBindInnerLockVO.getGatewayId();
                    Long innerLockId = gateWayHadBindInnerLockVO.getInnerLockId();
                    smartLockDao.updateInnerLockBindGateway(smartGatewayId, gatewayId, innerLockId);
                }
            }
        }
        //3.2.3 将外门锁信息插入数据库
        for (SmartLock publicLock : publicLockList) {
            //获取该门锁下的网关uuid
            String gatewayUuid = publicLock.getGatewayUuid();
            SmartDeviceV2 publicLockDevice = publicLock.getSmartDeviceV2();
            publicLockDevice.setPublicZoneId(publicZoneId);
            publicLockDevice.setCreatedBy(userId);
            publicLockDevice.setHouseCatalog(type);
            publicLockDevice.setProviderCode(providerCode);
            //新增设备(外门锁)记录绑定公共区域
            smartLockDao.addSmartDevice(publicLockDevice);
            String lockDeviceId = publicLockDevice.getSmartDeviceId();
            publicLock.setSmartLockId(lockDeviceId);
            //新增门锁记录
            smartLockDao.addSmartLock(publicLock);
            for (SmartGatewayV2 smartGatewayV2 : publicGatewayList) {
                String uuid = smartGatewayV2.getUuid();
                if (uuid.equals(gatewayUuid)) {
                    String gatewayDeviceId = smartGatewayV2.getSmartGatewayId();
                    smartLockDao.addSmartDeviceBind(lockDeviceId, gatewayDeviceId);
                    break;
                }
            }
            List<SmartLockPassword> smartLockPasswordList = publicLock.getSmartLockPasswordList();
            for (SmartLockPassword smartLockPassword : smartLockPasswordList) {
                smartLockPassword.setSmartLockId(lockDeviceId);
                smartLockPassword.setProviderCode(providerCode);
                if (smartLockPassword.getPassword() != null) {
                    smartLockDao.addSmartLockPassword(smartLockPassword);
                }
            }
        }
        //3.2.4 表明是房间
        if (!publicZoneId.equals(roomId)) {
            //获取该房间下的内门锁
            List<SmartLock> innerLockList = iSmartLock.searchRoomDeviceInfo(userId, thirdRoomId);
            //将内门锁信息插入数据库
            for (SmartLock innerLock : innerLockList) {
                //获取该门锁下的网关uuid
                String gatewayUuid = innerLock.getGatewayUuid();
                SmartDeviceV2 innerLockDevice = innerLock.getSmartDeviceV2();
                innerLockDevice.setRoomId(roomId);
                innerLockDevice.setCreatedBy(userId);
                innerLockDevice.setHouseCatalog(type);
                innerLockDevice.setProviderCode(providerCode);
                //新增设备(内门锁)记录绑定房间
                smartLockDao.addSmartDevice(innerLockDevice);
                String lockDeviceId = innerLockDevice.getSmartDeviceId();
                innerLock.setSmartLockId(lockDeviceId);
                //新增门锁记录
                smartLockDao.addSmartLock(innerLock);
                for (SmartGatewayV2 smartGatewayV2 : publicGatewayList) {
                    String uuid = smartGatewayV2.getUuid();
                    if (uuid.equals(gatewayUuid)) {
                        String gatewayDeviceId = smartGatewayV2.getSmartGatewayId();
                        smartLockDao.addSmartDeviceBind(lockDeviceId, gatewayDeviceId);
                        break;
                    }
                }
                List<SmartLockPassword> smartLockPasswordList = innerLock.getSmartLockPasswordList();
                for (SmartLockPassword smartLockPassword : smartLockPasswordList) {
                    smartLockPassword.setSmartLockId(lockDeviceId);
                    smartLockPassword.setProviderCode(providerCode);
                    if (smartLockPassword.getPassword() != null) {
                        smartLockDao.addSmartLockPassword(smartLockPassword);
                    }
                }
            }
        }


        //3.3 门锁房间关联
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

    /**
     * 根据门锁id查询密码列表
     *
     * @param lockId
     * @return
     * @throws SmartLockException
     */
    @Override
    public List<SmartLockPassword> findPasswordList(String lockId) throws SmartLockException {
        List<SmartLockPassword> passwords = smartLockDao.findPasswordListByLockId(lockId);
        return passwords;
    }

    /**
     * 新增门锁密码
     *
     * @throws SmartLockException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addLockPassword(LockPasswordVo lockPasswordVo) throws SmartLockException, IllegalAccessException, InstantiationException, ClassNotFoundException, ParseException {
        //获取门锁id
        String smartLockId = lockPasswordVo.getSerialNum();
        //根据门锁Id(即设备id)查询第三方门锁uuid
        SmartDeviceV2 smartDeviceV2 = smartLockDao.findThirdLockUuid(smartLockId);
        String thirdLockUuid = smartDeviceV2.getThreeId();
        String provideCode = smartDeviceV2.getProviderCode();
        ISmartLock smartLock = SmartLockOperateFactory.createSmartLock(provideCode);
        lockPasswordVo.setUuid(thirdLockUuid);
        String digitPwdType = lockPasswordVo.getDigitPwdType();
        String pwdTypeName = SmartLockPwdTypeEnum.getByCode(digitPwdType);
        lockPasswordVo.setName(pwdTypeName);
        if (SmartLockPwdTypeEnum.MANAGER_PASSWORD.getCode().equals(digitPwdType)) {
            lockPasswordVo.setIsDefault(SmartLockPasswordIsDefaultEnum.PASSWORD_ISDEFAULT.getCode());
            lockPasswordVo.setPwdType(SmartLockPasswordValidTypeEnum.PASSWORD_FOREVER.getCode());
            lockPasswordVo.setEnableTime(null);
            lockPasswordVo.setDisableTime(null);
//            long currentTime = System.currentTimeMillis();
//            lockPasswordVo.setEnableTime(DateUtils.longToString(currentTime, "yyyy-MM-dd HH:mm:ss"));
//            lockPasswordVo.setDisableTime("2060-12-31 00:00:00");
        } else {
            lockPasswordVo.setIsDefault(SmartLockPasswordIsDefaultEnum.PASSWORD_ISNOTDEFAULT.getCode());
            lockPasswordVo.setPwdType(SmartLockPasswordValidTypeEnum.PASSWORD_TIMEVALID.getCode());
        }
        String result = smartLock.addLockPassword(lockPasswordVo);
        JSONObject resJson = JSONObject.parseObject(result);
        String errNo = resJson.getString("ErrNo");
        if (!errNo.equals("0")) {
            throw new SmartLockException("第三方密码添加失败");
        }
        //第三方密码生成的id编号
        lockPasswordVo.setProvideCode(provideCode);
        lockPasswordVo.setStatus(SmartLockPasswordStatusEnum.PASSWORD_START.getCode());
        String passwordId = resJson.getString("id");
        lockPasswordVo.setPwdNo(passwordId);
        //操作密码记录对象
        SmartLockLog smartLockLog = new SmartLockLog();
        if ("999".equals(passwordId)) {
            String smartLockPasswordId = smartLockDao.findLockManagePassword(smartLockId, passwordId);
            lockPasswordVo.setId(smartLockPasswordId);
            //判断该管理密码在数据库中是否存在
            if (smartLockPasswordId != null) {
                //存在则修改
                smartLockDao.updateLockPassword(lockPasswordVo);
                smartLockLog.setSmartLockPasswordId(Long.valueOf(smartLockPasswordId));
                smartLockLog.setOperatorType(SmartLockOperatorTypeEnum.SMARTLOCK_PASSWORD_UPDATE.getCode().longValue());
            } //不存在则创建
            else {
                smartLockDao.addLockPassword(lockPasswordVo);
                smartLockLog.setSmartLockPasswordId(Long.valueOf(lockPasswordVo.getId()));
                smartLockLog.setOperatorType(SmartLockOperatorTypeEnum.SMARTLOCK_PASSWORD_ADD.getCode().longValue());
            }
        }//不是管理密码则直接创建
        else {
            smartLockDao.addLockPassword(lockPasswordVo);
            smartLockLog.setSmartLockPasswordId(Long.valueOf(lockPasswordVo.getId()));
            smartLockLog.setOperatorType(SmartLockOperatorTypeEnum.SMARTLOCK_PASSWORD_ADD.getCode().longValue());
        }
        smartLockLog.setCreatedBy(Long.valueOf(lockPasswordVo.getUserId()));
        smartLockLog.setSmartLockId(Long.valueOf(smartLockId));
        //新增密码操作记录
        smartLockDao.addSmartLockOperationLog(smartLockLog);
    }

    /**
     * 删除门锁密码
     *
     * @param
     * @throws SmartLockException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLockPassword(String passwordId, String userId) throws SmartLockException, IllegalAccessException, InstantiationException, ClassNotFoundException, ParseException {
        SmartLockPassword password = smartLockDao.findPasswordById(passwordId);
        String providerCode = password.getProviderCode();
        String smartLockId = password.getSmartLockId();
        String thirdPasswordId = password.getThreeId();
        String thirdLockUuid = password.getLock3Id();
        LockPasswordVo passwordVo = new LockPasswordVo();
        passwordVo.setUuid(thirdLockUuid);
        passwordVo.setPwdNo(thirdPasswordId);
        passwordVo.setUserId(userId);
        ISmartLock smartLock = SmartLockOperateFactory.createSmartLock(providerCode);
        String result = smartLock.deleteLockPassword(passwordVo);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String errNo = jsonObject.getString("ErrNo");
        if (!errNo.equals("0")) {
            throw new SmartLockException("第三方密码删除失败");
        }
        smartLockDao.deleteLockPassword(passwordId);
        SmartLockLog smartLockLog = new SmartLockLog();
        smartLockLog.setSmartLockId(Long.valueOf(smartLockId));
        smartLockLog.setSmartLockPasswordId(Long.valueOf(passwordId));
        smartLockLog.setCreatedBy(Long.valueOf(userId));
        smartLockLog.setOperatorType(SmartLockOperatorTypeEnum.SMARTLOCK_PASSWORD_DELETE.getCode().longValue());
        smartLockDao.addSmartLockOperationLog(smartLockLog);
    }

    /**
     * 修改门锁密码
     *
     * @param passwordVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLockPassword(LockPasswordVo passwordVo) throws ClassNotFoundException, SmartLockException, InstantiationException, IllegalAccessException, ParseException {
        SmartLockPassword password = smartLockDao.findPasswordById(passwordVo.getId());
        String providerCode = password.getProviderCode();
        String smartLockId = password.getSmartLockId();
        String thirdPasswordId = password.getThreeId();
        String thirdLockUuid = password.getLock3Id();
        //设置第三方密码对应门锁Id
        passwordVo.setUuid(thirdLockUuid);
        //设置第三方密码id
        passwordVo.setPwdNo(thirdPasswordId);
        passwordVo.setUserId(passwordVo.getUserId());
        ISmartLock smartLock = SmartLockOperateFactory.createSmartLock(providerCode);
        String result = smartLock.updateLockPassword(passwordVo);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String errNo = jsonObject.getString("ErrNo");
        if (!errNo.equals("0")) {
            throw new SmartLockException("第三方密码修改失败");
        }
        smartLockDao.updateLockPassword(passwordVo);
        SmartLockLog smartLockLog = new SmartLockLog();
        smartLockLog.setSmartLockId(Long.valueOf(smartLockId));
        smartLockLog.setSmartLockPasswordId(Long.valueOf(passwordVo.getId()));
        smartLockLog.setCreatedBy(Long.valueOf(passwordVo.getUserId()));
        smartLockLog.setOperatorType(SmartLockOperatorTypeEnum.SMARTLOCK_PASSWORD_UPDATE.getCode().longValue());
        smartLockDao.addSmartLockOperationLog(smartLockLog);
    }

    /**
     * 冻结门锁密码
     *
     * @param userId
     * @param password_id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void frozenLockPassword(String userId, String password_id) throws ClassNotFoundException, SmartLockException, InstantiationException, IllegalAccessException, ParseException {
        SmartLockPassword password = smartLockDao.findPasswordById(password_id);
        String providerCode = password.getProviderCode();
        String smartLockId = password.getSmartLockId();
        String thirdPasswordId = password.getThreeId();
        String thirdLockUuid = password.getLock3Id();
        LockPasswordVo passwordVo = new LockPasswordVo();
        //设置第三方门锁id
        passwordVo.setUuid(thirdLockUuid);
        //设置第三方密码id
        passwordVo.setPwdNo(thirdPasswordId);
        passwordVo.setUserId(userId);
        ISmartLock smartLock = SmartLockOperateFactory.createSmartLock(providerCode);
        String result = smartLock.frozenLockPassword(passwordVo);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String errNo = jsonObject.getString("ErrNo");

        if (!errNo.equals("0")) {
            throw new SmartLockException("第三方密码修改失败");
        }
        smartLockDao.frozenLockPassword(password_id);
        SmartLockLog smartLockLog = new SmartLockLog();
        smartLockLog.setSmartLockId(Long.valueOf(smartLockId));
        smartLockLog.setSmartLockPasswordId(Long.valueOf(password_id));
        smartLockLog.setCreatedBy(Long.valueOf(userId));
        smartLockLog.setOperatorType(SmartLockOperatorTypeEnum.SMARTLOCK_PASSWORD_FROZEN.getCode().longValue());
        smartLockDao.addSmartLockOperationLog(smartLockLog);
    }

    /**
     * 解冻门锁密码
     *
     * @param userId
     * @param passwordId
     */
    @Override
    public void unFrozenLockPassword(String userId, String passwordId) throws ClassNotFoundException, SmartLockException, InstantiationException, IllegalAccessException, ParseException {
        SmartLockPassword password = smartLockDao.findPasswordById(passwordId);
        String providerCode = password.getProviderCode();
        String thirdPasswordId = password.getThreeId();
        String smartLockId = password.getSmartLockId();
        String thirdLockUuid = password.getLock3Id();
        LockPasswordVo passwordVo = new LockPasswordVo();
        //设置第三方门锁id
        passwordVo.setUuid(thirdLockUuid);
        //设置第三方密码id
        passwordVo.setPwdNo(thirdPasswordId);
        passwordVo.setUserId(userId);
        ISmartLock smartLock = SmartLockOperateFactory.createSmartLock(providerCode);
        String result = smartLock.unfrozenLockPassword(passwordVo);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String errNo = jsonObject.getString("ErrNo");
        if (!errNo.equals("0")) {
            throw new SmartLockException("第三方密码修改失败");
        }
        smartLockDao.unFrozenLockPassword(passwordId);
        SmartLockLog smartLockLog = new SmartLockLog();
        smartLockLog.setSmartLockId(Long.valueOf(smartLockId));
        smartLockLog.setSmartLockPasswordId(Long.valueOf(passwordId));
        smartLockLog.setCreatedBy(Long.valueOf(userId));
        smartLockLog.setOperatorType(SmartLockOperatorTypeEnum.SMARTLOCK_PASSWORD_UNFROZEN.getCode().longValue());
        smartLockDao.addSmartLockOperationLog(smartLockLog);
    }

    @Override
    public int updateAutoCollection(int passwordId, int autoCollection) {
        return smartLockDao.updateAutoCollection(passwordId, autoCollection);
    }

    /**
     * 根据门锁id查询门锁详情
     *
     * @param lockId
     * @return
     */
    @Override
    public SmartLockDetailVO findSmartLockDetail(String lockId) throws SmartLockException {
        SmartLockDetailVO lockDetail = smartLockDao.findSmartLockDetail(lockId);
        lockDetail.splitVersion();
        String houseCatalog = lockDetail.getHouseCatalog();
        String publicZoneId = lockDetail.getPublicZoneId();
        String roomId = lockDetail.getRoomId();
        SmartLockDetailVO smartLockDetailVO = null;
        if (HouseStyleEnum.CONCENTRAT.getCode().equals(houseCatalog)) {
            if (StringUtils.isNotBlank(publicZoneId)) {
                smartLockDetailVO = smartLockDao.findConcentrateHomeInfoByPublicZoneId(publicZoneId);
            } else {
                smartLockDetailVO = smartLockDao.findConcentrateHomeInfoByRoomId(roomId);
            }
        } else {
            if (StringUtils.isNotBlank(publicZoneId)) {
                smartLockDetailVO = smartLockDao.findDispersedHomeInfoByPublicZoneId(publicZoneId);
            } else {
                smartLockDetailVO = smartLockDao.findDispersedHomeInfoByRoomId(roomId);
            }
        }
        lockDetail.setHomeName(smartLockDetailVO.getHomeName());
        lockDetail.setRoomName(smartLockDetailVO.getRoomName());
        return lockDetail;
    }

    @Override
    public void addLockPasswordCallBack(LockPasswordVo passwordVo) {
        smartLockDao.addLockPassword(passwordVo);
    }

    @Override
    public void updateLockPasswordCallBack(LockPasswordVo passwordVo) {
        smartLockDao.updateLockPasswordByUuid(passwordVo);
    }

    @Override
    public void deleteLockPasswordCallBack(String uuid) {
        smartLockDao.deleteLockPasswordByUuid(uuid);
    }

    /**
     * 根据门锁id查询开门记录
     *
     * @param lockId
     * @return
     */
    @Override
    public Map<String, ArrayList<SmartMistakeInfo>> findOpenLockRecord(String lockId) throws SmartLockException {
        List<SmartMistakeInfo> list = smartLockDao.findOpenLockRecord(lockId);
        Map<String, ArrayList<SmartMistakeInfo>> map = handleRecords(list);
        return map;
    }

    /**
     * 根据门锁Id查询操作记录
     *
     * @param lockId
     * @return
     */
    @Override
    public Map<String, ArrayList<SmartMistakeInfo>> findHistoryOperations(String lockId) {
        List<SmartMistakeInfo> list = smartLockDao.findHistoryOperations(lockId);
        Map<String, ArrayList<SmartMistakeInfo>> map = handleRecord(list);
        return map;
    }

    /**
     * 查询异常记录
     *
     * @param lockId
     * @return
     * @throws SmartLockException
     */
    @Override
    public Map<String, ArrayList<SmartMistakeInfo>> findExceptionRecords(String lockId) throws SmartLockException {
        List<SmartMistakeInfo> list = smartLockDao.findExceptionRecords(lockId);
        Map<String, ArrayList<SmartMistakeInfo>> map = handleRecords(list);
        return map;
    }

    /**
     * 更新电量
     *
     * @param lockInfoVo
     */
    @Override
    public void updateBatteryInfo(LockInfoVo lockInfoVo) {
        smartLockDao.updateBatteryInfo(lockInfoVo);
    }

    /**
     * 解绑门锁
     *
     * @param uuid
     */
    @Override
    public void uninstallSmartLock(String uuid) {
        smartLockDao.deleteSmartLockByUuid(uuid);
    }

    /**
     * 绑定门锁
     *
     * @param homeId
     * @param roomId
     * @param uuid
     */
    @Override
    public void installSmartLock(String homeId, String roomId, String uuid) {

    }

    /**
     * 处理记录方法
     *
     * @return
     */
    public Map<String, ArrayList<SmartMistakeInfo>> handleRecords(List<SmartMistakeInfo> list) {
        Map<String, ArrayList<SmartMistakeInfo>> map = new LinkedHashMap<String, ArrayList<SmartMistakeInfo>>();
        for (SmartMistakeInfo info : list) {
            info.splitCreatedTime();
            String yearMonthDay = info.getYearMonthDay();
            if (map.containsKey(yearMonthDay)) {
                ArrayList<SmartMistakeInfo> smartMistakeInfos = map.get(yearMonthDay);
                smartMistakeInfos.add(info);
            } else {
                ArrayList<SmartMistakeInfo> smartMistakeInfos = new ArrayList<SmartMistakeInfo>();
                smartMistakeInfos.add(info);
                map.put(yearMonthDay, smartMistakeInfos);
            }
        }
        return map;
    }

    /**
     * 查询操作记录
     *
     * @param list
     * @return
     */
    public Map<String, ArrayList<SmartMistakeInfo>> handleRecord(List<SmartMistakeInfo> list) {
        Map<String, ArrayList<SmartMistakeInfo>> map = new LinkedHashMap<String, ArrayList<SmartMistakeInfo>>();
        for (SmartMistakeInfo info : list) {
            info.splitCreatedTime();
            String yearMonthDay = info.getYearMonthDay();
            if (map.containsKey(yearMonthDay)) {
                ArrayList<SmartMistakeInfo> smartMistakeInfos = map.get(yearMonthDay);
                String describe = info.getUserName() + info.getOperatorType().substring(0, 2) + "了" + info.getPasswordName() + "(" + info.getPassname() + ")";
                info.setDescribe(describe);
                smartMistakeInfos.add(info);
            } else {
                ArrayList<SmartMistakeInfo> smartMistakeInfos = new ArrayList<SmartMistakeInfo>();
                String describe = info.getUserName() + info.getOperatorType().substring(0, 2) + "了" + info.getPasswordName() + "(" + info.getPassname() + ")";
                info.setDescribe(describe);
                smartMistakeInfos.add(info);
                map.put(yearMonthDay, smartMistakeInfos);
            }
        }
        return map;
    }


    /**
     * 获取密码+房间信息
     *
     * @return
     */
    @Override
    public List<PasswordRoomVO> getPasswordRoomList() {
        return smartLockDao.getPasswordRoomList();
    }

    @Override
    public List<PasswordRoomVO> getFrozenPassword(int roomId, int houseCatalog) {
        Log.info("rechargeUnfrozen - getFrozenPassword roomId:{},houseCatalog:{}", roomId, houseCatalog);
        return smartLockDao.getFrozenPassword(roomId, houseCatalog);
    }

    //判断两个日期是否相差几天
    public boolean compareDate(Date time1, Date time2, Integer day) {
        long time = 1000 * 3600 * 24;//一天的时间(秒)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse1 = null;
        Date parse2 = null;
        try {
            parse1 = dateFormat.parse(dateFormat.format(time1));
            parse2 = dateFormat.parse(dateFormat.format(time2));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.error("日期类转换异常");
        }
        long l = parse1.getTime() - parse2.getTime();
        if (day != null) {
            return (0 <= l && l <= day * time);
        } else {
            return l < 0;
        }
    }

    //当前日期的下一天
    public String nextDay(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date time = calendar.getTime();
        String stringDate = dateFormat.format(time);
        return stringDate;
    }

    /**
     * 判断房间账单是否已还清
     */
    @Override
    public boolean judgeRoomPayOff(PasswordRoomVO passwordRoom, int roomRentOrderId) {
        Date nowDate = new Date();
        if (passwordRoom.getHouseCatalog().equals("1")) {
            RoomContract roomContract = smartLockDao.getCaspainRoomContract(passwordRoom.getRoomId());
            Log.info("rechargeUnfrozen roomContract = {}", JSON.toJSONString(roomContract));
            if (roomContract == null) {
                return false;
            }
            Log.info("rechargeUnfrozen roomRentOrderId = {}", roomRentOrderId);
            RoomRentorder roomRentorder = smartLockDao.getCaspainRoomRentorder(roomContract.getId(), roomRentOrderId);
            Log.info("rechargeUnfrozen roomRentorder = {}", JSON.toJSONString(roomRentorder));
            if (roomRentorder == null) {
                return true;
            }
            Log.info("rechargeUnfrozen nowDate = {}", nowDate);
            Log.info("rechargeUnfrozen deadlinePayTime = {}", roomRentorder.getDeadlinePayTime());
            return compareDate(nowDate, roomRentorder.getDeadlinePayTime(), null);
        } else if (passwordRoom.getHouseCatalog().equals("0")) {
            com.ih2ome.sunflower.entity.volga.RoomContract roomContract = smartLockDao.getVolgaRoomContract(passwordRoom.getRoomId());
            Log.info("rechargeUnfrozen roomContract = {}", JSON.toJSONString(roomContract));
            if (roomContract == null) {
                return false;
            }
            com.ih2ome.sunflower.entity.volga.RoomRentorder roomRentorder = smartLockDao.getVolgaRoomRentorder(roomContract.getId(), roomRentOrderId);
            Log.info("rechargeUnfrozen roomRentorder = {}", JSON.toJSONString(roomRentorder));
            if (roomRentorder == null) {
                return true;
            }
            Log.info("rechargeUnfrozen nowDate = {}", nowDate);
            Log.info("rechargeUnfrozen deadlinePayTime = {}", roomRentorder.getDeadlinePayTime());
            return compareDate(nowDate, roomRentorder.getDeadlinePayTime(), null);
        }
        return false;
    }

    /**
     * 判断房间是否逾期并发短信
     *
     * @param passwordRoom
     * @param smsBaseUrl
     * @return
     */
    @Override
    public boolean judgeRoomOverdue(PasswordRoomVO passwordRoom, String smsBaseUrl) {
        String cacheKey = "judgeRoomOverdue_SMSCode";
        if (passwordRoom.getHouseCatalog().equals("1")) {
            RoomContract roomContract = smartLockDao.getCaspainRoomContract(passwordRoom.getRoomId());
            if (roomContract == null) {
                return false;
            } else {
                RoomRentorder roomRentorder = smartLockDao.getCaspainRoomRentorder(roomContract.getId(),0);
                Date nowDate = new Date();
                if (roomRentorder == null) {
                    return false;
                } else {
                    //判断当前时间和最晚交租时间的时间差是否在2天之内
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    if (compareDate(roomRentorder.getDeadlinePayTime(), nowDate, 2)) {
                        RoomCompanyVO roomCompany = smartLockDao.getCaspainRoomCompany(roomContract.getRoomId());
                        JSONObject data = new JSONObject();
                        data.put("brand", roomCompany.getCompanyBrand());
                        data.put("realName", roomContract.getCustomerName());
                        data.put("date", dateFormat.format(roomRentorder.getDeadlinePayTime()));
                        if(CacheUtils.getStr(cacheKey + roomContract.getCustomerPhone()) != null){
                            Log.info("短信发送结果==================跳过");
                        }
                        else {
                            CacheUtils.set(cacheKey + roomContract.getCustomerPhone(),"1", ExpireTime.ONE_MIN);
                            boolean b = SMSUtil.sendTemplateText(smsBaseUrl, SMSCodeEnum.WILL_FREEZE.getName(), roomContract.getCustomerPhone(), data, 0);
                            Log.info("短信发送结果=================={}", b);
                        }
                    }
                }
                return compareDate(roomRentorder.getDeadlinePayTime(), nowDate, null);
            }
        } else if (passwordRoom.getHouseCatalog().equals("0")) {
            com.ih2ome.sunflower.entity.volga.RoomContract roomContract = smartLockDao.getVolgaRoomContract(passwordRoom.getRoomId());
            if (roomContract == null) {
                return false;
            } else {
                com.ih2ome.sunflower.entity.volga.RoomRentorder roomRentorder = smartLockDao.getVolgaRoomRentorder(roomContract.getId(),0);
                Date nowDate = new Date();
                if (roomRentorder == null) {
                    return false;
                } else {
                    //判断当前时间和最晚交租时间的时间差是否在3天之内
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date deadlinePayTime = roomRentorder.getDeadlinePayTime();
                    if (compareDate(deadlinePayTime, nowDate, 2)) {
                        RoomCompanyVO roomCompany = smartLockDao.getVolgaRoomCompany(roomContract.getRoomId());
                        JSONObject data = new JSONObject();
                        data.put("brand", roomCompany.getCompanyBrand());
                        data.put("realName", roomContract.getCustomerName());
                        data.put("date", dateFormat.format(roomRentorder.getDeadlinePayTime()));
                        if(CacheUtils.getStr(cacheKey + roomContract.getCustomerPhone()) != null){
                            Log.info("短信发送结果==================跳过");
                        }
                        else {
                            CacheUtils.set(cacheKey + roomContract.getCustomerPhone(),"1", ExpireTime.ONE_MIN);
                            boolean b = SMSUtil.sendTemplateText(smsBaseUrl, SMSCodeEnum.WILL_FREEZE.getName(), roomContract.getCustomerPhone(), data, 0);
                            Log.info("短信发送结果=================={}", b);
                        }
                    }
                }
                return compareDate(roomRentorder.getDeadlinePayTime(), nowDate, null);
            }
        }
        return false;
    }

    @Override
    public void sendFrozenMessage(PasswordRoomVO passwordRoom, String smsBaseUrl, boolean isFrozen) {
        String cacheKey = "sendFrozenMessage_SMSCode";
        String customerName = "";
        String customerPhone = "";
        String brand = "";
        if (passwordRoom.getHouseCatalog().equals("1")) {
            RoomContract roomContract = smartLockDao.getCaspainRoomContract(passwordRoom.getRoomId());
            customerName = roomContract.getCustomerName();
            customerPhone = roomContract.getCustomerPhone();
            RoomCompanyVO roomCompany = smartLockDao.getCaspainRoomCompany(roomContract.getRoomId());
            brand = roomCompany.getCompanyBrand();
        } else if (passwordRoom.getHouseCatalog().equals("0")) {
            com.ih2ome.sunflower.entity.volga.RoomContract roomContract = smartLockDao.getVolgaRoomContract(passwordRoom.getRoomId());
            customerName = roomContract.getCustomerName();
            customerPhone = roomContract.getCustomerPhone();
            RoomCompanyVO roomCompany = smartLockDao.getVolgaRoomCompany(roomContract.getRoomId());
            brand = roomCompany.getCompanyBrand();
        }

        JSONObject data = new JSONObject();
        data.put("brand", brand);
        data.put("realName", customerName);
        if (CacheUtils.getStr(cacheKey + customerPhone) != null) {
            Log.info("短信发送结果==================跳过");
        } else {
            CacheUtils.set(cacheKey + customerPhone, "1", ExpireTime.ONE_MIN);
            SMSCodeEnum smsCodeEnum = isFrozen ? SMSCodeEnum.HAVE_FROZEN : SMSCodeEnum.HAVE_UNFROZEN;
            boolean b = SMSUtil.sendTemplateText(smsBaseUrl, smsCodeEnum.getName(), customerPhone, data, 0);
            Log.info("短信发送结果=================={}", b);
        }
    }
}
