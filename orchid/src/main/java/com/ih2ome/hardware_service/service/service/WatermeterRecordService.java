package com.ih2ome.hardware_service.service.service;


import com.ih2ome.sunflower.entity.narcissus.SmartWatermeterRecord;

import java.sql.Timestamp;

public interface WatermeterRecordService {
    /**
     * 添加抄表记录
     * @param smartWatermeterRecord
     */
    void addWatermeterRecord(SmartWatermeterRecord smartWatermeterRecord);
}
