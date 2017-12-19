package com.ih2ome.peony.ammeterInterface.powerBee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.peony.ammeterInterface.IAmmeter;
import com.ih2ome.peony.ammeterInterface.enums.PayMod;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.ammeterInterface.powerBee.util.PowerBeeAmmeterUtil;
import com.ih2ome.peony.ammeterInterface.vo.AmmeterInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

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
        //1 表示通电  0表示断电
        if(onOrOff.equals("1")){
            uri = BASE_URL+"/device/switchon/"+devId;
        }else if(onOrOff.equals("0")){
            uri = BASE_URL+"/device/switchoff/"+devId;
        }else{
            Log.error("参数错误！！！");
            throw new AmmeterException("参数错误！！！");
        }
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPutUrl(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
    public void updatePayMod(String devId, PayMod payMod) throws AmmeterException {
        Log.info("付费模式更改");
        Log.info("电表id"+devId+"/n付费模式"+payMod.getName());
        String uri = BASE_URL+"/device/ammeter/paymode/"+devId+"/"+payMod.getCode();
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPutUrl(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
        String res = HttpClientUtil.doPutUrl(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
    public AmmeterInfoVo getAmmeterInfo(String devId) throws AmmeterException {
        Log.info("获取电表信息");
        Log.info("电表id:"+devId);
        AmmeterInfoVo ammeterInfoVo = new AmmeterInfoVo();
        String uri = BASE_URL+"/device/ammeter/"+devId;
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
        JSONObject jsonObject = (JSONObject) resJson.getJSONArray("Data").get(0);
        ammeterInfoVo.setElectrifyStatus(jsonObject.getString("Value"));
        ammeterInfoVo.setOnline(jsonObject.getString("Status"));
        ammeterInfoVo.setPowerRate(jsonObject.getDouble("Price"));
        JSONObject expand = jsonObject.getJSONObject("Expand");
        ammeterInfoVo.setUuid(devId);
        ammeterInfoVo.setAllPower(expand.getDouble("allpower"));
        ammeterInfoVo.setCurrent(expand.getDouble("current"));
        Date lastTime = jsonObject.getDate("Lasttime");
        ammeterInfoVo.setLastTime(DateToString(lastTime));
        ammeterInfoVo.setPowerDay(expand.getDouble("powerday"));
        ammeterInfoVo.setSurplus(expand.getDouble("surplus"));
        ammeterInfoVo.setVoltage(expand.getDouble("voltage"));
        ammeterInfoVo.setPowerMonth(expand.getDouble("powermonth"));
        Boolean isNode = jsonObject.getBoolean("Isnode");
        String cid = jsonObject.getString("Cid");
        //查wifi
        //JSONObject nodeInfo = getDetailedAmmeterInfo(cid,isNode);


        ammeterInfoVo.initPowerOutput();
        return ammeterInfoVo;
    }

    @Override
    public void getAmmeterFlushInfo(String devId) throws AmmeterException {
        Log.info("获取电表最新数据");
        Log.info("电表id:"+devId);
        AmmeterInfoVo ammeterInfoVo = new AmmeterInfoVo();
        String uri = BASE_URL+"/device/ammeter/"+devId;
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPutUrl(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
            Log.error("抄表失败/n"+msg);
            throw new AmmeterException("抄表失败/n"+msg);
        }
    }

    private Integer getMissDeviceNum(Integer hour) throws AmmeterException {
        Log.info("获取离线电表数量");
        String uri = BASE_URL+"/report/offlinehour/count/"+hour;
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
        return resJson.getInteger("Data");
    }

    @Override
    public List<String> getMissDevice(Integer hour) throws AmmeterException {
        Log.info("获取离线电表");
        Integer size = getMissDeviceNum(hour);
        List <String> idList = new ArrayList<>();
        String uri = BASE_URL+"/report/offlinehour/"+hour+"/0/"+size;
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
        JSONArray jsonArray = resJson.getJSONArray("Data");
        for(Object object : jsonArray){
            JSONObject jsonObject = JSONObject.parseObject(object.toString());
            String devId = jsonObject.getString("Uuid");
            idList.add(devId);
        }
        return idList;
    }

    @Override
    public List<String> getOnlineNoDataDevice(Integer hour) throws AmmeterException {
        Log.info("获取长时间无数据上报设备");
        Integer size = getOnlineNoDataDeviceNum(hour);
        List <String> idList = new ArrayList<>();
        String uri = BASE_URL+"/report/onlinenodatahour/"+hour+"/0/"+size;
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
        JSONArray jsonArray = resJson.getJSONArray("Data");
        for(Object object : jsonArray){
            JSONObject jsonObject = JSONObject.parseObject(object.toString());
            String devId = jsonObject.getString("Uuid");
            idList.add(devId);
        }
        return idList;
    }

    @Override
    public List<String> getVacantPowerOn() throws AmmeterException {
        Log.info("获取获取空置未断电设备");
        Integer size = getVacantPowerOnNum();
        List <String> idList = new ArrayList<>();
        String uri = BASE_URL+"/report/vacantpoweron/0/"+size;
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
        JSONArray jsonArray = resJson.getJSONArray("Data");
        for(Object object : jsonArray){
            JSONObject jsonObject = JSONObject.parseObject(object.toString());
            String devId = jsonObject.getString("Uuid");
            idList.add(devId);
        }
        return idList;
    }

    @Override
    public Map<String,List<String>> getNegativeDeviceAndNegativePowerOnDevice() throws AmmeterException {
        Log.info("获取所有设备信息");
        String uri = BASE_URL+"/device/ammeter";
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
        JSONArray jsonArray = resJson.getJSONArray("Data");
        List <String> negativeDeviceList = new ArrayList<>();
        List <String> negativePowerOnDeviceList = new ArrayList<>();
        for(Object o:jsonArray){
            JSONObject jsonObject = JSONObject.parseObject(o.toString());
            JSONObject expand = jsonObject.getJSONObject("Expand");
            Double surplus = expand.getDouble("surplus");
            if(surplus<0){
                negativeDeviceList.add(jsonObject.getString("Uuid"));
                //电量负数且未断电
                if(jsonObject.getString("Value").equals("1")){
                    negativePowerOnDeviceList.add(jsonObject.getString("Uuid"));
                }
            }

        }
        Map<String,List<String>>map = new HashMap<>();
        map.put("negativeDeviceList",negativeDeviceList);
        map.put("negativePowerOnDeviceList",negativePowerOnDeviceList);
        return map;
    }

    private Integer getOnlineNoDataDeviceNum(Integer hour) throws AmmeterException {
        Log.info("获取长时间无数据上报设备数量");
        String uri = BASE_URL+"/report/onlinenodatahour/count/"+hour;
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
        return resJson.getInteger("Data");
    }

    private Integer getVacantPowerOnNum() throws AmmeterException {
        Log.info("获取空置未断电设备数量");
        String uri = BASE_URL+"/report/vacantpoweron/count";
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(url,new HashMap<>() ,PowerBeeAmmeterUtil.getToken());
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
        return resJson.getInteger("Data");
    }

    private  JSONObject getDetailedAmmeterInfo(String cid,Boolean isNode) throws AmmeterException {
        Log.info("获取集中器或节点数据");
        String uri = null;
        if (isNode){
            uri = BASE_URL+"/node/"+cid;
        }else{
            uri = BASE_URL+"/terminal/"+cid;
        }
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(url,new HashMap<>(),PowerBeeAmmeterUtil.getToken());
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
        if(isNode){
            return (JSONObject) resJson.getJSONArray("Data").get(0);
        }else{
            return resJson.getJSONObject("Data");
        }



    }

    private String DateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
