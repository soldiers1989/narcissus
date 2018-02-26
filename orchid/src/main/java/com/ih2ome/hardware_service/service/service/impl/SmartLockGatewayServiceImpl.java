package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.hardware_service.service.dao.SmartLockGatewayDao;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayService;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseStyleEnum;
import com.ih2ome.sunflower.vo.pageVo.smartLock.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Service
public class SmartLockGatewayServiceImpl implements SmartLockGatewayService{

    @Resource
    SmartLockGatewayDao smartLockGatewayDao;


    @Override
    public List<SmartLockGatewayAndHouseInfoVO> getSmartLockGatewayList(String homeId, String type) {
        if(StringUtils.isNotBlank(homeId)&&StringUtils.isNotBlank(type)){

            if(type.equals(HouseStyleEnum.DISPERSED.getCode())){
                return smartLockGatewayDao.getDispersedGatewayModelByHomeId(homeId);
            }else if(type.equals(HouseStyleEnum.CONCENTRAT.getCode())){
                return smartLockGatewayDao.getConcentrateGatewayModelByHomeId(homeId);
            }else{
                return null;
            }

        }
        return null;

    }

    @Override
    public SmartLockGatewayHadBindVO getSmartLockHadBindGateway(String gatewayId) {
        if(StringUtils.isNotBlank(gatewayId)){
            SmartLockGatewayHadBindVO smartLockGatewayHadBindVO = smartLockGatewayDao.getSmartLockHadBindGateway(gatewayId);
            if(smartLockGatewayHadBindVO!=null){
                List <SmartLockGatewayHadBindRoomVO> smartLockGatewayHadBindRoomVOList = smartLockGatewayDao.getSmartLockAndRoomListByGatewayId(gatewayId);
                smartLockGatewayHadBindVO.setSmartLockGatewayHadBindRoomVOList(smartLockGatewayHadBindRoomVOList);
            }
            return smartLockGatewayHadBindVO;

        }
        return null;

    }

    @Override
    public SmartLockDetailVO getSmartLockGatewayDetailInfo(String gatewayId) {
        if(StringUtils.isNotBlank(gatewayId)){
            SmartLockDetailVO smartLockDetailVO = smartLockGatewayDao.getSmartLockGatewayDetailInfo(gatewayId);
            if(smartLockDetailVO != null){
                smartLockDetailVO.splitVersion();

            }
            return smartLockDetailVO;

        }
        return null;

    }

    @Override
    public List<SmartLockHadBindHouseVo> getHadBindHouseList(String type, String userId) {
        if(StringUtils.isNotBlank(userId)&&StringUtils.isNotBlank(type)){
            if(type.equals(HouseStyleEnum.DISPERSED.getCode())){
                return smartLockGatewayDao.getDispersedHadBindHouseList(userId);
            }else if(type.equals(HouseStyleEnum.CONCENTRAT.getCode())){
                List<SmartLockHadBindHouseVo> smartLockHadBindHouseVoList = smartLockGatewayDao.getConcentrateHadBindHouseList(userId);
                for(SmartLockHadBindHouseVo smartLockHadBindHouseVo:smartLockHadBindHouseVoList){

                    long lockCount = smartLockGatewayDao.getCountOfApartmentLock(smartLockHadBindHouseVo.getHomeId());
                    long onlineCount = smartLockGatewayDao.getCountOfApartmentOnlineLock(smartLockHadBindHouseVo.getHomeId());
                    long offlineCount = lockCount-onlineCount;
                    long lowerPowerCount = smartLockGatewayDao.getCountOfApartmentLowPowerLock(smartLockHadBindHouseVo.getHomeId());
                    smartLockHadBindHouseVo.setLockCount(lockCount);
                    smartLockHadBindHouseVo.setOnlineCount(onlineCount);
                    smartLockHadBindHouseVo.setOfflineCount(offlineCount);
                    smartLockHadBindHouseVo.setLowerPowerCount(lowerPowerCount);
                    FloorVo floorVo = new FloorVo();
                    List<RoomAndPublicZoneVo> roomAndPublicZoneVoList = new ArrayList<>();
                    RoomAndPublicZoneVo roomAndPublicZoneVo = new RoomAndPublicZoneVo();
                    floorVo.setFloorId(000);
                    floorVo.setFloorName("公共区域");
                    roomAndPublicZoneVo.setCommunicationStatus(smartLockHadBindHouseVo.getOutSmartLockVo().getCommunicationStatus());
                    roomAndPublicZoneVo.setLockName(smartLockHadBindHouseVo.getOutSmartLockVo().getLockName());
                    roomAndPublicZoneVo.setPowerRate(smartLockHadBindHouseVo.getOutSmartLockVo().getPowerRate());
                    roomAndPublicZoneVo.setSmartLockId(smartLockHadBindHouseVo.getOutSmartLockVo().getSmartLockId());
                    roomAndPublicZoneVo.setRoomNo("外门锁");
                    roomAndPublicZoneVoList.add(roomAndPublicZoneVo);
                    floorVo.setRoomAndPublicZoneVoList(roomAndPublicZoneVoList);
//                    smartLockHadBindHouseVo.getFloorVoList().add(floorVo);
                    List <FloorVo> floorVoList = smartLockHadBindHouseVo.getFloorVoList();
                    floorVoList.add(0,floorVo);
                    for (FloorVo floorModel:floorVoList){
                        if(floorModel.getFloorId()==000){
                            floorModel.setLockCount(smartLockGatewayDao.getCountOfZoneLock(smartLockHadBindHouseVo.getHomeId()));
                            floorModel.setOnlineCount(smartLockGatewayDao.getCountOfZoneOnlineLock(smartLockHadBindHouseVo.getHomeId()));
                        }else{
                            floorModel.setLockCount(smartLockGatewayDao.getCountOfFloorLock(floorModel.getFloorId()));
                            floorModel.setOnlineCount(smartLockGatewayDao.getCountOfOnlineFloorLock(floorModel.getFloorId()));
                        }
                    }

                    smartLockHadBindHouseVo.setOutSmartLockVo(null);
                }
                return smartLockHadBindHouseVoList;
            }
        }
        return null;
    }

    @Override
    public void uninstallSmartLockGateway(String uuid) {
        smartLockGatewayDao.deleteSmartLockGateway(uuid);
    }

    @Override
    public void installSmartLockGateway(String homeId, String uuid) {
        
    }
}
