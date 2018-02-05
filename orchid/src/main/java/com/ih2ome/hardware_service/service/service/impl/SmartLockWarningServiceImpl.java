package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.AmmeterAlarmDao;
import com.ih2ome.hardware_service.service.dao.SmartLockWarningDao;
import com.ih2ome.hardware_service.service.service.SmartLockWarningService;
import com.ih2ome.sunflower.entity.narcissus.SmartMistakeInfo;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseStyleEnum;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockWarningVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/2
 * @Emial Lucius.li@ixiaoshuidi.com
 */

@Service
public class SmartLockWarningServiceImpl implements SmartLockWarningService {

    @Resource
    SmartLockWarningDao smartLockWarningDao;
    @Resource
    AmmeterAlarmDao ammeterAlarmDao;
    @Override
    public List<SmartLockWarningVO> getSmartLockWarningList(SmartLockWarningVO smartLockWarningVO) {
        if(smartLockWarningVO.getPage()!= null && smartLockWarningVO.getRows() != null){
            PageHelper.startPage(smartLockWarningVO.getPage(),smartLockWarningVO.getRows());
        }
        if(smartLockWarningVO.getApartmentType().equals(HouseStyleEnum.DISPERSED.getCode())){
            return smartLockWarningDao.findDispersedSmartLockWarningList(smartLockWarningVO);
        }else if(smartLockWarningVO.getApartmentType().equals(HouseStyleEnum.CONCENTRAT.getCode())){
            return smartLockWarningDao.findConcentratSmartLockWarningList(smartLockWarningVO);
        }else{
            return null;
        }
    }

    @Override
    public void saveSmartLockAlarmInfo(SmartMistakeInfo smartMistakeInfo) {
        List<SmartMistakeInfo> smartMistakeInfoList = new ArrayList<>();
        smartMistakeInfoList.add(smartMistakeInfo);
        ammeterAlarmDao.saveAlarmList(smartMistakeInfoList);
    }
}
