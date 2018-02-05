package com.ih2ome.hardware_service.service.service;

import com.ih2ome.sunflower.entity.narcissus.SmartMistakeInfo;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockWarningVO;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/2
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public interface SmartLockWarningService {

    List<SmartLockWarningVO> getSmartLockWarningList(SmartLockWarningVO smartLockWarningVO);

    void saveSmartLockAlarmInfo(SmartMistakeInfo smartMistakeInfoList);
}
