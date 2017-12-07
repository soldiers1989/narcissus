package com.ih2ome.peony.watermeterInterface.yunding;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.peony.ammeterInterface.powerBee.util.PowerBeeAmmeterUtil;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.peony.watermeterInterface.vo.AddHomeVo;
import com.ih2ome.peony.watermeterInterface.yunding.utils.YunDingWatermeterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class YunDingWatermeter implements IWatermeter {

    private static final String TOKEN_KEY = "YUN_DING_WATERMETER_TOKEN";
    private static final String UID_KEY = "YUN_DING_WATERMETER_UID";
    private static final String EXPRIES_TIME = "YUN_DING_WATERMETER_EXPRIES_TIME";
    private static final String BASE_URL = "https://lockapi.dding.net/openapi/v1";
    //private static final String BASE_URL = "http://dev-lockapi.dding.net:8090/openspi/v1";
    private static final Logger Log = LoggerFactory.getLogger(YunDingWatermeter.class);

    /**
     * 查询单个房屋的状态
     * @param home_id
     * @return
     * @throws WatermeterException
     */
    @Override
    public String findHomeState(String home_id) throws WatermeterException {
        Log.info("查询房源");
        Log.info("房源id："+home_id);
        JSONObject json= new JSONObject();
        json.put("access_token",YunDingWatermeterUtil.getToken());
        json.put("home_id","001");

        String uri = BASE_URL+"/find_home_state";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPost(uri,(Object) json);
        System.out.println(res);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            //throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new WatermeterException("第三方请求失败/n"+msg);
        }
        return String.valueOf(resJson);
    }

    /**
     * 查询多个房屋的状态
     * @param home_id
     * @return
     * @throws WatermeterException
     */
    @Override
    public String findHomeStates(String[] home_id) throws WatermeterException {
        Log.info("查询多个房屋的状态");
        Log.info("房源id："+home_id);

        JSONObject json= new JSONObject();
        json.put("access_token",YunDingWatermeterUtil.getToken());
        json.put("home_id",home_id);

        String uri = BASE_URL+"/find_home_states";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPostSSL(uri,(Object) json);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new WatermeterException("第三方请求失败/n"+msg);
        }
        return res;
    }

    /**
     * 添加房源
     * @param home
     */
    @Override
    public String addHome(AddHomeVo home) throws WatermeterException {
        Log.info("添加房源");
        Log.info("房源信息："+home.toString());
        home.setAccess_token(YunDingWatermeterUtil.getToken());

        String uri = BASE_URL+"/add_home";
        String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPostSSL(url,home);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new WatermeterException("第三方请求失败/n"+msg);
        }
        return res;
    }

    /**
     * 读取水表抄表
     * @param uuid
     * @param manufactory
     * @return
     * @throws WatermeterException
     */
    @Override
    public String readWatermeter(String uuid, String manufactory) throws WatermeterException {
        Log.info("发送抄表命令");
        Log.info("水表uuid："+uuid+"水表供应商"+manufactory);
        Map<String,Object> map= new HashMap();
        map.put("access_token",YunDingWatermeterUtil.getToken());
        map.put("uuid",uuid);
        map.put("manufactory",manufactory);

        String uri = BASE_URL + "/read_watermeter";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(uri,map);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new WatermeterException("第三方请求失败/n"+msg);
        }
        return res;
    }

    /**
     * 给指定公寓添加房间
     * @param home_id
     * @param room_id
     * @param room_name
     * @param rooom_description
     * @return
     * @throws WatermeterException
     */
    @Override
    public String addRoom(String home_id, String room_id, String room_name, String rooom_description) throws WatermeterException {
        Log.info("给指定公寓添加房间");

        Map<String,Object> map= new HashMap();
        map.put("access_token",YunDingWatermeterUtil.getToken());
        map.put("home_id",home_id);
        map.put("room_id",room_id);
        map.put("room_name",room_name);
        map.put("rooom_description",rooom_description);

        String uri = BASE_URL + "/add_room";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPost(uri,map);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new WatermeterException("第三方请求失败/n"+msg);
        }
        return res;
    }

    /**
     * 给指定公寓添加多个房间
     * @param home_id
     * @param rooms
     * @return
     * @throws WatermeterException
     */
    @Override
    public String addRooms(String home_id, String[] rooms) throws WatermeterException {
        Log.info("给指定公寓添加多个房间");

        Map<String,Object> map= new HashMap();
        map.put("access_token",YunDingWatermeterUtil.getToken());
        map.put("home_id",home_id);
        map.put("rooms",rooms);


        String uri = BASE_URL + "/add_rooms";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPost(uri,map);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new WatermeterException("第三方请求失败/n"+msg);
        }
        return res;
    }

    public String getBaseDoPostUrl(String uri,Map<String,Object> map) throws WatermeterException{
        String url = BASE_URL + uri;
        String res = HttpClientUtil.doPost(url,map);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new WatermeterException("json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n"+msg);
            throw new WatermeterException("第三方请求失败/n"+msg);
        }
        return res;
    }


}
