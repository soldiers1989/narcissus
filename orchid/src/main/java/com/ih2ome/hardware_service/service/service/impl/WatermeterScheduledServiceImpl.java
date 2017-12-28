package com.ih2ome.hardware_service.service.service.impl;


import com.ih2ome.hardware_service.service.dao.WatermeterScheduledMapper;
import com.ih2ome.hardware_service.service.service.WatermeterScheduledService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class WatermeterScheduledServiceImpl implements WatermeterScheduledService {

    @Resource
    private WatermeterScheduledMapper watermeterScheduledMapper;

    private static final Logger Log = LoggerFactory.getLogger(WatermeterScheduledServiceImpl.class);

    /**
     * 查询home_ids
     * @return
     */
    @Override
    public String[] findAllHomeIds() {
        Log.info("查询home_ids");
        List<String> list=watermeterScheduledMapper.selectAllHomeIds();
        String[] homeIds = (String[]) list.toArray(new String[list.size()]);
        return homeIds;
    }
}
