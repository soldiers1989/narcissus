package com.ih2ome.hardware_service.service.service;

import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockOperationRecordVO;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/2
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public interface SmartLockOperationRecordService {
    List<SmartLockOperationRecordVO> getSmartLockOperationRecordList(SmartLockOperationRecordVO smartLockOperationRecordVO);
}
