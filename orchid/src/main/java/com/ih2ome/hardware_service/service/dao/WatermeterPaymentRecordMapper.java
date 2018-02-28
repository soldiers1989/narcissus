package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.common.base.MyMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeterRecord;
import com.ih2ome.hardware_service.service.model.narcissus.WatermeterPaymentRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatermeterPaymentRecordMapper extends MyMapper<SmartWatermeterRecord> {


    List<SmartWatermeter> selectWatermeterByRoomId(Integer roomId);

    Long selectWatermeterLastAmountBySmartWatermeterId(int smartWatermeterId);

    Integer insertWatermeterPaymentRecord(WatermeterPaymentRecord watermeterPaymentRecord);
}
