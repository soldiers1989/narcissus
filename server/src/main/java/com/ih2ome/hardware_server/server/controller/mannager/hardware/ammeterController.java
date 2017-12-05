package com.ih2ome.hardware_server.server.controller.mannager.hardware;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ih2ome.common.api.vo.request.ApiRequestVO;
import com.ih2ome.common.base.BaseController;
import com.ih2ome.hardware_service.service.service.AmmeterManagerService;
import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import com.ih2ome.hardware_service.service.vo.DeviceIdAndName;
import com.ih2ome.peony.ammeterInterface.enums.PAY_MOD;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.ammeterInterface.powerBee.util.PowerBeeAmmeterUtil;
import com.ih2ome.peony.ammeterInterface.vo.AmmeterInfoVo;
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
 * create by 2017/11/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@RestController
@RequestMapping("/ammeter")
@CrossOrigin
public class ammeterController extends BaseController {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmmeterManagerService ammeterManagerService;

    /**
     * 分散式房源list
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/dispersedList",method = RequestMethod.POST,produces = {"application/json"})
    public String dispersedList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        AmmeterMannagerVo ammeterMannagerVo = resData.getObject("ammeterMannagerVo",AmmeterMannagerVo.class);
        List<AmmeterMannagerVo> ammeterMannagerVoList = ammeterManagerService.findDispersedAmmeter(ammeterMannagerVo);
        PageInfo <AmmeterMannagerVo> pageInfo = new PageInfo<>(ammeterMannagerVoList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("ammeterMannagerVoList",pageInfo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 集中式房源list
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/concentratedList",method = RequestMethod.POST,produces = {"application/json"})
    public String concentratedList(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        AmmeterMannagerVo ammeterMannagerVo = resData.getObject("ammeterMannagerVo",AmmeterMannagerVo.class);
        List<AmmeterMannagerVo> ammeterMannagerVoList = ammeterManagerService.findConcentratAmmeter(ammeterMannagerVo);
        PageInfo <AmmeterMannagerVo> pageInfo = new PageInfo<>(ammeterMannagerVoList);
        JSONObject responseJson = new JSONObject();
        responseJson.put("ammeterMannagerVoList",pageInfo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 查看电表关系
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/ammeterRelation",method = RequestMethod.POST,produces = {"application/json"})
    public String ammeterRelation(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        String id = resData.getString("id");
        String type = resData.getString("type");
        DeviceIdAndName deviceVo = ammeterManagerService.getAmmeterRelation(id,type);
        JSONObject responseJson = new JSONObject();
        responseJson.put("deviceVo",deviceVo);
        String res = structureSuccessResponseVO(responseJson,new Date().toString(),"");
        return res;
    }

    /**
     * 电表信息查询
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/ammeterInfo",method = RequestMethod.PUT,produces = {"application/json"})
    public String ammeterInfo(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        String id = resData.getString("id");
        String type = resData.getString("type");
        AmmeterInfoVo ammeterInfoVo = ammeterManagerService.getAmmeterInfoVo(id,type);

        return null;
    }

    /**
     * 使用场景变更
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/scenarioUpdate",method = RequestMethod.PUT,produces = {"application/json"})
    public String scenarioUpdate(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        String id = resData.getString("id");
        String wiring = resData.getString("wiring");
        String type = resData.getString("type");
        ammeterManagerService.updateWiring(id,type,wiring);
        String res = structureSuccessResponseVO(null,new Date().toString(),"修改成功");
        return res;
    }

    /**
     * 付费模式变更
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/payMatureUpdate",method = RequestMethod.PUT,produces = {"application/json"})
    public String payMatureUpdate(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        String id = resData.getString("id");
        String payMod = resData.getString("payMod");
        String type = resData.getString("type");
        try {
            ammeterManagerService.updatePayMod(id,type, PAY_MOD.getByCode(Integer.valueOf(payMod)));
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (AmmeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        }
        String res = structureSuccessResponseVO(null,new Date().toString(),"修改成功");
        return res;
    }

    /**
     * 修改电费单价
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/unitPriceOfElectricityUpdate",method = RequestMethod.PUT,produces = {"application/json"})
    public String unitPriceOfElectricityUpdate(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        String id = resData.getString("id");
        String price = resData.getString("price");
        String type = resData.getString("type");
        try {
            ammeterManagerService.updatePrice(id,type,price);
        } catch (AmmeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        }
        String res = structureSuccessResponseVO(null,new Date().toString(),"修改成功");
        return res;
    }

    /**
     * 电表通断电操作
     * @param apiRequestVO
     * @return
     */
    @RequestMapping(value="/operateAmmeter",method = RequestMethod.POST,produces = {"application/json"})
    public String operateAmmeter(@RequestBody ApiRequestVO apiRequestVO){
        JSONObject resData = apiRequestVO.getDataRequestBodyVO().getDt();
        String id = resData.getString("id");
        String operate = resData.getString("operate");
        String type = resData.getString("type");
        try {
            ammeterManagerService.switchDevice(id,operate,type);
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (IllegalAccessException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (InstantiationException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        } catch (AmmeterException e) {
            Log.error(e.getMessage(),e);
            String res = structureSuccessResponseVO(null,new Date().toString(),"修改失败"+e.getMessage());
            return res;
        }
        String res = structureSuccessResponseVO(null,new Date().toString(),"修改成功");
        return res;
    }

    @RequestMapping(value="/test")
    public void test() throws AmmeterException {
        PowerBeeAmmeterUtil.getToken();
    }


}
