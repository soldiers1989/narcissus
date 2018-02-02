package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.hardware_service.service.dao.SmartLockGatewayDao;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayService;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseStyleEnum;
import com.ih2ome.sunflower.vo.pageVo.smartLock.*;
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
    public List<SmartLockGatewayAndHouseInfoVO> getSmartLockGatewayList(String homeId, String type) {
        if(StringUtils.isNotBlank(homeId)&&StringUtils.isNotBlank(type)){

            if(type.equals(HouseStyleEnum.DISPERSED.getCode())){
                return smartLockGatewayDao.getDispersedGatewayModelByHomeId(homeId);
            }else if(type.equals(HouseStyleEnum.CONCENTRAT.getCode())){
                return smartLockGatewayDao.getConcentrateGatewayModelByHomeId(homeId);
            }else{
                return null;
            }

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

    @Override
    public SmartLockDetailVO getSmartLockGatewayDetailInfo(String gatewayId) {
        if(StringUtils.isNotBlank(gatewayId)){
            SmartLockDetailVO smartLockDetailVO = smartLockGatewayDao.getSmartLockGatewayDetailInfo(gatewayId);
            if(smartLockDetailVO != null){
                smartLockDetailVO.splitVersion();

            }
            return smartLockDetailVO;

        }
        return null;

    }

    @Override
    public List<SmartLockHadBindHouseVo> getHadBindHouseList(String type, String userId) {
        if(StringUtils.isNotBlank(userId)&&StringUtils.isNotBlank(type)){
            if(type.equals(HouseStyleEnum.DISPERSED.getCode())){
                return smartLockGatewayDao.getDispersedHadBindHouseList(userId);
            }else if(type.equals(HouseStyleEnum.CONCENTRAT.getCode())){
                return smartLockGatewayDao.getConcentrateHadBindHouseList(userId);
            }
        }
        return null;
    }
}
