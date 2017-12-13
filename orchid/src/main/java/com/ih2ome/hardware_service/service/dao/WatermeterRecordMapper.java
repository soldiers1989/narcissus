package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.common.base.MyMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeterRecord;
import org.springframework.stereotype.Repository;

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
}
