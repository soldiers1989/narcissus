package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeterRecord;

public interface WatermeterRecordService {
    /**
     * 更新抄表记录bysmartWatermeterRecord
     * @param smartWatermeterRecord
     */
    void updataWatermeterRecord(SmartWatermeterRecord smartWatermeterRecord);

    /**
     * 添加抄表记录
     * @param smartWatermeterRecord
     */
    void addWatermeterRecord(SmartWatermeterRecord smartWatermeterRecord);
}
