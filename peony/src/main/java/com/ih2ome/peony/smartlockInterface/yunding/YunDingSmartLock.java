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
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.YunDingPullHomeCountEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.*;
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

    /**
     * 新增密码
     *
     * @param lockPassword
     * @return
     * @throws SmartLockException
     * @throws ParseException
     */
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

    /**
     * 修改密码
     *
     * @param lockPassword
     * @return
     * @throws SmartLockException
     * @throws ParseException
     */
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

    /**
     * 删除密码
     *
     * @param lockPassword
     * @return
     * @throws SmartLockException
     * @throws ParseException
     */
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

    /**
     * 冻结密码
     *
     * @param lockPassword
     * @return
     * @throws SmartLockException
     * @throws ParseException
     */
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

    /**
     * 解冻结密码
     *
     * @param lockPassword
     * @return
     * @throws SmartLockException
     * @throws ParseException
     */
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

    /**
     * 查询所有的房源信息和设备信息
     *
     * @param params
     * @return
     * @throws SmartLockException
     */
    @Override
    public String searchHomeInfo(Map<String, Object> params) throws SmartLockException {
        Log.info("查询该用户所有的房源和设备信息");
        String url = BASE_URL + "/search_home_info";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", params.get("access_token"));
        map.put("count", YunDingPullHomeCountEnum.ONE_THOUSAND.getCount());
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

    /**
     * 根据网关的uuid查询网关信息
     *
     * @param params
     * @return
     * @throws SmartLockException
     */
    @Override
    public String searchGataWayInfo(Map<String, Object> params) throws SmartLockException {
        Log.info("根据网关uuid查询网关信息,uuid:{}", params.get("uuid"));
        String url = BASE_URL + "/get_center_info";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", params.get("access_token"));
        map.put("uuid", params.get("uuid"));
        String result = HttpClientUtil.doGet(url, map);
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
            Log.error("第三方请求失败", msg);
            throw new SmartLockException("第三方请求失败/n" + msg);
        }
        YunDingGataWayInfoVO yunDingGataWayInfoVO = YunDingGataWayInfoVO.jsonToObject(resJson);
        return JSONObject.toJSONString(yunDingGataWayInfoVO);
    }

    /**
     * 根据门锁的uuid查询门锁信息
     *
     * @param params
     * @return
     * @throws SmartLockException
     */
    @Override
    public String searchLockInfo(Map<String, Object> params) throws SmartLockException {
        Log.info("根据门锁uuid查询门锁信息,uuid:{}", params.get("uuid"));
        String url = BASE_URL + "/get_lock_info";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", params.get("access_token"));
        map.put("uuid", params.get("uuid"));
        String result = HttpClientUtil.doGet(url, map);
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
            Log.error("第三方请求失败", msg);
            throw new SmartLockException("第三方请求失败/n" + msg);
        }
        YunDingLockInfoVO yunDingLockInfoVO = YunDingLockInfoVO.jsonToObject(resJson);
        return JSONObject.toJSONString(yunDingLockInfoVO);
    }

}
