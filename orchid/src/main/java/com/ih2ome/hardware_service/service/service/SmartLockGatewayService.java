package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.vo.SmartDoorLockGatewayVO;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public interface SmartLockGatewayService {
    List<SmartDoorLockGatewayVO> gatewayList(SmartDoorLockGatewayVO smartDoorLockGatewayVO);
}
