package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.hardware_service.service.vo.LockListVo;
import com.ih2ome.hardware_service.service.vo.SmartDoorLockGatewayVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Repository
public interface SmartLockGatewayDao {
    List<SmartDoorLockGatewayVO> findConcentratGateway(SmartDoorLockGatewayVO smartDoorLockGatewayVO);

    List<SmartDoorLockGatewayVO> findDispersedGateway(SmartDoorLockGatewayVO smartDoorLockGatewayVO);

    SmartDoorLockGatewayVO getSmartDispersedDoorLockGatewayVOById(String id);

    SmartDoorLockGatewayVO getConcentratSmartDoorLockGatewayVOById(String id);

    List<LockListVo> getDispersedSmartDoorLockByGatewayId(String id);

    List<LockListVo> getConcentratSmartDoorLockByGatewayId(String id);
}
