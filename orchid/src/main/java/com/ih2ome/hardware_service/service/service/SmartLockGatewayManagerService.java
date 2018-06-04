package com.ih2ome.hardware_service.service.service;

import com.ih2ome.sunflower.vo.pageVo.smartLock.LockListVo;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartDoorLockGatewayVO;
import com.ih2ome.hardware_service.service.peony.smartlockInterface.exception.SmartLockException;

import java.text.ParseException;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public interface SmartLockGatewayManagerService {
    List<SmartDoorLockGatewayVO> gatewayList(SmartDoorLockGatewayVO smartDoorLockGatewayVO);

    SmartDoorLockGatewayVO getSmartDoorLockGatewayVOById(String type, String id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException;

    List<LockListVo> getSmartDoorLockByGatewayId(String id, String type, Integer page, Integer rows);
}
