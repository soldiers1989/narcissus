package com.ih2ome.hardware_service.service.dao;

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
    List<SmartDoorLockGatewayVO> findConcentratAmmeter(SmartDoorLockGatewayVO smartDoorLockGatewayVO);

    List<SmartDoorLockGatewayVO> findDispersedAmmeter(SmartDoorLockGatewayVO smartDoorLockGatewayVO);
}
