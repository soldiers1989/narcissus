package com.ih2ome.hardware_server.server.controller.watermeter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.model.narcissus.WatermeterPaymentRecord;
import com.ih2ome.hardware_service.service.service.WatermeterPaymentRecordService;
import com.ih2ome.hardware_service.service.vo.HMWatermeterListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 水表缴费记录controller
 *
 * @auther Administrator young
 * @create 2018/2/27
 */
@RestController
@RequestMapping("/WatermeterPaymentRecord")
@CrossOrigin
public class WatermeterPaymentRecordController extends BaseController {
    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WatermeterPaymentRecordService watermeterPaymentRecordService;

    /**
     * 获取水费金额
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/getPaymentAmount",method = RequestMethod.POST,produces = {"application/json"})
    public String getPaymentAmount(@RequestBody ApiRequestVO apiRequestVO)  {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        Integer roomId = dt.getIntValue("roomId");

        List<WatermeterPaymentRecord> paymentRecordList = watermeterPaymentRecordService.findPaymentAmountByRoomId(roomId);

        return JSONObject.toJSONString(paymentRecordList);
    }

    /**
     * 创建缴费记录
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/creatWatermeterPaymentRecord",method = RequestMethod.POST,produces = {"application/json"})
    public String creatWatermeterPaymentRecord(@RequestBody ApiRequestVO apiRequestVO)  {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        Integer smartWatermeterId = dt.getIntValue("smartWatermeterId");
        Integer lastNum = dt.getIntValue("lastNum");
        Integer amount = dt.getIntValue("amount");
        Long price = dt.getLongValue("price");
        Date meterUpdateAt = dt.getDate("meterUpdateAt");
        Integer meterType = dt.getIntValue("meterType");
        WatermeterPaymentRecord watermeterPaymentRecord =new WatermeterPaymentRecord();
        watermeterPaymentRecord.setSmartWatermeterId(smartWatermeterId);
        watermeterPaymentRecord.setLastNum(lastNum);
        watermeterPaymentRecord.setAmount(amount);
        watermeterPaymentRecord.setPrice(price);
        watermeterPaymentRecord.setMeterUpdateAt(meterUpdateAt);
        watermeterPaymentRecord.setMeterType(meterType);

        Boolean res= watermeterPaymentRecordService.createWatermeterPaymentRecord(watermeterPaymentRecord);
        if (!res){
            return "缴费记录创建失败";
        }

        return "success";
    }
}
