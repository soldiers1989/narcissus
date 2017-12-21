package com.ih2ome.hardware_server.server.controller.mannager.hardware;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.LockManagerService;
import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import com.ih2ome.hardware_service.service.vo.LockManagerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/20
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/mannager/smartLock")
@CrossOrigin
public class SmartLockController extends BaseController{

    @Autowired
    private LockManagerService lockManagerService;
    /**
     * 门锁list
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/lockList",method = RequestMethod.POST,produces = {"application/json"})
    public String ammeterList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData=apiRequestVO.getDataRequestBodyVO().getDt();
        LockManagerVo lockManagerVo=resData.getObject("lockManagerVo", LockManagerVo.class);
        List<LockManagerVo> lockManagerVoList=lockManagerService.lockList(lockManagerVo);
        PageInfo<LockManagerVo> pageInfo = new PageInfo<>(lockManagerVoList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("lockMannagerVoList",pageInfo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

}
