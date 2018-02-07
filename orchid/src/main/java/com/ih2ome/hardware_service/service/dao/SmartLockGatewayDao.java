package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.sunflower.vo.pageVo.smartLock.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Repository
public interface SmartLockGatewayDao {

    /**
     * 根据HomeId获取分散式网关列表
     * @param homeId
     * @return
     */
    List <SmartLockGatewayAndHouseInfoVO> getDispersedGatewayModelByHomeId(String homeId);

    /**
     * 根据HomeId获取集中式网关列表
     * @param homeId
     * @return
     */
    List <SmartLockGatewayAndHouseInfoVO> getConcentrateGatewayModelByHomeId(String homeId);

    /**
     * 根据网关id获取绑定的门锁
     * @param gatewayId
     * @return
     */
    SmartLockGatewayHadBindVO getSmartLockHadBindGateway(String gatewayId);

    /**
     * 根据网关id获取门锁和房间信息s
     * @param gatewayId
     * @return
     */
    List<SmartLockGatewayHadBindRoomVO> getSmartLockAndRoomListByGatewayId(String gatewayId);

    /**
     * 查询门锁详情
     * @param gatewayId
     * @return
     */
    SmartLockDetailVO getSmartLockGatewayDetailInfo(String gatewayId);

    /**
     * 获取分散式已绑定房源列表
     * @param userId
     * @return
     */
    List<SmartLockHadBindHouseVo> getDispersedHadBindHouseList(String userId);

    /**
     * 获取集中式已绑定房源列表
     * @param userId
     * @return
     */
    List<SmartLockHadBindHouseVo> getConcentrateHadBindHouseList(String userId);

    /**
     * 解绑网关
     * @param uuid
     */
    void deleteSmartLockGateway(String uuid);

    long getCountOfApartmentLock(long homeId);

    long getCountOfApartmentOnlineLock(long homeId);

    long getCountOfApartmentOfflineLock(long homeId);

    long getCountOfApartmentLowPowerLock(long homeId);

    long getCountOfFloorLock(long floorId);

    long getCountOfOnlineFloorLock(long floorId);

    long getCountOfZoneLock(long floorId);

    long getCountOfZoneOnlineLock(long floorId);
}
