package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.service.SaasSmartLockService;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.model.backup.SaasSmartLock;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseStyleEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaasSmartLockServiceImpl implements SaasSmartLockService{

    @Resource
    private SmartLockDao smartLockDao;

    @Override
    public SaasSmartLock getSmartLock(String userId, String type, String roomId) {
        SaasSmartLock smartLock=new SaasSmartLock();
        //判断是集中式
        if(HouseStyleEnum.CONCENTRAT.getCode().equals(type)){
            smartLock=smartLockDao.findSmartLock(roomId);
        }else if(HouseStyleEnum.DISPERSED.getCode().equals(type)){
            smartLock=smartLockDao.findSmartLockCode(roomId);

        }
        return smartLock;
    }

    @Override
    public String getSmartLockCount(String userId, String type) {
        String count=null;
        if(HouseStyleEnum.CONCENTRAT.getCode().equals(type)){
            count=smartLockDao.findSmartLockCount(userId);
        }else if(HouseStyleEnum.DISPERSED.getCode().equals(type)){
            count=smartLockDao.QuerySmartLockCount(userId);
        }

        return count;
    }
}
