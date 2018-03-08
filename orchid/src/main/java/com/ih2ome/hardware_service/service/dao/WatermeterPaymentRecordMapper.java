package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.common.base.MyMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.hardware_service.service.model.narcissus.WatermeterPaymentRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatermeterPaymentRecordMapper extends MyMapper<WatermeterPaymentRecord> {


    List<SmartWatermeter> selectWatermeterByRoomId(@Param("roomId") Integer roomId, @Param("type") Integer type);

    Long selectWatermeterLastAmountBySmartWatermeterId(int smartWatermeterId);

    Integer insertWatermeterPaymentRecord(WatermeterPaymentRecord watermeterPaymentRecord);
}
