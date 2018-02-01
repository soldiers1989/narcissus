package com.ih2ome.hardware_server.server.controller.smartlock;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SmartLockService;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
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
 * @author Sky
 * @create 2018/01/22
 * @email sky.li@ixiaoshuidi.com
 **/
@RestController
@RequestMapping("/smartlock")
@CrossOrigin
public class SmartLockController extends BaseController {
    private final Logger Log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SmartLockService smartLockService;

    /**
     * 关联房源查询房屋信息
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/search/home", method = RequestMethod.POST, produces = {"application/json"})
    public String searchHome(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        //获得用户id
        String userId = dt.getString("userId");
        //判断是集中还是分散
        String type = dt.getString("type");
        //判断是哪个第三方(云丁，果加)
        String factoryType = dt.getString("factoryType");
        Map<String, List<HomeVO>> results = null;
        try {
            results = smartLockService.searchHome(userId, type, factoryType);
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
     * 房间取消关联
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/association/cancel", method = RequestMethod.POST, produces = {"application/json"})
    public String cancelAssociation(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        SmartHouseMappingVO smartHouseMappingVO = JSONObject.parseObject(dt.toString(), SmartHouseMappingVO.class);
        try {
            smartLockService.cancelAssociation(smartHouseMappingVO);
        } catch (SmartLockException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "取消失败");
            return result;
        }
        String result = structureSuccessResponseVO(null, new Date().toString(), "取消成功");
        return result;
    }

    /**
     * 房间关联
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/association/confirm", method = RequestMethod.POST, produces = {"application/json"})
    public String confirmAssociation(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        SmartHouseMappingVO smartHouseMappingVO = JSONObject.parseObject(dt.toString(), SmartHouseMappingVO.class);
        try {
            smartLockService.confirmAssociation(smartHouseMappingVO);
        } catch (SmartLockException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "关联失败");
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
        String result = structureSuccessResponseVO(null, new Date().toString(), "关联成功");
        return result;
    }

}