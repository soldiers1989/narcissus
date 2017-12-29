package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.SmartLockGatewayDao;
import com.ih2ome.hardware_service.service.enums.HouseStyleEnum;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayService;
import com.ih2ome.hardware_service.service.vo.SmartDoorLockGatewayVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Service
public class SmartLockGatewayServiceImpl implements SmartLockGatewayService {

    @Resource
    SmartLockGatewayDao smartLockGatewayDao;

    @Override
    public List<SmartDoorLockGatewayVO> gatewayList(SmartDoorLockGatewayVO smartDoorLockGatewayVO) {
        if(smartDoorLockGatewayVO.getPage()!= null && smartDoorLockGatewayVO.getRows() != null){
            PageHelper.startPage(smartDoorLockGatewayVO.getPage(),smartDoorLockGatewayVO.getRows());
        }
        if(smartDoorLockGatewayVO.getType().equals(HouseStyleEnum.DISPERSED.getCode())){
            return smartLockGatewayDao.findDispersedAmmeter(smartDoorLockGatewayVO);
        }else if(smartDoorLockGatewayVO.getType().equals(HouseStyleEnum.CONCENTRAT.getCode())){
            return smartLockGatewayDao.findConcentratAmmeter(smartDoorLockGatewayVO);
        }else{
            return null;
        }
    }
}
