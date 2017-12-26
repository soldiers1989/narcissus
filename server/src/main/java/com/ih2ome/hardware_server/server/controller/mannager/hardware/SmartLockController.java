package com.ih2ome.hardware_server.server.controller.mannager.hardware;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.LockManagerService;
import com.ih2ome.hardware_service.service.vo.LockListVo;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.enums.SmartLockFirm;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
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
public class SmartLockController extends BaseController {

    @Autowired
    private LockManagerService lockManagerService;

    /**
     * 门锁list
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/lockList", method = RequestMethod.POST, produces = {"application/json"})
    public String lockList(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        LockListVo lockListVo = resData.getObject("lockListVo", LockListVo.class);
        List<LockListVo> lockListVoList = lockManagerService.lockList(lockListVo);
        PageInfo<LockListVo> pageInfo = new PageInfo<>(lockListVoList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("lockListVo", pageInfo);
        String res = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return res;
    }

    @RequestMapping(value = "/lockInfo", method = RequestMethod.POST, produces = {"application/json"})
    public String lockInfo(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        String lockNo = resData.getString("lockNo");
        String type=resData.getString("type");

        try {
            ISmartLock iSmartLock = (ISmartLock) Class.forName(SmartLockFirm.GUO_JIA.getClazz()).newInstance();
            iSmartLock.getGuoJiaLockInfo(lockNo);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SmartLockException e) {
            e.printStackTrace();
        }
        return null;
    }


}
