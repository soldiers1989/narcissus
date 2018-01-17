package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockWarningVO;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/2
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public interface SmartLockWarningDao {

    List<SmartLockWarningVO> findDispersedSmartLockWarningList(SmartLockWarningVO smartLockWarningVO);

    List<SmartLockWarningVO> findConcentratSmartLockWarningList(SmartLockWarningVO smartLockWarningVO);
}
