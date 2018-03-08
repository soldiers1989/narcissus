package com.ih2ome.hardware_service.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.service.SmartLockService;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.factory.SmartLockOperateFactory;
import com.ih2ome.sunflower.entity.narcissus.*;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.model.backup.RoomVO;
import com.ih2ome.sunflower.vo.pageVo.enums.*;
import com.ih2ome.sunflower.vo.pageVo.smartLock.LockInfoVo;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockDetailVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGateWayHadBindInnerLockVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.SmartLockFirmEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.YunDingHomeTypeEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingDeviceInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingHomeInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingRoomInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
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
        List<HomeVO> localHomeList = null;
        //判断是分散式(0是集中式，1是分散式)
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            localHomeList = smartLockDao.findDispersedHomes(userId);
            Iterator<HomeVO> iterator = thirdHomeList.iterator();
            while (iterator.hasNext()) {
                HomeVO homeVO = iterator.next();
                if (!homeVO.getHomeType().equals(YunDingHomeTypeEnum.DISPERSED.getCode())) {
                    iterator.remove();
                }
            }
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            localHomeList = smartLockDao.findConcentrateHomes(userId);
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
                        Iterator<RoomVO> iterator = thirdRooms.iterator();
                        while (iterator.hasNext()) {
                            RoomVO roomVO = iterator.next();
                            if (thirdRoomId.equals(roomVO.getThirdRoomId())) {
                                localRoom.setThirdRoomName(roomVO.getThirdRoomName());
                                localHomeVO.setThirdHomeId(thirdHomeVO.getHomeId());
                                thirdHomeVO.setLocalHomeId(localHomeVO.getHomeId());
                                roomVO.setRoomAssociationStatus("1");
//                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
            }
        }
        Map<String, List<HomeVO>> map = new HashMap<String, List<HomeVO>>();
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
                    if (roomInfo.getRoomId().equals(deviceInfo.getRoomId())&&deviceInfo.getUuid()!=null) {
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

        //2 清除该房间下的所有设备信息和该房间所在房源的公共区域的设备信息
        //2.1 清除该非公共区域下的设备信息(外门锁,网关设备)
        if (HouseMappingDataTypeEnum.ROOM.getCode().equals(dataType)) {
            //清除该房间下的设备信息(内门锁)
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
        }
        List<SmartLockGateWayHadBindInnerLockVO> gatewayBindInnerLocks = smartLockDao.findGatewayBindInnerLock(type, publicZoneId, providerCode);
        //2.2 清除该公共区域下的设备信息(外门锁,网关设备)
        smartLockDao.clearDevicesByPublicZoneId(type, publicZoneId, providerCode);

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
//            新增设备(网关)记录绑定公共区域
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
                smartLockDao.addSmartLockPassword(smartLockPassword);
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
                    smartLockDao.addSmartLockPassword(smartLockPassword);
                }
            }
        }

        //3.3 房间关联
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
        String errMsg = jsonObject.getString("ErrMsg");
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
        Map<String, ArrayList<SmartMistakeInfo>> map = handleRecords(list);
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
}
