package com.ih2ome.hardware_service.service.service;

import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockDetailVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayAndHouseInfoVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayHadBindVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockHadBindHouseVo;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public interface SmartLockGatewayService {
    /**
     * 根据房源查询网关列表
     * @param homeId
     * @param type
     * @return
     */
    List<SmartLockGatewayAndHouseInfoVO> getSmartLockGatewayList(String homeId, String type);

    /**
     * 查询已绑定网关的门锁
     * @param gatewayId
     * @return
     */
    SmartLockGatewayHadBindVO getSmartLockHadBindGateway(String gatewayId);

    /**
     * 查询网关基本信息
     * @param gatewayId
     * @return
     */
    SmartLockDetailVO getSmartLockGatewayDetailInfo(String gatewayId);

    /**
     * 获取已绑定房源列表
     * @param type
     * @param userId
     * @return
     */
    List<SmartLockHadBindHouseVo> getHadBindHouseList(String type, String userId);

    /**
     * 解绑网关
     * @param uuid
     */
    void uninstallSmartLockGateway(String uuid);
}
