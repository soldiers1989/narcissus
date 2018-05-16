package com.ih2ome.hardware_server.server.controller.smartlock;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.hardware_service.service.service.SaasSmartLockService;
import com.ih2ome.sunflower.model.backup.SaasSmartLock;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.ih2ome.common.base.BaseController.structureSuccessResponseVO;

@RestController
@RequestMapping("/saas/smartlock")
@CrossOrigin
public class SaasLockController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SaasLockController.class);

    @Autowired
    private SaasSmartLockService saasSmartLockService;

    @GetMapping(value = "/search/lock")
    public String getSmartLock( @RequestParam  String userId,
                                @RequestParam  String type,
                                @RequestParam  String roomId) {
        SaasSmartLock smartLock=saasSmartLockService.getSmartLock(userId,type, roomId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("smartCode",smartLock.getSn());
        jsonObject.put("serial_id",smartLock.getSmart_lock_ID());
        String result = structureSuccessResponseVO(jsonObject, new Date().toString(), "查询成功");
        return result;
    }
    @GetMapping(value = "/search/lockcount")
    public String existAmmeter( @RequestParam  String userId,
                                @RequestParam  String type) {
        String count=saasSmartLockService.getSmartLockCount(userId,type);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count",count);
        String result =structureSuccessResponseVO(jsonObject,new Date().toString(),"查询成功");
        return result;
    }
}
