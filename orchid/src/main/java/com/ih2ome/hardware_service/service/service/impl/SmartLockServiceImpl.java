package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.dao.WatermeterMapper;
import com.ih2ome.hardware_service.service.service.SmartLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Sky
 * @create 2018/01/22
 * @email sky.li@ixiaoshuidi.com
 **/
@Service
public class SmartLockServiceImpl implements SmartLockService {
    private static final Logger Log = LoggerFactory.getLogger(SmartLockServiceImpl.class);

    @Resource
    private SmartLockDao smartLockDao;
}
