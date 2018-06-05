package com.ih2ome.hardware_server.server.controller.watermeter.controller;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.hardware_service.service.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 同步房源Controller
 */
@RestController
@RequestMapping("/watermeter")
@CrossOrigin
public class SynchronousHomeController  extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SynchronousHomeService synchronousHomeService;

    /**
     * 关联房源查询房屋信息
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/serch/water",method = RequestMethod.POST,produces = {"application/json"})
    public String serchWater(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        //获得用户id
        String userId = dt.getString("userId");
        //判断是集中还是分散
        String type = dt.getString("type");
        //判断是哪个第三方(云丁，果加)
        String factoryType = dt.getString("factoryType");
        Map<String, List<HomeVO>> results = null;
        try {
            results= synchronousHomeService.serchWater(userId,type,factoryType);
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "查询失败");
            return result;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "查询失败");
            return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SmartLockException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "查询失败");
            return result;
        }
        JSONObject responseJson = new JSONObject();
        responseJson.put("thirdHomeList", results.get("thirdHomeList"));
        responseJson.put("localHomeList", results.get("localHomeList"));
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;
    }

    /**
     * 关联
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/water/relation",method = RequestMethod.POST,produces = {"application/json"})
    public String confirmAssociation(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        SmartHouseMappingVO smartHouseMappingVO = JSONObject.parseObject(dt.toString(), SmartHouseMappingVO.class);
        String msg;
        try {
            msg=synchronousHomeService.confirmAssociation(smartHouseMappingVO);
        } catch (SmartLockException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "此设备已被关联在其他公寓");
            return result;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "关联失败");
            return result;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "关联失败");
            return result;
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "关联失败");
            return result;
        } catch (ParseException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "关联失败");
            return result;
        }
        String result = structureSuccessResponseVO(null, new Date().toString(), msg);
        return result;
    }

    /**
     * 取消关联
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/cancel/relation",method = RequestMethod.POST,produces = {"application/json"})
    public String cancelRelation(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        SmartHouseMappingVO smartHouseMappingVO = JSONObject.parseObject(dt.toString(), SmartHouseMappingVO.class);
        try {
            synchronousHomeService.cancelRelation(smartHouseMappingVO);
        } catch (SmartLockException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "取消失败");
            return result;
        }
        String result = structureSuccessResponseVO(null, new Date().toString(), "取消成功");
        return result;
    }

}
