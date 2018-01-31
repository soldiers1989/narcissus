package com.ih2ome.hardware_service.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.service.SmartLockService;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.factory.SmartLockOperateFactory;
import com.ih2ome.sunflower.entity.narcissus.*;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.model.backup.RoomVO;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseCatalogEnum;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseMappingDataTypeEnum;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseStyleEnum;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.GatewayInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.SmartLockFirmEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.YunDingHomeTypeEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingHomeInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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
                                iterator.remove();
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

    /**
     * 取消房间关联
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
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
            }
            //取消公共区域的关联
            smartLockDao.cancelAssociation(houseMapping);
            //判断取消关联的是房间之间的关联
        } else if (HouseMappingDataTypeEnum.ROOM.getCode().equals(dataType)) {
            smartLockDao.cancelAssociation(houseMapping);
        }

    }

    /**
     * 房间或公共区域关联
     *
     * @param smartHouseMappingVO
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void confirmAssociation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException, ClassNotFoundException, IllegalAccessException, InstantiationException, ParseException {
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
        //判断是否是公共区域
        if (HouseMappingDataTypeEnum.PUBLICZONE.getCode().equals(dataType)) {
            publicZoneId = roomId;
            //判断是否是房间
        } else if (HouseMappingDataTypeEnum.ROOM.getCode().equals(dataType)) {
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
        } else {
            throw new SmartLockException("参数异常");
        }
        //清除该公共区域下的设备信息(外门锁,网关设备)
        smartLockDao.clearDevicesByPublicZoneId(type, publicZoneId, providerCode);

        SmartLockFirmEnum lockFirmEnum = SmartLockFirmEnum.getByCode(providerCode);
        ISmartLock iSmartLock = SmartLockOperateFactory.createSmartLock(lockFirmEnum.getCode());
        Map<String, Object> map = iSmartLock.searchHouseDeviceInfo(userId, thirdHomeId);

        //获得该房屋下的网关信息
        List<SmartGatewayV2> publicGatewayList = (List<SmartGatewayV2>) map.get("gatewayInfoVOList");
        //获得该房屋下的外门锁信息
        List<SmartLock> publicLockList = (List<SmartLock>) map.get("lockVOList");
        //将网关信息插入数据库
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
        //将外门锁信息插入数据库
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
        //表明是房间
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
}
