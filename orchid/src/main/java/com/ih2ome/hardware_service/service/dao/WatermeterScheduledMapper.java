package com.ih2ome.hardware_service.service.dao;


import com.ih2ome.common.base.MyMapper;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeter;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/12/10.
 */
@Repository
public interface WatermeterScheduledMapper extends MyMapper<SmartWatermeter> {

    List<String> selectAllHomeIds();
}
