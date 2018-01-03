package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.vo.SmartLockWarningVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/2
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Service
public interface SmartLockWarningService {
    List<SmartLockWarningVO> getSmartLockWarningList(SmartLockWarningVO smartLockWarningVO);
}
