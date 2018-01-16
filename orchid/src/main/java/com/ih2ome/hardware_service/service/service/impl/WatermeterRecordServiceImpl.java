package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.hardware_service.service.dao.WatermeterRecordMapper;
import com.ih2ome.hardware_service.service.entity.narcissus.SmartWatermeterRecord;
import com.ih2ome.hardware_service.service.service.WatermeterRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

@Service
public class WatermeterRecordServiceImpl implements WatermeterRecordService {

    @Resource
    private WatermeterRecordMapper watermeterRecordMapper;

    private static final Logger Log = LoggerFactory.getLogger(WatermeterRecordServiceImpl.class);

    /**
     * 更新抄表记录
     * @param smartWatermeterRecord
     */
    @Override
    public void updataWatermeterRecord(SmartWatermeterRecord smartWatermeterRecord) {
        Log.info("更新抄表记录，smartWatermeterRecord：{}",smartWatermeterRecord.toString());
        watermeterRecordMapper.updataWatermeterRecord(smartWatermeterRecord);
    }

    /**
     * 添加抄表记录
     * @param smartWatermeterRecord
     */
    @Override
    public void addWatermeterRecord(SmartWatermeterRecord smartWatermeterRecord) {
        Log.info("添加抄表记录，smartWatermeterRecord：{}",smartWatermeterRecord.toString());
        watermeterRecordMapper.insertWatermeterRecord(smartWatermeterRecord);
    }

    @Override
    public Timestamp findWatermeterMeterUpdatedAtByWatermeterId(Integer watermeterid) {
        return watermeterRecordMapper.selectWatermeterMeterUpdatedAtByWatermeterId(watermeterid);
    }
}