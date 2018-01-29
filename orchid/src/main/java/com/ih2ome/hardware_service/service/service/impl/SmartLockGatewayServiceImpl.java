package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.hardware_service.service.dao.SmartLockGatewayDao;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayService;
import com.ih2ome.sunflower.model.house.SmartLockGatewayModel;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayHadBindRoomVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayHadBindVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<SmartLockGatewayModel> getSmartLockGatewayList(String homeId) {
        if(StringUtils.isNotBlank(homeId)){
            return smartLockGatewayDao.getGatewayModelByHomeId(homeId);

        }
        return null;

    }

    @Override
    public SmartLockGatewayHadBindVO getSmartLockHadBindGateway(String gatewayId) {
        if(StringUtils.isNotBlank(gatewayId)){
            SmartLockGatewayHadBindVO smartLockGatewayHadBindVO = smartLockGatewayDao.getSmartLockHadBindGateway(gatewayId);
            List <SmartLockGatewayHadBindRoomVO> smartLockGatewayHadBindRoomVOList = smartLockGatewayDao.getSmartLockAndRoomListByGatewayId(gatewayId);
            smartLockGatewayHadBindVO.setSmartLockGatewayHadBindRoomVOList(smartLockGatewayHadBindRoomVOList);
            return smartLockGatewayHadBindVO;

        }
        return null;

    }
}
