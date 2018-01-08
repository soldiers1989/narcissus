package com.ih2ome.hardware_server.server.controller.mannager.operation;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SmartLockOperationRecordService;
import com.ih2ome.hardware_service.service.vo.SmartLockOperationRecordVO;
import com.ih2ome.peony.smartlockInterface.enums.GuojiaOperateLogTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/2
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/mannager/smartLockOperationRecord")
@CrossOrigin
public class SmartLockOperationRecordController extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SmartLockOperationRecordService smartLockOperationRecordService;

    @RequestMapping(value="/smartLockOperationRecordList",method = RequestMethod.POST,produces = {"application/json"})
    public String smartLockOperationRecordList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject reqData=apiRequestVO.getDataRequestBodyVO().getDt();
        SmartLockOperationRecordVO smartLockOperationRecordVO = reqData
                .getObject("smartLockOperationRecordVO",SmartLockOperationRecordVO.class);
        List<SmartLockOperationRecordVO> smartLockOperationRecordVOList = smartLockOperationRecordService
                .getSmartLockOperationRecordList(smartLockOperationRecordVO);
        PageInfo <SmartLockOperationRecordVO> pageInfo = new PageInfo<>(smartLockOperationRecordVOList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartLockOperationRecordVOList",pageInfo);
        responseJson.put("GuojiaOperateLogTypeEnum", GuojiaOperateLogTypeEnum.enum2Json());
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

}
