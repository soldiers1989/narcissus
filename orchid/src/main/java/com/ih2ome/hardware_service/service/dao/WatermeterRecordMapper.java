package com.ih2ome.hardware_service.service.dao;


import com.ih2ome.common.base.MyMapper;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeterRecord;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface WatermeterRecordMapper extends MyMapper<SmartWatermeterRecord> {

    /**
     * 更新抄表记录
     * @param smartWatermeterRecord
     */
    void updataWatermeterRecord(SmartWatermeterRecord smartWatermeterRecord);

    /**
     * 添加抄表记录
     * @param smartWatermeterRecord
     */
    void insertWatermeterRecord(SmartWatermeterRecord smartWatermeterRecord);

    Timestamp selectWatermeterMeterUpdatedAtByWatermeterId(Integer watermeterid);
}
