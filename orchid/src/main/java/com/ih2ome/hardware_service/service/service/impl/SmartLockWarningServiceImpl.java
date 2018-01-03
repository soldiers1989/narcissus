package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.SmartLockWarningDao;
import com.ih2ome.hardware_service.service.enums.HouseStyleEnum;
import com.ih2ome.hardware_service.service.service.SmartLockWarningService;
import com.ih2ome.hardware_service.service.vo.SmartLockWarningVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Override
    public List<SmartLockWarningVO> getSmartLockWarningList(SmartLockWarningVO smartLockWarningVO) {
        if(smartLockWarningVO.getPage()!= null && smartLockWarningVO.getRows() != null){
            PageHelper.startPage(smartLockWarningVO.getPage(),smartLockWarningVO.getRows());
        }
        if(smartLockWarningVO.getType().equals(HouseStyleEnum.DISPERSED.getCode())){
            return smartLockWarningDao.findDispersedSmartLockWarningList(smartLockWarningVO);
        }else if(smartLockWarningVO.getType().equals(HouseStyleEnum.CONCENTRAT.getCode())){
            return smartLockWarningDao.findConcentratSmartLockWarningList(smartLockWarningVO);
        }else{
            return null;
        }
    }
}
