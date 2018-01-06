package com.ih2ome.hardware_server.server.controller.mannager.hardware;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.LockManagerService;
import com.ih2ome.hardware_service.service.vo.*;
import com.ih2ome.peony.smartlockInterface.vo.LockPasswordVo;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.enums.SmartLockFirm;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${sms.baseUrl}")
    String baseUrl;


    /**
     * 门锁list
     *
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
        String id = resData.getString("id");
        String type = resData.getString("type");
        LockInfoVo lockInfoVo = null;
        try {
            lockInfoVo = lockManagerService.getLockInfoVo(id, type);
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
        PageInfo<LockPasswordVo> pageInfo = new PageInfo<>(pwdList);
        responseJson.put("lockpasswordListVo", pageInfo);
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
            lockManagerService.addPassword(lockPasswordVo, baseUrl);
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
            lockManagerService.updatePassword(lockPasswordVo, baseUrl);
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

    /**
     * 查询门锁的历史状态
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/lockStatusList", method = RequestMethod.POST, produces = {"application/json"})
    public String lockStatusList(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        LockHistoryStatusVO lockHistoryStatusVO = JSONObject.parseObject(resData.toString(), LockHistoryStatusVO.class);
        JSONObject responseJson = new JSONObject();
        List<LockHistoryStatusVO> lockStatusList = null;
        lockStatusList = lockManagerService.getLockHistoryList(lockHistoryStatusVO);
        PageInfo<LockHistoryStatusVO> pageInfo = new PageInfo<>(lockStatusList);
        responseJson.put("lockStatusListVo", pageInfo);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;
    }

    /**
     * 查询门锁的操作记录
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/lockOperateRecordList", method = RequestMethod.POST, produces = {"application/json"})
    public String lockOperateRecord(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        LockOperateRecordVO lockOperateRecordVO = JSONObject.parseObject(resData.toString(), LockOperateRecordVO.class);
        JSONObject responseJson = new JSONObject();
        List<LockOperateRecordVO> lockOperateRecords = null;
        lockOperateRecords = lockManagerService.getLockOperateRecords(lockOperateRecordVO);
        PageInfo<LockOperateRecordVO> pageInfo = new PageInfo<>(lockOperateRecords);
        responseJson.put("lockOperateRecords", pageInfo);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;
    }

    /**
     * 查询门锁的开门记录
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/lockOpenRecordList", method = RequestMethod.POST, produces = {"application/json"})
    public String lockOpenRecord(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        LockOpenRecordVO lockOpenRecord = JSONObject.parseObject(resData.toString(), LockOpenRecordVO.class);
        JSONObject responseJson = new JSONObject();
        List<LockOpenRecordVO> lockOpenRecords = null;
        lockOpenRecords = lockManagerService.getLockOpenRecords(lockOpenRecord);
        PageInfo<LockOpenRecordVO> pageInfo = new PageInfo<>(lockOpenRecords);
        responseJson.put("lockOpenRecords", pageInfo);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;
    }

    /**
     * 发送短信
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST, produces = {"application/json"})
    public String sendMessage(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        LockRequestVo params = JSONObject.parseObject(resData.toString(), LockRequestVo.class);
        Boolean bool = lockManagerService.sendMessage(params, baseUrl);
        if (bool) {
            String result = structureSuccessResponseVO(null, new Date().toString(), "发送短信成功");
            return result;
        } else {
            String result = structureSuccessResponseVO(null, new Date().toString(), "发送短信失败");
            return result;
        }

    }

    /**
     * 获取短信内容
     *
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value = "/getMessage", method = RequestMethod.POST, produces = {"application/json"})
    public String getMessage(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        LockRequestVo params = JSONObject.parseObject(resData.toString(), LockRequestVo.class);
        String messageContent = lockManagerService.getMessage(params);
        JSONObject responseJson = new JSONObject();
        responseJson.put("messageContent", messageContent);
        String result = structureSuccessResponseVO(responseJson, new Date().toString(), "");
        return result;
    }
}
