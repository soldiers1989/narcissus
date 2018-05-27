package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.hardware_service.service.dao.WatermeterAccountMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.hardware_service.service.model.narcissus.WatermeterPaymentRecord;
import com.ih2ome.hardware_service.service.service.WatermeterAccountService;
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
public class WatermeterAccountServiceImpl implements WatermeterAccountService {
    private static final Logger Log = LoggerFactory.getLogger(WatermeterAccountService.class);

    @Autowired
    private WatermeterAccountMapper watermeterAccountMapper;

    @Override
    public List<SmartWatermeter> getWaterByRoomId(Integer roomId, Integer type) {
        return watermeterAccountMapper.selectWatermeterByRoomId(roomId, type);
    }

    /**
     * 查询水费金额
     * @param roomId
     * @param type
     * @return
     */
    @Override
    public List<WatermeterPaymentRecord> findPaymentAmountByRoomId(Integer roomId, Integer type) {
        //查询水表读数，水表单价
        List<SmartWatermeter> watermeterList= watermeterAccountMapper.selectWatermeterByRoomId(roomId,type);

        List<WatermeterPaymentRecord> watermeterPaymentRecordList = new ArrayList<>();
        if(!watermeterList.isEmpty() || watermeterList != null){
            for (SmartWatermeter watermeter:watermeterList) {
                Long lastAmount = watermeter.getLastAmount();
                Long price = watermeter.getPrice();
                int smartWatermeterId = watermeter.getSmartWatermeterId();
                //查询上次缴费水表读数
                Long paylastAmouny = watermeterAccountMapper.selectWatermeterLastAmountBySmartWatermeterId(smartWatermeterId);
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

        Integer count = watermeterAccountMapper.insertWatermeterPaymentRecord(watermeterPaymentRecord);
        if(count == 1){
            return true;
        }
        return false;
    }


}
