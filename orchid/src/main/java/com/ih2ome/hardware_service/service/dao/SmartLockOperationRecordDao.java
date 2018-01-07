package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.hardware_service.service.vo.SmartLockOperationRecordVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/2
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Repository
public interface SmartLockOperationRecordDao {

    List<SmartLockOperationRecordVO> findConcentratSmartLockOperationRecordList(SmartLockOperationRecordVO smartLockOperationRecordVO) ;

    List<SmartLockOperationRecordVO> findDispersedSmartLockOperationRecordList(SmartLockOperationRecordVO smartLockOperationRecordVO);
}
