package com.ih2ome.hardware_service.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.service.SmartLockService;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.factory.SmartLockOperateFactory;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.model.backup.RoomVO;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    @Transactional
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
     * 房间关联
     *
     * @param smartHouseMappingVO
     */
    @Override
    @Transactional
    public void confirmAssociation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //TODO: 删除原来同步过的设备
        String type = smartHouseMappingVO.getType();
        String userId = smartHouseMappingVO.getUserId();
        SmartHouseMappingVO houseMapping = SmartHouseMappingVO.toH2ome(smartHouseMappingVO);
        SmartLockFirmEnum lockFirmEnum = SmartLockFirmEnum.getByCode(houseMapping.getProviderCode());

        houseMapping.setDataType(HouseMappingDataTypeEnum.ROOM.getCode());
        ISmartLock iSmartLock = SmartLockOperateFactory.createSmartLock(lockFirmEnum.getCode());
        Map<String, Object> map = iSmartLock.searchHouseDeviceInfo(userId, smartHouseMappingVO.getThirdRoomId());
        List<LockVO> lockVOList = iSmartLock.searchRoomDeviceInfo(userId, smartHouseMappingVO.getThirdRoomId());
        List<GatewayInfoVO> publicGatewayInfoVOList = (List<GatewayInfoVO>) map.get("gatewayInfoVOList");
        List<LockVO> publicLockVOList = (List<LockVO>) map.get("lockVOList");
        //TODO:将设备存进数据库
        //TODO:lockVOLIst入门锁表关联“房间”
        //TODO:publicGatewayInfoVOList入网关表关联“公共区域”
        //TODO:publicLockVOList入门锁表关联“公共区域”


    }
}
