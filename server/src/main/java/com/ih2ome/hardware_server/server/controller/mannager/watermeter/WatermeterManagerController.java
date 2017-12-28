package com.ih2ome.hardware_server.server.controller.mannager.watermeter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeterRecord;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.hardware_service.service.service.WatermeterManagerService;
import com.ih2ome.hardware_service.service.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 水表WebController
 */
@RestController
@RequestMapping("/watermeter/manager")
public class WatermeterManagerController extends BaseController{

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WatermeterManagerService watermeterManagerService;
    @Autowired
    private SynchronousHomeService synchronousHomeService;


    /**
     * 水表list
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/watermeterlist",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterWebList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        WatermeterWebListVo watermeterWebListVo = resData.getObject("watermeterWebListVo",WatermeterWebListVo.class);
        List<WatermeterWebListVo> watermeterWebListVoList = watermeterManagerService.watermeterWebListVoList(watermeterWebListVo);
        PageInfo<WatermeterWebListVo> pageInfo = new PageInfo<>(watermeterWebListVoList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterWebListVoList",pageInfo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 水表详情
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/watermeterDetail",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterWebDetail (@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        String uuid = String.valueOf(resData.get("uuid"));
        String type = resData.getString("type");
        //查询水表详情byUuid
        WatermeterManagerDetailVO watermeterManagerDetailVO = watermeterManagerService.findWatermeterDetailByUuid(uuid,type);
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterManagerDetailVO",watermeterManagerDetailVO);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 水表抄表记录时间筛选查询
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/smartwatermeterrecords/filter",method = RequestMethod.POST,produces = {"application/json"})
    public String findTotalWaterFilterList(@RequestBody ApiRequestVO apiRequestVO){
        //获取水表id
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        WatermeterRecordManagerVO watermeterRecordManagerVO = resData.getObject("watermeterRecordManagerVO", WatermeterRecordManagerVO.class);
        //通过水表id查询水表读数列表
        List<WatermeterRecordManagerVO> watermeterRecordManagerVOList= watermeterManagerService.findWatermeterRecordByWatermeterIdAndTime(watermeterRecordManagerVO);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterRecordManagerVOList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterRecordManagerVOList",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 智能水表异常记录
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/exception/watermeter",method = RequestMethod.POST,produces = {"application/json"})
    public String watermeterException(@RequestBody ApiRequestVO apiRequestVO){
        //获取水表id
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        ExceptionVO exceptionVO = resData.getObject("exceptionVO", ExceptionVO.class);

        List<ExceptionVO> exceptionVOS= watermeterManagerService.findWatermeterException(exceptionVO);
        JSONObject responseJson = new JSONObject();
        responseJson.put("exceptionVOS",exceptionVOS);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 网关list
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/gatewaylist",method = RequestMethod.POST,produces = {"application/json"})
    public String gatewayWebList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        GatewayWebListVo gatewayWebListVo = resData.getObject("gatewayWebListVo",GatewayWebListVo.class);
        List<GatewayWebListVo> gatewayWebListVoList = watermeterManagerService.gatewayWebListVoList(gatewayWebListVo);
        PageInfo<GatewayWebListVo> pageInfo = new PageInfo<>(gatewayWebListVoList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("gatewayWebListVoList",pageInfo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 网关详情
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/gatewayDetail",method = RequestMethod.POST,produces = {"application/json"})
    public String gatewayDetail(@RequestBody ApiRequestVO apiRequestVO){
        //通过网关id查询网关详情
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int smartGatewayId = dt.getIntValue("gatewayId");
        String type = dt.getString("type");

        //查询网关详情
        GatewayWebDetailVO gatewayDetailVO = watermeterManagerService.findGatewayDetailbyId(smartGatewayId,type);

        JSONObject responseJson = new JSONObject();
        responseJson.put("gatewayDetailVO",gatewayDetailVO);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 网关异常记录
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/exception/Gateway",method = RequestMethod.POST,produces = {"application/json"})
    public String gatewayException(@RequestBody ApiRequestVO apiRequestVO){
        //获取水表id
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        ExceptionVO exceptionVO = resData.getObject("exceptionVO", ExceptionVO.class);
        List<ExceptionVO> exceptionVOS= watermeterManagerService.findGatewayException(exceptionVO);
        JSONObject responseJson = new JSONObject();
        responseJson.put("exceptionVOS",exceptionVOS);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 同步房源搜索房源
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/jz/synchronous_housing/FindjzHomeIsSynchronoused",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindJZHomesIsSynchronoused(@RequestBody ApiRequestVO apiRequestVO) {
        //获取id
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int userId = (int) dt.get("id");

        List<HomeSyncVO> homeSyncVOS = null;

        homeSyncVOS = synchronousHomeService.findHomeIsSynchronousedByUserId(userId);

        JSONObject responseJson = new JSONObject();
        responseJson.put("homeSyncVOS",homeSyncVOS);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

}
