package com.ih2ome.hardware_server.server.controller.smartlock;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SmartLockService;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.entity.narcissus.SmartLockPassword;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.convert.RedisData;
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

    /**
     * 密码列表
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/password/list", method = RequestMethod.POST, produces = {"application/json"})
    public String passwordManageList(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        //获取门锁Id
        String lockId = dt.getString("serial_id");
        List<SmartLockPassword> passwordList = null;
        try {
            passwordList = smartLockService.findPasswordList(lockId);
        } catch (SmartLockException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "查询失败");
            return result;
        }
        JSONObject responseJson = new JSONObject();
        responseJson.put("passwordList", passwordList);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;
    }

    /**
     * 新增密码
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/password/add", method = RequestMethod.POST, produces = {"application/json"})
    public String addPassword(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        String serial_id = resData.getString("serial_id");
        String password = resData.getString("password");
        String digit_pwd_type = resData.getString("digit_pwd_type");
        String user_name = resData.getString("user_name");
        String mobile = resData.getString("mobile");
        String enable_time = resData.getString("enable_time");
        String disable_time = resData.getString("disable_time");
        String remark = resData.getString("remark");
        LockPasswordVo passwordVo = new LockPasswordVo();
        passwordVo.setSerialNum(serial_id);
        passwordVo.setPassword(password);
        passwordVo.setDigitPwdType(digit_pwd_type);
        passwordVo.setUserName(user_name);
        passwordVo.setMobile(mobile);
        passwordVo.setUserName(user_name);
        passwordVo.setEnableTime(enable_time);
        passwordVo.setDisableTime(disable_time);
        passwordVo.setRemark(remark);
        passwordVo.setUserId(resData.getString("userId"));
        try {
            smartLockService.addLockPassword(passwordVo);
        } catch (SmartLockException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "新增失败");
            return result;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "新增失败");
            return result;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "新增失败");
            return result;
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "新增失败");
            return result;
        } catch (ParseException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "新增失败");
            return result;
        }
        String result = structureSuccessResponseVO(null, new Date().toString(), "新增成功");
        return result;
    }


}