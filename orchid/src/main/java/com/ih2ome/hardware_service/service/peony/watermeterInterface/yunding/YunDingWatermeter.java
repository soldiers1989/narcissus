package com.ih2ome.hardware_service.service.peony.watermeterInterface.yunding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.hardware_service.service.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.hardware_service.service.peony.smartlockInterface.yunding.util.YunDingSmartLockUtil;
import com.ih2ome.hardware_service.service.peony.watermeterInterface.IWatermeter;
import com.ih2ome.hardware_service.service.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.AddHomeVo;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.AddRoomVO;
import com.ih2ome.hardware_service.service.peony.watermeterInterface.yunding.utils.YunDingWatermeterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YunDingWatermeter implements IWatermeter {

    private static final String TOKEN_KEY = "YUN_DING_WATERMETER_TOKEN";
    private static final String UID_KEY = "YUN_DING_WATERMETER_UID";
    private static final String EXPRIES_TIME = "YUN_DING_WATERMETER_EXPRIES_TIME";
    private static final String BASE_URL = "https://lockapi.dding.net/openapi/v1";
//    private static final String BASE_URL = "http://dev-lockapi.dding.net:8090/openspi/v1";
//    private static final String BASE_URL = "http://yundinghometest.dding.net:8088/openapi/v1";
    private static final Logger Log = LoggerFactory.getLogger(YunDingWatermeter.class);

    /**
     * 查询单个房屋的状态
     * @param home_id
     * @return
     * @throws WatermeterException
     */
    @Override
    public String findHomeState(String home_id, String userId) throws WatermeterException {
        Log.info("查询单个房屋的状态，房源home_id：{}",home_id);
        JSONObject json= new JSONObject();
        json.put("access_token",YunDingWatermeterUtil.getToken(userId));
        json.put("home_id",home_id);

        String uri = BASE_URL+"/find_home_state";
        String res = HttpClientUtil.doGet(uri,json);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }
        if (resJson != null) {
            String code = resJson.get("ErrNo").toString();
            if (!code.equals("0")) {
                String msg = resJson.get("ErrMsg").toString();
                Log.error("查询房源状态",msg);
            }
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
    public String findHomeStates(String[] home_id, String userId) throws WatermeterException {
        Log.info("查询多个房屋的状态,房源home_id:{}",home_id);

        JSONObject json= new JSONObject();
        json.put("access_token",YunDingWatermeterUtil.getToken(userId));
        json.put("home_id",home_id);

        String uri = BASE_URL+"/find_home_states";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPostSSL(uri,(Object) json);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }
        //如果没查到返回空
        if (res == null){
            return null;
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败,{}",msg);
            throw new WatermeterException("第三方请求失败"+msg);
        }
        return res;
    }

    /**
     * 添加房源
     * @param home
     */
    @Override
    public String addHome(AddHomeVo home, String userId) throws WatermeterException {
        Log.info("添加房源,房源信息home:{}",home);
        home.setAccess_token(YunDingWatermeterUtil.getToken(userId));

        String json = JSONObject.toJSONString(home);

        String uri = BASE_URL+"/add_home";
        String res = HttpClientUtil.doPost(uri,json);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败",msg);
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
    public String readWatermeter(String uuid, String manufactory, String userId) throws WatermeterException {
        Log.info("发送抄表命令,水表uuid:{},水表供应商manufactory:{}",uuid,manufactory);
        Map<String,Object> map= new HashMap();
        map.put("access_token",YunDingWatermeterUtil.getToken(userId));
        map.put("uuid",uuid);
        map.put("manufactory",manufactory);

        String uri = BASE_URL + "/read_watermeter";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(uri,map);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败:{}",msg);
            return "第三方请求失败"+msg;
        }
        return "success";
    }

    /**
     * 获取抄表状态
     * @param uuid
     * @param manufactory
     * @return
     * @throws WatermeterException
     */
    @Override
    public String readWatermeterStatus(String uuid, String manufactory, String userId) throws WatermeterException {
        Log.info("获取抄表状态,水表uuid:{},水表供应商manufactory:{}",uuid,manufactory);
        Map<String,Object> map= new HashMap();
        try {
            map.put("access_token", YunDingSmartLockUtil.getAccessToken(userId));
        }
        catch (Exception ex) {
            Log.error("access_token error", ex);
        }
        map.put("uuid",uuid);
        map.put("manufactory",manufactory);

        String uri = BASE_URL + "/read_watermeter_status";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(uri,map);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败",msg);
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
    public String addRoom(String home_id, String room_id, String room_name, String rooom_description, String userId) throws WatermeterException {
        Log.info("给指定公寓添加房间,home_id:{},room_id:{},room_name:{},rooom_description:{}",home_id,room_id,room_name,rooom_description);

        JSONObject json=new JSONObject();
        json.put("access_token",YunDingWatermeterUtil.getToken(userId));
        json.put("home_id",home_id);
        json.put("room_id",room_id);
        json.put("room_name",room_name);
        json.put("rooom_description",rooom_description);
        String uri = BASE_URL + "/add_room";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPost(uri,json.toJSONString());

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败",msg);
            //throw new WatermeterException("第三方请求失败/n"+msg);
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
    public String addRooms(String home_id, List<AddRoomVO> rooms, String userId) throws WatermeterException {
        Log.info("给指定公寓添加多个房间,home_id:{},rooms:{}",home_id,rooms);

        JSONObject json =new JSONObject();
        json.put("access_token",YunDingWatermeterUtil.getToken(userId));
        json.put("home_id",home_id);
        json.put("rooms", JSONArray.parseArray(JSON.toJSONString(rooms)));


        String uri = BASE_URL + "/add_rooms";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doPost(uri,json.toJSONString());

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败",msg);
           // throw new WatermeterException("第三方请求失败/n"+msg);
        }
        return res;
    }

    /**
     * 获取设备历史异常记录
     * @param uuid
     * @param offset
     * @param count
     * @param start_time
     * @param end_time
     * @return
     * @throws WatermeterException
     */
    @Override
    public String deviceFetchExceptions(String uuid, int offset, int count, int start_time, int end_time, String userId) throws WatermeterException {
        Log.info("获取设备历史异常记录,uuid:{},offset:{},count:{},start_time:{},end_time:{}",uuid,offset,count,start_time,end_time);

        Map<String,Object> map= new HashMap();
        map.put("access_token",YunDingWatermeterUtil.getToken(userId));
        map.put("uuid",uuid);
        map.put("offset",offset);
        map.put("count",count);
        map.put("start_time",start_time);
        map.put("end_time",end_time);

        String uri = BASE_URL + "/device_fetch_exception";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(uri,map);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败{}",msg);
            throw new WatermeterException("第三方请求失败"+msg);
        }
        return res;
    }

    /**
     * 获取水表网关信息
     * @param uuid
     * @return
     * @throws WatermeterException
     */
    @Override
    public String getWaterGatewayInfo(String uuid,String userId) throws WatermeterException {
        Log.info("获取水表网关信息,uuid:{}",uuid);

        Map<String,Object> map= new HashMap();
        try {
            map.put("access_token", YunDingSmartLockUtil.getAccessToken(userId));
        } catch (SmartLockException e) {
            e.printStackTrace();
        }
        map.put("uuid",uuid);

        String uri = BASE_URL + "/get_water_gateway_info";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(uri,map);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败"+msg);
            throw new WatermeterException("第三方请求失败"+msg);
        }
        return res;
    }

    /**
     * 获取水表信息
     * @param uuid
     * @param manufactory
     * @return
     * @throws WatermeterException
     */
    @Override
    public String getWatermeterInfo(String uuid, String manufactory,String userId) throws WatermeterException {
        Log.info("获取水表信息,uuid:{},manufactory:{}",uuid,manufactory);

        Map<String,Object> map= new HashMap();
        try {
            map.put("access_token",YunDingSmartLockUtil.getAccessToken(userId));
        } catch (SmartLockException e) {
            e.printStackTrace();
        }
        map.put("uuid",uuid);
        map.put("manufactory",manufactory);

        String uri = BASE_URL + "/get_watermeter_info";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(uri,map);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败"+msg);
            throw new WatermeterException("第三方请求失败"+msg);
        }
        return res;
    }

    /**
     * 获取抄表历史
     * @param uuid
     * @param manufactory
     * @param room_id
     * @param type
     * @param count
     * @param offset
     * @param begin
     * @param end
     * @return
     * @throws WatermeterException
     */
    @Override
    public String getMeterRecord(String uuid, String manufactory, String room_id, int type, int count, int offset, int begin, int end, String userId) throws WatermeterException {
        Log.info("获取抄表历史,uuid:{},manufactory:{},room_id:{},type:{},count:{},offset:{},begin:{},end:{}",uuid,manufactory,room_id,type,room_id,type,count,offset,begin,end);

        Map<String,Object> map= new HashMap();
        map.put("access_token",YunDingWatermeterUtil.getToken(userId));
        map.put("uuid",uuid);
        map.put("manufactory",manufactory);
        map.put("room_id",room_id);
        map.put("type",type);
        map.put("count",count);
        map.put("offset",offset);
        map.put("begin",begin);
        map.put("end",end);

        String uri = BASE_URL + "/get_meter_record";
        //String url = PowerBeeAmmeterUtil.generateParam(uri);
        String res = HttpClientUtil.doGet(uri,map);

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败"+msg);
            throw new WatermeterException("第三方请求失败"+msg);
        }
        return res;
    }

    /**
     * 云丁doGet请求
     * @param uri
     * @param map
     * @return
     * @throws WatermeterException
     */
    @Override
    public String yunDingDoGetUrl(String uri,Map<String,Object> map, String userId) throws WatermeterException{
        Log.info("云丁doGet请求：{}，参数map：{}",uri,map);
        String url = BASE_URL + uri;
        map.put("access_token",YunDingWatermeterUtil.getToken(userId));
        String res = HttpClientUtil.doGet(url,map);

        if(res == null){
            return null;
        }

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败"+msg);
            throw new WatermeterException("第三方请求失败/n"+msg);
        }
        return res;
    }

    /**
     * 云丁doPost请求
     * @param uri
     * @param map
     * @return
     * @throws WatermeterException
     */
    @Override
    public String yunDingDoPostUrl(String uri,Map<String,Object> map, String userId) throws WatermeterException{
        Log.info("云丁doPost请求：{}，参数map：{}",uri,map);
        String url = BASE_URL + uri;
        map.put("access_token",YunDingWatermeterUtil.getToken(userId));
        JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(map));
        String json = jsonArray.toString();
        String res = HttpClientUtil.doPost(url,json);

        if(res == null){
            return null;
        }

        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(res);
        }catch (Exception e){
            Log.error("第三方json格式解析错误",e);
            throw new WatermeterException("第三方json格式解析错误"+e.getMessage());
        }

        String code = resJson.get("ErrNo").toString();
        if(!code.equals("0")){
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败"+msg);
            throw new WatermeterException("第三方请求失败"+msg);
        }
        return res;
    }

}
