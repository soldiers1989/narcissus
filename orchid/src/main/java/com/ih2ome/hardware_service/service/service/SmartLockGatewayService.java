package com.ih2ome.hardware_service.service.service;

import com.ih2ome.sunflower.model.house.SmartLockGatewayModel;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayHadBindVO;

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
     * @return
     */
    List<SmartLockGatewayModel> getSmartLockGatewayList(String homeId);

    SmartLockGatewayHadBindVO getSmartLockHadBindGateway(String gatewayId);
}
