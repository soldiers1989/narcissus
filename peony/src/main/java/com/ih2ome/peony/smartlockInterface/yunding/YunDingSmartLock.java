package com.ih2ome.peony.smartlockInterface.yunding;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.yunding.util.YunDingSmartLockUtil;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.GatewayInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingDeviceInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingHomeInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingLockPasswordVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingRoomInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sky
 * @create 2018/01/11
 * @email sky.li@ixiaoshuidi.com
 **/
public class YunDingSmartLock implements ISmartLock {
    private static final String BASE_URL = "https://lockapi.dding.net/openapi/v1";
    private static final Logger Log = LoggerFactory.getLogger(YunDingSmartLock.class);

    @Override
    public LockVO getLockInfo(String lockNo) throws SmartLockException {
        return null;
    }

    @Override
    public GatewayInfoVO getGateWayInfo(String gateNo) throws SmartLockException {
        return null;
    }

    //新增密码
    @Override
    public String addLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException {
        YunDingLockPasswordVO yunDingLockPasswordVO = YunDingLockPasswordVO.fromH2ome(lockPassword);
        Log.info("添加门锁密码,密码信息:{}", yunDingLockPasswordVO);
        String url = BASE_URL + "/add_password";
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("access_token", YunDingSmartLockUtil.getToken());
        pwdJson.put("uuid", yunDingLockPasswordVO.getUuid());
        pwdJson.put("phonenumber", yunDingLockPasswordVO.getPhonenumber());
        pwdJson.put("is_default", yunDingLockPasswordVO.getIsDefault());
        pwdJson.put("password", yunDingLockPasswordVO.getPassword());
        pwdJson.put("permission_begin", yunDingLockPasswordVO.getPermissionBegin());
        pwdJson.put("permission_end", yunDingLockPasswordVO.getPermissionEnd());
        String result = HttpClientUtil.doPost(url, pwdJson);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("第三方json格式解析错误", e);
            throw new SmartLockException("第三方json格式解析错误" + e.getMessage());
        }
        String code = resJson.get("ErrNo").toString();
        if (!code.equals("0")) {
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n", msg);
            throw new SmartLockException("第三方请求失败/n" + msg);
        }
        return result;
    }

    //修改密码
    @Override
    public String updateLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException {
        YunDingLockPasswordVO yunDingLockPasswordVO = YunDingLockPasswordVO.fromH2ome(lockPassword);
        Log.info("修改门锁密码,密码信息:{}", yunDingLockPasswordVO);
        String url = BASE_URL + "/update_password";
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("access_token", YunDingSmartLockUtil.getToken());
        pwdJson.put("uuid", yunDingLockPasswordVO.getUuid());
        pwdJson.put("password_id", yunDingLockPasswordVO.getPasswordId());
        pwdJson.put("password", yunDingLockPasswordVO.getPassword());
        pwdJson.put("phonenumber", yunDingLockPasswordVO.getPhonenumber());
        pwdJson.put("permission_begin", yunDingLockPasswordVO.getPermissionBegin());
        pwdJson.put("permission_end", yunDingLockPasswordVO.getPermissionEnd());
        String result = HttpClientUtil.doPost(url, pwdJson);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("第三方json格式解析错误", e);
            throw new SmartLockException("第三方json格式解析错误" + e.getMessage());
        }
        String code = resJson.get("ErrNo").toString();
        if (!code.equals("0")) {
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n", msg);
            throw new SmartLockException("第三方请求失败/n" + msg);
        }
        return result;
    }

    //删除密码
    @Override
    public String deleteLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException {
        YunDingLockPasswordVO yunDingLockPasswordVO = YunDingLockPasswordVO.fromH2ome(lockPassword);
        Log.info("删除门锁密码,门锁uuid:{},密码id:{}", yunDingLockPasswordVO.getUuid(), yunDingLockPasswordVO.getPasswordId());
        String url = BASE_URL + "/delete_password";
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("access_token", YunDingSmartLockUtil.getToken());
        pwdJson.put("uuid", yunDingLockPasswordVO.getUuid());
        pwdJson.put("password_id", yunDingLockPasswordVO.getPasswordId());
        String result = HttpClientUtil.doPost(url, pwdJson);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("第三方json格式解析错误", e);
            throw new SmartLockException("第三方json格式解析错误" + e.getMessage());
        }
        String code = resJson.get("ErrNo").toString();
        if (!code.equals("0")) {
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n", msg);
            throw new SmartLockException("第三方请求失败/n" + msg);
        }
        return result;
    }

    //冻结密码
    @Override
    public String frozenLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException {
        YunDingLockPasswordVO yunDingLockPasswordVO = YunDingLockPasswordVO.fromH2ome(lockPassword);
        Log.info("冻结门锁密码,门锁uuid:{},密码id:{}", yunDingLockPasswordVO.getUuid(), yunDingLockPasswordVO.getPasswordId());
        String url = BASE_URL + "/frozen_password";
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("access_token", YunDingSmartLockUtil.getToken());
        pwdJson.put("uuid", yunDingLockPasswordVO.getUuid());
        pwdJson.put("password_id", yunDingLockPasswordVO.getPasswordId());
        String result = HttpClientUtil.doPost(url, pwdJson);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("第三方json格式解析错误", e);
            throw new SmartLockException("第三方json格式解析错误" + e.getMessage());
        }
        String code = resJson.get("ErrNo").toString();
        if (!code.equals("0")) {
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n", msg);
            throw new SmartLockException("第三方请求失败/n" + msg);
        }
        return result;
    }

    //解冻结密码
    @Override
    public String unfrozenLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException {
        YunDingLockPasswordVO yunDingLockPasswordVO = YunDingLockPasswordVO.fromH2ome(lockPassword);
        Log.info("解冻门锁密码,门锁uuid:{},密码id:{}", yunDingLockPasswordVO.getUuid(), yunDingLockPasswordVO.getPasswordId());
        String url = BASE_URL + "/unfrozen_password";
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("access_token", YunDingSmartLockUtil.getToken());
        pwdJson.put("uuid", yunDingLockPasswordVO.getUuid());
        pwdJson.put("password_id", yunDingLockPasswordVO.getPasswordId());
        String result = HttpClientUtil.doPost(url, pwdJson);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("第三方json格式解析错误", e);
            throw new SmartLockException("第三方json格式解析错误" + e.getMessage());
        }
        String code = resJson.get("ErrNo").toString();
        if (!code.equals("0")) {
            String msg = resJson.get("ErrMsg").toString();
            Log.error("第三方请求失败/n", msg);
            throw new SmartLockException("第三方请求失败/n" + msg);
        }
        return result;
    }

    //查询所有的房源信息和设备信息
    @Override
    public String searchHomeInfo(Map<String, Object> params) throws SmartLockException {
        Log.info("查询该用户所有的房源和设备信息");
        String url = BASE_URL + "/search_home_info";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", params.get("access_token"));
        String result = HttpClientUtil.doGet(url, map);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("第三方json格式解析错误", e);
            throw new SmartLockException("第三方json格式解析错误" + e.getMessage());
        }
        List<YunDingHomeInfoVO> homeList = new ArrayList<YunDingHomeInfoVO>();
        JSONArray homes = resJson.getJSONArray("home_list");
        //遍历所有的房源信息，封装成对象。
        for (Object homeObject : homes) {
            YunDingHomeInfoVO yunDingHomeInfoVO = new YunDingHomeInfoVO();
            JSONObject homeJsonObject = JSONObject.parseObject(homeObject.toString());
            yunDingHomeInfoVO.setUserId(homeJsonObject.getString("user_id"));
            yunDingHomeInfoVO.setHomeId(homeJsonObject.getString("home_id"));
            yunDingHomeInfoVO.setDescription(homeJsonObject.getString("description"));
            yunDingHomeInfoVO.setCountry(homeJsonObject.getString("country"));
            yunDingHomeInfoVO.setProvince(homeJsonObject.getString("province"));
            yunDingHomeInfoVO.setCity(homeJsonObject.getString("city"));
            yunDingHomeInfoVO.setZone(homeJsonObject.getString("zone"));
            yunDingHomeInfoVO.setLocation(homeJsonObject.getString("location"));
            yunDingHomeInfoVO.setBlock(homeJsonObject.getString("block"));
            yunDingHomeInfoVO.setHomeName(homeJsonObject.getString("home_name"));
            yunDingHomeInfoVO.setHomeType(homeJsonObject.getString("home_type"));
            //封装该房屋下的房间
            List<YunDingRoomInfoVO> roomList = new ArrayList<YunDingRoomInfoVO>();
            JSONArray rooms = homeJsonObject.getJSONArray("rooms");
            for (Object roomObject : rooms) {
                YunDingRoomInfoVO yunDingRoomInfoVO = new YunDingRoomInfoVO();
                JSONObject roomJsonObject = JSONObject.parseObject(roomObject.toString());
                yunDingRoomInfoVO.setHomeId(homeJsonObject.getString("home_id"));
                yunDingRoomInfoVO.setRoomId(roomJsonObject.getString("room_id"));
                yunDingRoomInfoVO.setRoomName(roomJsonObject.getString("room_name"));
                yunDingRoomInfoVO.setRoomDescription(roomJsonObject.getString("description"));
                yunDingRoomInfoVO.setSpState(roomJsonObject.getString("sp_state"));
                yunDingRoomInfoVO.setInstallState(roomJsonObject.getString("install_state"));
                roomList.add(yunDingRoomInfoVO);
            }
            //封装该房屋下的设备
            List<YunDingDeviceInfoVO> deviceList = new ArrayList<YunDingDeviceInfoVO>();
            JSONArray devices = homeJsonObject.getJSONArray("devices");
            for (Object deviceObject : devices) {
                YunDingDeviceInfoVO yunDingDeviceInfoVO = new YunDingDeviceInfoVO();
                JSONObject deviceJsonObject = JSONObject.parseObject(deviceObject.toString());
                yunDingDeviceInfoVO.setCenterUuid(deviceJsonObject.getString("center_uuid"));
                yunDingDeviceInfoVO.setDescription(deviceJsonObject.getString("description"));
                yunDingDeviceInfoVO.setManufactory(deviceJsonObject.getString("manufactory"));
                yunDingDeviceInfoVO.setUuid(deviceJsonObject.getString("uuid"));
                yunDingDeviceInfoVO.setRoomId(deviceJsonObject.getString("room_id"));
                yunDingDeviceInfoVO.setSn(deviceJsonObject.getString("sn"));
                yunDingDeviceInfoVO.setType(deviceJsonObject.getString("type"));
                deviceList.add(yunDingDeviceInfoVO);
            }
            yunDingHomeInfoVO.setRooms(roomList);
            yunDingHomeInfoVO.setDevices(deviceList);
            homeList.add(yunDingHomeInfoVO);
        }
        return JSONObject.toJSONString(homeList);
    }

//    public  static void main(String[] args){
//        YunDingSmartLock yunDingSmartLock=new YunDingSmartLock();
//        try {
//            Map<String,Object> params=new HashMap<String,Object>();
//            params.put("access_token","99e54de548a000dcef76ffcae7c5074e25fd67dd50c71ce199bb174b6319c81eefdf09fc93f73f35e74e4dc2bd9004fb15c4be694e72d7be4230c96af8ea256c");
//            String result = yunDingSmartLock.searchHomeInfo(params);
//            System.out.println(result);
//            List<YunDingHomeInfoVO> yundingLists = JSONObject.parseArray(result, YunDingHomeInfoVO.class);
//            for(YunDingHomeInfoVO yunDingHomeInfoVO:yundingLists){
//                System.out.println(yunDingHomeInfoVO.getHomeName());
//                System.out.println(yunDingHomeInfoVO.getHomeId());
//            }
//        } catch (SmartLockException e) {
//            e.printStackTrace();
//        }
//    }
}
