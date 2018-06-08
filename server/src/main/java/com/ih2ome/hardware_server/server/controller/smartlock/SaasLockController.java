package com.ih2ome.hardware_server.server.controller.smartlock;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.hardware_service.service.service.SaasSmartLockService;
import com.ih2ome.sunflower.model.backup.SaasSmartLock;
import com.ih2ome.sunflower.vo.pageVo.Ammeter;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockDetailVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.WatermeterDetailVO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.ih2ome.common.base.BaseController.structureSuccessResponseVO;

@RestController
@RequestMapping("/saas/smartlock")
@CrossOrigin
public class SaasLockController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SaasLockController.class);

    @Autowired
    private SaasSmartLockService saasSmartLockService;

    /**
     * 根据roomid获取门锁信息
     * @param userId
     * @param type
     * @param roomId
     * @return
     */
    @GetMapping(value = "/search/lock")
    public String getSmartLock( @RequestParam  String userId,
                                @RequestParam  String type,
                                @RequestParam  String roomId) {
        SaasSmartLock smartLock=saasSmartLockService.getSmartLock(userId,type, roomId);
        JSONObject jsonObject = new JSONObject();
        if(smartLock!=null){
            jsonObject.put("smartCode",smartLock.getSn());
            jsonObject.put("serial_id",smartLock.getSmart_lock_ID());
        }
        String result = structureSuccessResponseVO(jsonObject, new Date().toString(), "查询成功");
        return result;
    }

    /**
     * 获取账户下门锁数
     * @param userId
     * @param type
     * @return
     */
    @GetMapping(value = "/search/lockcount")
    public String existAmmeter( @RequestParam  String userId,
                                @RequestParam  String type) {
        SaasSmartLock count=saasSmartLockService.getSmartLockCount(userId,type);
        JSONObject jsonObject = new JSONObject();
        if(count!=null){
            jsonObject.put("count",count.getCount());
            jsonObject.put("gatway",count.getGatWay());
        }
        String result =structureSuccessResponseVO(jsonObject,new Date().toString(),"查询成功");
        return result;
    }
    @GetMapping(value = "/search/information")
    public String getSmartInformation(@RequestParam  String type,
                                        @RequestParam  String roomId){
        SmartLockDetailVO smartLockDetailVO=saasSmartLockService.getInformation(type,roomId);
        List<WatermeterDetailVO> watermeterDetailVO=saasSmartLockService.getWatermeter(type,roomId);
        Ammeter Ammeter=saasSmartLockService.getAmmeter(type,roomId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("SmartLockDetailVO",smartLockDetailVO);
        jsonObject.put("watermeterDetailVO",watermeterDetailVO);
        jsonObject.put("Ammeter",Ammeter);
        String result =structureSuccessResponseVO(jsonObject,new Date().toString(),"查询成功");
        return result;
    }
}
