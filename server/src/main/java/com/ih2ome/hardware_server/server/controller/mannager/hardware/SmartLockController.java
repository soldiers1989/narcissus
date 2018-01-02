package com.ih2ome.hardware_server.server.controller.mannager.hardware;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.LockManagerService;
import com.ih2ome.hardware_service.service.vo.LockInfoVo;
import com.ih2ome.hardware_service.service.vo.LockListVo;
import com.ih2ome.peony.smartlockInterface.vo.LockPasswordVo;
import com.ih2ome.hardware_service.service.vo.LockRequestVo;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.enums.SmartLockFirm;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    private final Logger Log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LockManagerService lockManagerService;

    /**
     * 门锁list
     *
     * @param apiRequestVO <pre>
     *                                                                                                                                                                       lockListVo
     *                                                                                                                                                                           serialNum 门锁编码
     *                                                                                                                                                                           roomNo 房间编号
     *                                                                                                                                                                           apartmentName 公寓名称
     *                                                                                                                                                                           customerName 租客姓名
     *                                                                                                                                                                           authUserName 房东电话
     *                                                                                                                                                                           customerPhone 租客电话
     *                                                                                                                                                                           provinceName 省名
     *                                                                                                                                                                           cityName 市名
     *                                                                                                                                                                           districtName 区名
     *                                                                                                                                                                           areaName 小区名
     *                                                                                                                                                                           status 状态
     *                                                                                                                                                                           type 公寓类型（0集中，1分布）
     *                                                                                                                                                                           page 页码(当前页)
     *                                                                                                                                                                           rows 每页大小
     *                                                                                                                                                                 </pre>
     * @return result
     * <pre>
     *              lockListVo
     *                apartmentName 公寓名称
     *                apartmentType 公寓类型
     *                authUserName 房东电话
     *                customerName 租客姓名
     *                customerPhone 租客电话,
     *                getway 网关编码
     *                houseAddress 房源地址
     *                installTime 安装时间
     *                lockId 门锁ID
     *                providerName 厂商名称
     *                remainingBattery 剩余电量
     *                roomNo 房间编号
     *                serialNum 门锁编码,
     *                status通讯状态
     * </pre>
     * @link /mannager/smartLock/lockList
     */
    @RequestMapping(value = "/lockList", method = RequestMethod.POST, produces = {"application/json"})
    public String lockList(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        LockListVo lockListVo = resData.getObject("lockListVo", LockListVo.class);
        List<LockListVo> lockListVoList = lockManagerService.lockList(lockListVo);
        PageInfo<LockListVo> pageInfo = new PageInfo<>(lockListVoList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("lockListVo", pageInfo);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;
    }

    /**
     * 根据门锁的门锁编码查询门锁的基本信息
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/lockInfo", method = RequestMethod.POST, produces = {"application/json"})
    public String lockInfo(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        String lockNo = resData.getString("lockNo");
        String type = resData.getString("type");
        LockInfoVo lockInfoVo = null;
        try {
            lockInfoVo = lockManagerService.getLockInfoVo(lockNo, type);
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "查询失败");
            return result;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "查询失败");
            return result;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "查询失败");
            return result;
        } catch (SmartLockException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "查询失败");
            return result;
        }
        JSONObject responseJson = new JSONObject();
        responseJson.put("LockInfoVo", lockInfoVo);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;
    }

    /**
     * 根据门锁编码查询门锁密码列表
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/passwordList", method = RequestMethod.POST, produces = {"application/json"})
    public String passwordList(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        LockRequestVo lockRequestVo = JSONObject.parseObject(resData.toString(), LockRequestVo.class);
        JSONObject responseJson = new JSONObject();
        List<LockPasswordVo> pwdList = null;
        pwdList = lockManagerService.getPwdList(lockRequestVo);
        responseJson.put("lockpasswordListVo", pwdList);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;
    }


    /**
     * 新增门锁密码
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/addPassword", method = RequestMethod.POST, produces = {"application/json"})
    public String addPassword(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        LockPasswordVo lockPasswordVo = JSONObject.parseObject(resData.toString(), LockPasswordVo.class);
        try {
            lockManagerService.addPassword(lockPasswordVo);
        } catch (ClassNotFoundException e) {
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
        } catch (SmartLockException e) {
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

    /**
     * 修改门锁密码
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT, produces = {"application/json"})
    public String updatePassword(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        LockPasswordVo lockPasswordVo = JSONObject.parseObject(resData.toString(), LockPasswordVo.class);
        try {
            lockManagerService.updatePassword(lockPasswordVo);
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "修改失败");
            return result;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "修改失败");
            return result;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "修改失败");
            return result;
        } catch (SmartLockException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "修改失败");
            return result;
        } catch (ParseException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "修改失败");
            return result;
        }
        String result = structureSuccessResponseVO(null, new Date().toString(), "修改成功");
        return result;
    }

    /**
     * 删除门锁密码
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/deletePassword", method = RequestMethod.PUT, produces = {"application/json"})
    public String deletePassword(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        LockPasswordVo lockPasswordVo = JSONObject.parseObject(resData.toString(), LockPasswordVo.class);
        try {
            lockManagerService.deletePassword(lockPasswordVo);
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "删除失败");
            return result;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "删除失败");
            return result;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "删除失败");
            return result;
        } catch (SmartLockException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "删除失败");
            return result;
        } catch (ParseException e) {
            Log.error(e.getMessage(), e);
            String result = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi, new Date().toString(), "删除失败");
            return result;
        }
        String result = structureSuccessResponseVO(null, new Date().toString(), "删除成功");
        return result;
    }
}
