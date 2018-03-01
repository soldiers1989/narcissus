package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.hardware_service.service.dao.WatermeterPaymentRecordMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.hardware_service.service.model.narcissus.WatermeterPaymentRecord;
import com.ih2ome.hardware_service.service.service.WatermeterPaymentRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther Administrator young
 * @create 2018/2/28
 */
@Service
public class WatermeterPaymentRecordServiceImpl implements WatermeterPaymentRecordService {
    private static final Logger Log = LoggerFactory.getLogger(WatermeterPaymentRecordService.class);

    @Autowired
    private WatermeterPaymentRecordMapper watermeterPaymentRecordMapper;

    /**
     * 查询水费金额
     * @param roomId
     * @return
     */
    @Override
    public List<WatermeterPaymentRecord> findPaymentAmountByRoomId(Integer roomId) {
        //查询水表读数，水表单价
        List<SmartWatermeter> watermeterList= watermeterPaymentRecordMapper.selectWatermeterByRoomId(roomId);

        List<WatermeterPaymentRecord> watermeterPaymentRecordList = new ArrayList<>();
        if(!watermeterList.isEmpty() || watermeterList != null){
            for (SmartWatermeter watermeter:watermeterList) {
                Long lastAmount = watermeter.getLastAmount();
                Long price = watermeter.getPrice();
                int smartWatermeterId = watermeter.getSmartWatermeterId();
                //查询上次缴费水表读数
                Long paylastAmouny =watermeterPaymentRecordMapper.selectWatermeterLastAmountBySmartWatermeterId(smartWatermeterId);
                if (paylastAmouny == null){
                    paylastAmouny=0L;
                }
                //计算水费
                long amount=lastAmount - paylastAmouny;
                WatermeterPaymentRecord watermeterPaymentRecord = new WatermeterPaymentRecord();
                watermeterPaymentRecord.setSmartWatermeterId(smartWatermeterId);
                watermeterPaymentRecord.setAmount(Math.toIntExact(amount));
                watermeterPaymentRecord.setLastNum(Math.toIntExact(lastAmount));
                watermeterPaymentRecord.setMeterUpdateAt(watermeter.getMeterUpdatedAt());
                watermeterPaymentRecord.setPrice(price);
                watermeterPaymentRecord.setMeterType(watermeter.getMeterType());
                watermeterPaymentRecordList.add(watermeterPaymentRecord);
            }
        }
        //返回数据
        return watermeterPaymentRecordList;
    }

    /**
     * 添加缴费记录
     * @param watermeterPaymentRecord
     * @return
     */
    @Override
    public Boolean createWatermeterPaymentRecord(WatermeterPaymentRecord watermeterPaymentRecord) {

        Integer count = watermeterPaymentRecordMapper.insertWatermeterPaymentRecord(watermeterPaymentRecord);
        if(count == 1){
            return true;
        }
        return false;
    }


}
