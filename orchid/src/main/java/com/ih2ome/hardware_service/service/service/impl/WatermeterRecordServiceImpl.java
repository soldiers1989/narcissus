package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.hardware_service.service.dao.WatermeterRecordMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeterRecord;
import com.ih2ome.hardware_service.service.service.WatermeterRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WatermeterRecordServiceImpl implements WatermeterRecordService {

    @Resource
    private WatermeterRecordMapper watermeterRecordMapper;

    /**
     * 更新抄表记录
     * @param smartWatermeterRecord
     */
    @Override
    public void updataWatermeterRecord(SmartWatermeterRecord smartWatermeterRecord) {
        watermeterRecordMapper.updataWatermeterRecord(smartWatermeterRecord);
    }

    @Override
    public void addWatermeterRecord(SmartWatermeterRecord smartWatermeterRecord) {
        watermeterRecordMapper.insertWatermeterRecord(smartWatermeterRecord);
    }
}