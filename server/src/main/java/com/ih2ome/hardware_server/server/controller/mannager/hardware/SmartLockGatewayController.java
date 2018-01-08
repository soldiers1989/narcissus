package com.ih2ome.hardware_server.server.controller.mannager.hardware;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayService;
import com.ih2ome.hardware_service.service.vo.LockListVo;
import com.ih2ome.hardware_service.service.vo.SmartDoorLockGatewayVO;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
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
 * create by 2017/12/21
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/mannager/smartLockGateWay")
@CrossOrigin
public class SmartLockGatewayController extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SmartLockGatewayService smartLockGatewayService;

    /**
     * 网关列表
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/smartLockGateList",method = RequestMethod.POST,produces = {"application/json"})
    public String  smartLockGateList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject reqData=apiRequestVO.getDataRequestBodyVO().getDt();
        SmartDoorLockGatewayVO smartDoorLockGatewayVO = reqData.getObject("smartDoorLockGatewayVO",SmartDoorLockGatewayVO.class);
        List<SmartDoorLockGatewayVO> smartLockGatewayVOList = smartLockGatewayService.gatewayList(smartDoorLockGatewayVO);
        PageInfo <SmartDoorLockGatewayVO> pageInfo = new PageInfo<>(smartLockGatewayVOList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("smartLockGatewayVOList",pageInfo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 网关详情 该网关下门锁列表 嵌套页面  独立做分页
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/smartLockGateBaseInfo",method = RequestMethod.POST,produces = {"application/json"})
    public String smartLockGateBaseInfo(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject reqData=apiRequestVO.getDataRequestBodyVO().getDt();
        String type = reqData.getString("type");
        String id = reqData.getString("id");
        Integer page = reqData.getInteger("page");
        Integer rows = reqData.getInteger("rows");
        SmartDoorLockGatewayVO smartDoorLockGatewayVO = null;
        try {
            smartDoorLockGatewayVO = smartLockGatewayService.getSmartDoorLockGatewayVOById(type,id);
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"查询失败");
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"查询失败");
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"查询失败");
            return res;
        } catch (SmartLockException e) {
            Log.error(e.getMessage(),e);
            String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"查询失败");
            return res;
        }
        List <LockListVo> lockListVoList = smartLockGatewayService.getSmartDoorLockByGatewayId(id,type,page,rows);
        PageInfo <LockListVo> pageInfo = new PageInfo<>(lockListVoList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("lockListVoList",pageInfo);
        responseJson.put("smartDoorLockGatewayVO",smartDoorLockGatewayVO);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }
}
