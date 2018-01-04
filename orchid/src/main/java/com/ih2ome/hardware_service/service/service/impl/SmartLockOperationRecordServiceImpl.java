package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.SmartLockOperationRecordDao;
import com.ih2ome.hardware_service.service.enums.HouseStyleEnum;
import com.ih2ome.hardware_service.service.service.SmartLockOperationRecordService;
import com.ih2ome.hardware_service.service.vo.SmartLockOperationRecordVO;
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
public class SmartLockOperationRecordServiceImpl implements SmartLockOperationRecordService {

    @Resource
    SmartLockOperationRecordDao smartLockOperationRecordDao;

    @Override
    public List<SmartLockOperationRecordVO> getSmartLockOperationRecordList(SmartLockOperationRecordVO smartLockOperationRecordVO) {
        if(smartLockOperationRecordVO.getPage()!= null && smartLockOperationRecordVO.getRows() != null){
            PageHelper.startPage(smartLockOperationRecordVO.getPage(),smartLockOperationRecordVO.getRows());
        }
        if(smartLockOperationRecordVO.getApartmentType().equals(HouseStyleEnum.DISPERSED.getCode())){
            return smartLockOperationRecordDao.findDispersedSmartLockOperationRecordList(smartLockOperationRecordVO);
        }else if(smartLockOperationRecordVO.getApartmentType().equals(HouseStyleEnum.CONCENTRAT.getCode())){
            return smartLockOperationRecordDao.findConcentratSmartLockOperationRecordList(smartLockOperationRecordVO);
        }else{
            return null;
        }
    }
}
