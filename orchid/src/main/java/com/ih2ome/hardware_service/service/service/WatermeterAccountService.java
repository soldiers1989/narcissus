package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.hardware_service.service.model.narcissus.WatermeterPaymentRecord;

import java.util.List;

/**
 * 水表缴费记录service
 *
 * @auther Administrator young
 * @create 2018/2/28
 */
public interface WatermeterAccountService {

    List<SmartWatermeter> getWaterByRoomId(Integer roomId, Integer type);

    List<WatermeterPaymentRecord> findPaymentAmountByRoomId(Integer roomId, Integer type);

    Boolean createWatermeterPaymentRecord(WatermeterPaymentRecord watermeterPaymentRecord);
}
