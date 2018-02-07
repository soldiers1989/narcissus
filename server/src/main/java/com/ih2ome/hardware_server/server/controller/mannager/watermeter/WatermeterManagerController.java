package com.ih2ome.hardware_server.server.controller.mannager.watermeter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_server.server.controller.mannager.watermeter.help.ExcelHelp;
import com.ih2ome.hardware_service.service.service.SynchronousHomeService;
import com.ih2ome.hardware_service.service.service.WatermeterManagerService;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.vo.pageVo.watermeter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        PageInfo<WatermeterRecordManagerVO> pageInfo = new PageInfo<>(watermeterRecordManagerVOList);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(watermeterRecordManagerVOList));
        JSONObject responseJson = new JSONObject();
        responseJson.put("watermeterRecordManagerVOList",pageInfo);
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
        ExceptionWebVO exceptionVO = resData.getObject("exceptionVO", ExceptionWebVO.class);

        List<ExceptionWebVO> exceptionVOS= watermeterManagerService.findWatermeterException(exceptionVO);
        PageInfo<ExceptionWebVO> pageInfo = new PageInfo<>(exceptionVOS);
        JSONObject responseJson = new JSONObject();
        responseJson.put("exceptionVOS",pageInfo);
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
        ExceptionWebVO exceptionVO = resData.getObject("exceptionVO", ExceptionWebVO.class);
        List<ExceptionWebVO> exceptionVOS= watermeterManagerService.findGatewayException(exceptionVO);
        PageInfo<ExceptionWebVO> pageInfo = new PageInfo<>(exceptionVOS);
        JSONObject responseJson = new JSONObject();
        responseJson.put("exceptionVOS",pageInfo);
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
        //未同步完的房源
        List<SynchronousHomeWebVo> outofsyncHomeList =new ArrayList<>();
        //已同步的房源
        List<SynchronousHomeWebVo> synchronizedHomeList =new ArrayList<>();
        for (int i=0;i<synchronousHomeWebVoList.size();i++) {
            List<HmRoomSyncVO> hmRoomSyncVOList = watermeterManagerService.selectHmRoomIsAllSynchronous(Integer.parseInt(synchronousHomeWebVoList.get(i).getHomeId()), 1, synchronousHomeWebVoList.get(i).getType());
            if (hmRoomSyncVOList.size() > 0) {
                SynchronousHomeWebVo synchronousHome = new SynchronousHomeWebVo();
                synchronousHome.setHomeId(synchronousHomeWebVoList.get(i).getHomeId());
                synchronousHome.setHomeName(synchronousHomeWebVoList.get(i).getHomeName());
                synchronousHome.setSynchronous(synchronousHomeWebVoList.get(i).getSynchronous());
                synchronousHome.setType(synchronousHomeWebVoList.get(i).getType());
                synchronizedHomeList.add(synchronousHome);
            }

            List<HmRoomSyncVO> hmRoomSyncVOList2 = watermeterManagerService.selectHmRoomIsAllSynchronous(Integer.parseInt(synchronousHomeWebVoList.get(i).getHomeId()),0,synchronousHomeWebVoList.get(i).getType());
            if (hmRoomSyncVOList2.size()>0){
                SynchronousHomeWebVo synchronousHome2 = new SynchronousHomeWebVo();
                synchronousHome2.setHomeId(synchronousHomeWebVoList.get(i).getHomeId());
                synchronousHome2.setHomeName(synchronousHomeWebVoList.get(i).getHomeName());
                synchronousHome2.setSynchronous(synchronousHomeWebVoList.get(i).getSynchronous());
                synchronousHome2.setType(synchronousHomeWebVoList.get(i).getType());
                outofsyncHomeList.add(synchronousHome2);
            }
        }
        JSONObject responseJson = new JSONObject();
        responseJson.put("outofsyncHomeList",outofsyncHomeList);
        responseJson.put("synchronizedHomeList",synchronizedHomeList);
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
        int synchronous = dt.getIntValue("synchronous");
        String type = dt.getString("type");

        //房间同步状态
        List<HmRoomSyncVO> hmRoomSyncVOList = watermeterManagerService.findRoomSynchronousStatus(homeId,synchronous,type);

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


    /**
     * 水表抄表记录导出excel
     * @param req
     * @return
     */
    @RequestMapping(value="/smartwatermeterrecords/export",method = RequestMethod.GET)
    public void exportWaterRecordList(HttpServletRequest req, HttpServletResponse res,String smartWatermeterId,String startTime,String endTime) throws IOException {
        //获取参数
        WatermeterRecordManagerVO watermeterRecordManagerVO=new WatermeterRecordManagerVO();
        watermeterRecordManagerVO.setSmartWatermeterId(smartWatermeterId);
        watermeterRecordManagerVO.setStartTime(startTime);
        watermeterRecordManagerVO.setEndTime(endTime);
        //通过水表id查询水表读数列表
        List<WatermeterRecordManagerVO> watermeterRecordManagerVOList= watermeterManagerService.findWatermeterRecordByWatermeterIdAndTime2(watermeterRecordManagerVO);
        List<Map<String,Object>> list=ExcelHelp.createExcelRecord(watermeterRecordManagerVOList);
        String columnNames[]={"水表id", "抄表时间", "读数", "当日用水量"};//列名
        String keys[] = {"smartWatermeterId", "createdAt", "deviceAmount", "dayAmount"};//map中的key
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatTime = sdf.format(d);
        String fileName="水表抄表记录表-"+formatTime;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelHelp.createWorkBook(list,keys,columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        res.reset();
        res.setContentType("application/vnd.ms-excel;charset=utf-8");
        res.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = res.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }





}
