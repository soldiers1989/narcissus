package com.ih2ome.watermeter.service.impl;

import com.ih2ome.watermeter.dao.WatermeterScheduledMapper;
import com.ih2ome.watermeter.service.WatermeterScheduledService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class WatermeterScheduledServiceImpl implements WatermeterScheduledService{

    @Resource
    private WatermeterScheduledMapper watermeterScheduledMapper;

    /**
     * 查询home_ids
     * @return
     */
    @Override
    public String[] findAllHomeIds() {
        List<String> list=watermeterScheduledMapper.selectAllHomeIds();
        String[] homeIds = (String[]) list.toArray(new String[list.size()]);
        return homeIds;
    }
}
