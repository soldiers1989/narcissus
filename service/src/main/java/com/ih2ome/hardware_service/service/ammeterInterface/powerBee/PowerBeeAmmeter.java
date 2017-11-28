package com.ih2ome.hardware_service.service.ammeterInterface.powerBee;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.hardware_service.service.ammeterInterface.IAmmeter;
import com.ih2ome.hardware_service.service.ammeterInterface.exception.AmmeterException;
import com.ih2ome.hardware_service.service.ammeterInterface.powerBee.util.PowerBeeAmmeterUtil;
import com.ih2ome.hardware_service.service.ammeterInterface.vo.AmmeterInfoVo;
import com.ih2ome.hardware_service.service.enums.PAY_MOD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/27
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class PowerBeeAmmeter implements IAmmeter {

    private static final String BASE_URL = "http://smartammeter.zg118.com:8001";
    private static final String TOKEN_KEY = "POWER_BEE_AMMETER_TOKEN";
    private static final String UID_KEY = "POWER_BEE_AMMETER_UID";
    private static final Logger Log = LoggerFactory.getLogger(PowerBeeAmmeter.class);

    @Override
    public void switchAmmeter(String devId, String onOrOff) throws AmmeterException {
        Log.info("电表通断电");
        Log.info("电表id"+devId);
        Log.info("开关"+onOrOff);
        String uri = null;
        if(onOrOff.equals("on")){
            uri = BASE_URL+"/device/switchon/"+devId;
        }else if(onOrOff.equals("off")){
            uri = BASE_URL+"/device/switchoff/"+devId;
        }else{
            Log.error("参数错误！！！");
            throw new AmmeterException("参数错误！！！");
        }
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPutUrl(url,null);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new AmmeterException("json格式解析错误"+e.getMessage());
        }
        String code = resJson.get("Code").toString();
        if(!code.equals("0")){
            String msg = resJson.get("Message").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new AmmeterException("第三方请求失败/n"+msg);
        }

    }

    @Override
    public void updatePayMod(String devId, PAY_MOD payMod) throws AmmeterException {
        Log.info("付费模式更改");
        Log.info("电表id"+devId+"/n付费模式"+payMod.getName());
        String uri = BASE_URL+"/device/ammeter/paymode/"+devId+"/"+payMod.getCode();
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPutUrl(url,null);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new AmmeterException("json格式解析错误"+e.getMessage());
        }
        String code = resJson.get("Code").toString();
        if(!code.equals("0")){
            String msg = resJson.get("Message").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new AmmeterException("第三方请求失败/n"+msg);
        }

    }

    @Override
    public void setElectricityPrice(String devId, Double value) throws AmmeterException {
        Log.info("修改电表单价");
        Log.info("电表id"+devId+"/n电表单价"+value);
        String uri = BASE_URL+"/device/ammeter/price/"+devId+"/"+value;
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPutUrl(url,null);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new AmmeterException("json格式解析错误"+e.getMessage());
        }
        String code = resJson.get("Code").toString();
        if(!code.equals("0")){
            String msg = resJson.get("Message").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new AmmeterException("第三方请求失败/n"+msg);
        }

    }

    @Override
    public AmmeterInfoVo getAmmeterInfo(String devId) {
        return null;
    }
}
