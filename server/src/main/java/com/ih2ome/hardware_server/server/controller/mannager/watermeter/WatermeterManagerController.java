package com.ih2ome.hardware_server.server.controller.mannager.watermeter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeterRecord;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.hardware_service.service.service.WatermeterManagerService;
import com.ih2ome.hardware_service.service.vo.*;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 水表WebController
 */
@RestController
@RequestMapping("/watermeter/manager")
@CrossOrigin
public class WatermeterManagerController extends BaseController{

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WatermeterManagerService watermeterManagerService;
    @Autowired
    private SynchronousHomeService synchronousHomeService;

    /**
     * 水表list
     * @url /watermeter/manager/watermeterlist
     * @param apiRequestVO
     * <pre>
     *       watermeterWebListVo
     *              watermeterId 水表id
     *              apartmentName 公寓名称
     *              apartmentType 公寓类型
     *              authUserName 用户名(房东手机号)
     *              provinceName 省名
     *              cityName 市名
     *              districtName 区名
     *              areaName 小区名
     *              houseAddress 房源地址
     *              roomNo 房间编号
     *              customerName 租客姓名
     *              customerPhone 租客电话
     *              deviceName 设备号（水表序列号）
     *              communicationStatus 通讯状态
     *              updatedAt  更新时间
     *              amount 水表读数
     *              type 集中式或分散式
     *              gatewayUuid 水表网关
     * </pre>
     * @return result
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
        String uuid = resData.getString("uuid");
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
    @RequestMapping(value="/synchronoushousing/findhome",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindHomes(@RequestBody ApiRequestVO apiRequestVO) {
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        SynchronousHomeWebVo synchronousHomeWebVo = resData.getObject("synchronousHomeWebVo",SynchronousHomeWebVo.class);
        List<SynchronousHomeWebVo> synchronousHomeWebVoList = watermeterManagerService.findHomeSynchronousStatus(synchronousHomeWebVo);
        JSONObject responseJson = new JSONObject();
        responseJson.put("synchronousHomeWebVoList",synchronousHomeWebVoList);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 同步房源查询房间同步状态
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/synchronoushousing/findroom",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousingFindRooms(@RequestBody ApiRequestVO apiRequestVO) {
        //同步房源查询房间同步状态
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        int homeId = dt.getIntValue("homeId");
        String type = dt.getString("type");

        //房间同步状态
        List<HmRoomSyncVO> hmRoomSyncVOList = watermeterManagerService.findRoomSynchronousStatus(homeId,type);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(hmRoomSyncVOList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("hmRoomSyncVOList",jsonArray);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 同步房源
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/synchronoushousing",method = RequestMethod.POST,produces = {"application/json"})
    public String synchronousHousing(@RequestBody ApiRequestVO apiRequestVO) {
        //同步房源查询房间同步状态
        JSONObject dt = apiRequestVO.getDataRequestBodyVO().getDt();
        JSONArray jsonArray = dt.getJSONArray("homeAndRooms");
        String type = dt.getString("type");
        List<HomeAndRoomSyncVO> homeAndRoomSyncVOList=new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            String json = jsonArray.get(i).toString();
            HomeAndRoomSyncVO homeAndRoomSyncVO = JSONObject.parseObject(json, HomeAndRoomSyncVO.class);
            //房间同步
            HomeAndRoomSyncVO homeAndRoom= null;
            try {
                homeAndRoom = watermeterManagerService.synchronousHomeAndRoom(homeAndRoomSyncVO,type);
            } catch (ClassNotFoundException e) {
                Log.error(e.getMessage(),e);
                String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
                return res;
            } catch (WatermeterException e) {
                Log.error(e.getMessage(),e);
                String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
                return res;
            } catch (InstantiationException e) {
                Log.error(e.getMessage(),e);
                String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
                return res;
            } catch (IllegalAccessException e) {
                Log.error(e.getMessage(),e);
                String res = structureErrorResponse(ApiErrorCodeEnum.Service_request_geshi,new Date().toString(),"同步失败"+e.getMessage());
                return res;
            }
            homeAndRoomSyncVOList.add(homeAndRoom);
        }

        JSONArray jsonArray1 = JSONArray.parseArray(JSON.toJSONString(homeAndRoomSyncVOList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("hmRoomSyncVOList",jsonArray1);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

}
