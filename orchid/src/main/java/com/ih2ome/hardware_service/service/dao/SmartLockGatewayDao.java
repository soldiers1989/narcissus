package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.sunflower.model.house.SmartLockGatewayModel;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayHadBindRoomVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayHadBindVO;
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
     * 根据HomeId获取网关列表
     * @param homeId
     * @return
     */
    List <SmartLockGatewayModel> getGatewayModelByHomeId(String homeId);

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
}
