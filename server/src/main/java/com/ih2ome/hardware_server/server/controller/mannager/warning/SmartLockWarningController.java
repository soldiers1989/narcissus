package com.ih2ome.hardware_server.server.controller.mannager.warning;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SmartLockWarningService;
import com.ih2ome.hardware_service.service.vo.SmartLockWarningVO;
import com.ih2ome.peony.smartlockInterface.enums.GuoJiaLockStatusEnum;
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
@RequestMapping("/mannager/warning")
@CrossOrigin
public class SmartLockWarningController extends BaseController{

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SmartLockWarningService smartLockWarningService;

    @RequestMapping(value="/smartLockWarningList",method = RequestMethod.POST,produces = {"application/json"})
    public String smartLockWarningList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject reqData=apiRequestVO.getDataRequestBodyVO().getDt();
        SmartLockWarningVO smartLockWarningVO = reqData.getObject("smartLockWarningVO",SmartLockWarningVO.class);
        List<SmartLockWarningVO> smartLockWarningVOList = smartLockWarningService.getSmartLockWarningList(smartLockWarningVO);
        PageInfo <SmartLockWarningVO> pageInfo = new PageInfo<>(smartLockWarningVOList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartLockWarningVOList",pageInfo);
        responseJson.put("guoJiaLockStatusEnum", GuoJiaLockStatusEnum.enum2Json());
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

}
