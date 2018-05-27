package com.ih2ome.hardware_server.server.controller.watermeter.controller;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.hardware_service.service.model.narcissus.WatermeterPaymentRecord;
import com.ih2ome.hardware_service.service.service.WatermeterAccountService;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import com.ih2ome.sunflower.vo.pageVo.watermeter.RoomAccountVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Hunter Pan
 * create by 2018/5/25
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/watermeter/account")
@CrossOrigin
public class AccountController  extends BaseController {
    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WatermeterService watermeterService;

    /**
     * 获取水费金额
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/room",method = RequestMethod.POST,produces = {"application/json"})
    public String getRoom(@RequestBody ApiRequestVO apiRequestVO)  {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        Integer roomId = dt.getIntValue("roomId");
        Integer type = dt.getIntValue("type");

        RoomAccountVO roomAccount = watermeterService.getRoomAmount(roomId,type);

        JSONObject responseJson = new JSONObject();
        responseJson.put("room",roomAccount);

        return structureSuccessResponseVO(responseJson,new Date().toString(),"");
    }

    /**
     * 添加真实充值记录
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/recharge/real",method = RequestMethod.POST,produces = {"application/json"})
    public String addRealRecharge(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        Integer roomId = dt.getIntValue("roomId");
        Integer type = dt.getIntValue("type");
        Integer amount = dt.getIntValue("amount");
        String mobile = dt.getString("mobile");

        //改变账户余额
        watermeterService.changeBalance(roomId, type, amount * 100, "online_wechat", "real_recharge", mobile);
        //当前用水量归零
        watermeterService.makeWaterZero(roomId, type);

        JSONObject responseJson = new JSONObject();
        responseJson.put("result", "success");
        return structureSuccessResponseVO(responseJson, new Date().toString(), "");
    }

    /**
     * 虚拟充值
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/recharge/virtual",method = RequestMethod.POST,produces = {"application/json"})
    public String virtualRecharge(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        Integer roomId = dt.getIntValue("roomId");
        Integer type = dt.getIntValue("type");
        Integer amount = dt.getIntValue("amount");
        String payChannel = dt.getString("payChannel");
        String userId = dt.getString("userId");

        //改变账户余额
        watermeterService.changeBalance(roomId, type, amount * 100, "offline_" + payChannel, "virtual_recharge", userId);
        //当前用水量归零
        watermeterService.makeWaterZero(roomId, type);

        JSONObject responseJson = new JSONObject();
        responseJson.put("result", "success");
        return structureSuccessResponseVO(responseJson, new Date().toString(), "");
    }
}
