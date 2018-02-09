package com.ih2ome.peony.smartlockInterface.yunding;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.DateUtils;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.yunding.util.YunDingSmartLockUtil;
import com.ih2ome.sunflower.entity.narcissus.SmartDeviceV2;
import com.ih2ome.sunflower.entity.narcissus.SmartGatewayV2;
import com.ih2ome.sunflower.entity.narcissus.SmartLock;
import com.ih2ome.sunflower.entity.narcissus.SmartLockPassword;
import com.ih2ome.sunflower.vo.pageVo.enums.SmartDeviceEnum;
import com.ih2ome.sunflower.vo.pageVo.enums.SmartDeviceTypeEnum;
import com.ih2ome.sunflower.vo.pageVo.enums.SmartLockPasswordIsDefaultEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.GatewayInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockVO;
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
    private static final String MANAGER_SMART_LOCK_PASSWORD_ID = "999";

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
        Log.info("添加门锁密码,密码信息:{}", lockPassword);
        String url = BASE_URL + "/add_password";
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("access_token", YunDingSmartLockUtil.getAccessToken(lockPassword.getUserId()));
        pwdJson.put("uuid", lockPassword.getUuid());
        pwdJson.put("phonenumber", lockPassword.getMobile());
        pwdJson.put("is_default", lockPassword.getIsDefault());
        pwdJson.put("password", lockPassword.getPassword());
        System.out.println(lockPassword.getIsDefault());
        if (SmartLockPasswordIsDefaultEnum.PASSWORD_ISNOTDEFAULT.getCode().equals(lockPassword.getIsDefault())) {
            pwdJson.put("permission_begin", DateUtils.stringToLong(lockPassword.getEnableTime(), "yyyy-MM-dd HH:mm:ss") / 1000);
            pwdJson.put("permission_end", DateUtils.stringToLong(lockPassword.getDisableTime(), "yyyy-MM-dd HH:mm:ss") / 1000);
        }
        pwdJson.put("name", lockPassword.getName());
        String result = HttpClientUtil.doPost(url, pwdJson);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
            System.out.println(resJson);
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
        Log.info("修改门锁密码,密码id:{},密码内容:{}", lockPassword.getPwdNo(), lockPassword.getPassword());
        String url = BASE_URL + "/update_password";
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("access_token", YunDingSmartLockUtil.getAccessToken(lockPassword.getUserId()));
        pwdJson.put("uuid", lockPassword.getUuid());
        pwdJson.put("password_id", lockPassword.getPwdNo());
        pwdJson.put("password", lockPassword.getPassword());
        pwdJson.put("phonenumber", lockPassword.getMobile());
        pwdJson.put("permission_begin", DateUtils.stringToLong(lockPassword.getEnableTime(), "yyyy-MM-dd HH:mm:ss") / 1000);
        pwdJson.put("permission_end", DateUtils.stringToLong(lockPassword.getDisableTime(), "yyyy-MM-dd HH:mm:ss") / 1000);
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
        Log.info("删除门锁密码,门锁uuid:{},密码id:{}", lockPassword.getUuid(), lockPassword.getPwdNo());
        String url = BASE_URL + "/delete_password";
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("access_token", YunDingSmartLockUtil.getAccessToken(lockPassword.getUserId()));
        pwdJson.put("uuid", lockPassword.getUuid());
        pwdJson.put("password_id", lockPassword.getPwdNo());
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
        Log.info("冻结门锁密码,门锁uuid:{},密码id:{}", lockPassword.getUuid(), lockPassword.getPwdNo());
        String url = BASE_URL + "/frozen_password";
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("access_token", YunDingSmartLockUtil.getAccessToken(lockPassword.getUserId()));
        pwdJson.put("uuid", lockPassword.getUuid());
        pwdJson.put("password_id", lockPassword.getPwdNo());
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
        Log.info("解冻门锁密码,门锁uuid:{},密码id:{}", lockPassword.getUuid(), lockPassword.getPwdNo());
        String url = BASE_URL + "/unfrozen_password";
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("access_token", YunDingSmartLockUtil.getAccessToken(lockPassword.getUserId()));
        pwdJson.put("uuid", lockPassword.getUuid());
        pwdJson.put("password_id", lockPassword.getPwdNo());
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
        Log.info("查询该用户所有的房源和设备信息,用户ID:{}",YunDingSmartLockUtil.getAccessToken(params.get("userId").toString()));
        String url = BASE_URL + "/search_home_info";
        Map<String, Object> map = new HashMap<String, Object>();
        String access_token = YunDingSmartLockUtil.getAccessToken(params.get("userId").toString());
        map.put("access_token", access_token);
        map.put("count", YunDingPullHomeCountEnum.ONE_THOUSAND.getCount());
        String result = HttpClientUtil.doGet(url, map);
        Log.info("RESULT:{}",result);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("第三方json格式解析错误", e);
            throw new SmartLockException("第三方json格式解析错误" + e.getMessage());
        }
        List<YunDingHomeInfoVO> homeList = new ArrayList<YunDingHomeInfoVO>();
        JSONArray homes = resJson.getJSONArray("home_list");
        Log.info("homes:{}",homes);
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
            Log.info("***********{}",rooms);
            for (Object roomObject : rooms) {
                YunDingRoomInfoVO yunDingRoomInfoVO = new YunDingRoomInfoVO();
                JSONObject roomJsonObject = JSONObject.parseObject(roomObject.toString());
                yunDingRoomInfoVO.setHomeId(homeJsonObject.getString("home_id"));
                yunDingRoomInfoVO.setRoomId(roomJsonObject.getString("room_id"));
                yunDingRoomInfoVO.setRoomName(roomJsonObject.getString("room_name"));
                yunDingRoomInfoVO.setRoomDescription(roomJsonObject.getString("description"));
                yunDingRoomInfoVO.setSpState(roomJsonObject.getString("sp_state"));
                yunDingRoomInfoVO.setInstallState(roomJsonObject.getString("install_state"));

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
     * 查询房源设备信息
     *
     * @param userId
     * @param thirdHomeId
     * @return
     * @throws SmartLockException
     */
    @Override
    public Map<String, Object> searchHouseDeviceInfo(String userId, String thirdHomeId) throws SmartLockException, ParseException {
        Log.info("根据第三方homeId查询对应userId下的外门锁和网关设备,userId:{}", userId);
        String gatewayUrl = BASE_URL + "/get_center_info_arr";
        String outDoorLockUrl = BASE_URL + "/get_lock_info";
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", YunDingSmartLockUtil.getAccessToken(userId));
        map.put("home_id", thirdHomeId);

        String gatewayResult = HttpClientUtil.doGet(gatewayUrl, map);
        JSONObject gatewayResponseJSON = JSONObject.parseObject(gatewayResult);

        String lockResult = HttpClientUtil.doGet(outDoorLockUrl, map);
        JSONObject lockResponseJSON = JSONObject.parseObject(lockResult);

        List<SmartGatewayV2> gatewayInfoVOList = new ArrayList<>();
        List<SmartLock> lockVOList = new ArrayList<>();

        //第三方返回的code为0为调用成功
        if ("0".equals(gatewayResponseJSON.getString("ErrNo"))) {
            JSONArray jsonArray = gatewayResponseJSON.getJSONArray("centers");
            for (Object object : jsonArray) {
                JSONObject center = JSONObject.parseObject(object.toString());
                SmartGatewayV2 smartGatewayV2 = new SmartGatewayV2();
                SmartDeviceV2 smartDeviceV2 = new SmartDeviceV2();
                smartGatewayV2.setUuid(center.getString("uuid"));
                smartGatewayV2.setSn(center.getString("sn"));
                smartGatewayV2.setMac(center.getString("mac"));
                smartGatewayV2.setModel(center.getString("model"));
                smartGatewayV2.setModelName(center.getString("model_name"));
                smartGatewayV2.setInstallTime(DateUtils.longToString(center.getLong("bind_time") * 1000L, "yyyy-MM-dd HH:mm:ss"));
                smartGatewayV2.setBrand(center.getString("brand"));
                smartGatewayV2.setVersions(center.getString("versions"));
                smartDeviceV2.setBrand(center.getString("brand"));
                smartDeviceV2.setName(center.getString("name"));
                smartDeviceV2.setConnectionStatus(center.getString("onoff_line"));
                smartDeviceV2.setSmartDeviceType(SmartDeviceEnum.SMART_DEVICE_GATEWAY.getCode());
                smartDeviceV2.setThreeId(center.getString("uuid"));
                smartDeviceV2.setConnectionStatusUpdateTime(DateUtils.longToString(center.getLong("onoff_time") * 1000L, "yyyy-MM-dd HH:mm:ss"));
                smartGatewayV2.setSmartDeviceV2(smartDeviceV2);
                gatewayInfoVOList.add(smartGatewayV2);

            }
        } else {
            Log.error("该房源下网关不存在,homeId:{}", thirdHomeId);
        }

        //第三方返回的code为0为调用成功
        if ("0".equals(lockResponseJSON.getString("ErrNo"))) {
            SmartLock smartLock = new SmartLock();
            SmartDeviceV2 smartDeviceV2 = new SmartDeviceV2();
            smartLock.setSn(lockResponseJSON.getString("sn"));
            smartLock.setMac(lockResponseJSON.getString("mac"));
            smartLock.setModel(lockResponseJSON.getString("model"));
            smartLock.setModelName(lockResponseJSON.getString("model_name"));
            smartLock.setPower(lockResponseJSON.getString("power"));
            smartLock.setPowerRefreshtime(DateUtils.longToString(lockResponseJSON.getLong("power_refreshtime") * 1000L, "yyyy-MM-dd HH:mm:ss"));
            smartLock.setOnoffTime(lockResponseJSON.getString("onoff_line"));
            smartLock.setLqi(lockResponseJSON.getString("lqi"));
            smartLock.setLqiRefreshtime(DateUtils.longToString(lockResponseJSON.getLong("lqi_refreshtime"), "yyyy-MM-dd HH:mm:ss"));
            smartLock.setBindTime(DateUtils.longToString(lockResponseJSON.getLong("bind_time") * 1000L, "yyyy-MM-dd HH:mm:ss"));
            smartLock.setVersions(lockResponseJSON.getString("versions"));
            smartLock.setGatewayUuid(lockResponseJSON.getString("center_uuid"));
            smartDeviceV2.setBrand(lockResponseJSON.getString("brand"));
            smartDeviceV2.setName(lockResponseJSON.getString("name"));
            smartDeviceV2.setThreeId(lockResponseJSON.getString("uuid"));
            smartDeviceV2.setConnectionStatus(lockResponseJSON.getString("onoff_line"));
            smartDeviceV2.setSmartDeviceType(SmartDeviceEnum.SMART_DEVICE_LOCK.getCode());
            smartDeviceV2.setConnectionStatusUpdateTime(DateUtils.longToString(lockResponseJSON.getLong("onoff_time") * 1000L, "yyyy-MM-dd HH:mm:ss"));
            smartLock.setSmartDeviceV2(smartDeviceV2);
            List<SmartLockPassword> smartLockPasswordList = this.fetchSmartLockPassword(lockResponseJSON.getString("uuid"), userId);
            smartLock.setSmartLockPasswordList(smartLockPasswordList);
            lockVOList.add(smartLock);

        } else {
            Log.error("该房源下外门锁不存在,homeId:{}", thirdHomeId);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("gatewayInfoVOList", gatewayInfoVOList);
        resultMap.put("lockVOList", lockVOList);
        return resultMap;

    }

    /**
     * 查询房间设备信息
     *
     * @param userId
     * @param thirdRoomId
     * @return
     * @throws SmartLockException
     */
    @Override
    public List<SmartLock> searchRoomDeviceInfo(String userId, String thirdRoomId) throws SmartLockException, ParseException {
        Log.info("根据第三方homeId查询对应userId下门锁列表,userId:{}", userId);
        String url = BASE_URL + "/get_lock_info";
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", YunDingSmartLockUtil.getAccessToken(userId));
        map.put("room_id", thirdRoomId);
        String lockResult = HttpClientUtil.doGet(url, map);
        JSONObject resJSON = JSONObject.parseObject(lockResult);
        List<SmartLock> lockVOList = new ArrayList<>();
        if ("0".equals(resJSON.getString("ErrNo"))) {
            SmartLock smartLock = new SmartLock();
            SmartDeviceV2 smartDeviceV2 = new SmartDeviceV2();
            smartLock.setSn(resJSON.getString("sn"));
            smartLock.setMac(resJSON.getString("mac"));
            smartLock.setModel(resJSON.getString("model"));
            smartLock.setModelName(resJSON.getString("model_name"));
            smartLock.setPower(resJSON.getString("power"));
            smartLock.setPowerRefreshtime(DateUtils.longToString(resJSON.getLong("power_refreshtime") * 1000L, "yyyy-MM-dd HH:mm:ss"));
            smartLock.setOnoffTime(resJSON.getString("onoff_line"));
            smartLock.setLqi(resJSON.getString("lqi"));
            smartLock.setLqiRefreshtime(DateUtils.longToString(resJSON.getLong("lqi_refreshtime") * 1000L, "yyyy-MM-dd HH:mm:ss"));
            smartLock.setBindTime(DateUtils.longToString(resJSON.getLong("bind_time") * 1000L, "yyyy-MM-dd HH:mm:ss"));
            smartLock.setVersions(resJSON.getString("versions"));
            smartLock.setGatewayUuid(resJSON.getString("center_uuid"));
            smartDeviceV2.setBrand(resJSON.getString("brand"));
            smartDeviceV2.setName(resJSON.getString("name"));
            smartDeviceV2.setThreeId(resJSON.getString("uuid"));
            smartDeviceV2.setConnectionStatus(resJSON.getString("onoff_line"));
            smartDeviceV2.setSmartDeviceType(SmartDeviceEnum.SMART_DEVICE_LOCK.getCode());
            smartDeviceV2.setConnectionStatusUpdateTime(DateUtils.longToString(resJSON.getLong("onoff_time") * 1000L, "yyyy-MM-dd HH:mm:ss"));
            smartLock.setSmartDeviceV2(smartDeviceV2);
            String lockUuid = resJSON.getString("uuid");
            List<SmartLockPassword> smartLockPasswordList = null;
            if (lockUuid != null) {
                smartLockPasswordList = this.fetchSmartLockPassword(resJSON.getString("uuid"), userId);
            }
            smartLock.setSmartLockPasswordList(smartLockPasswordList);
            lockVOList.add(smartLock);
        } else {
            Log.error("该房间下门锁不存在,roomId:{}", thirdRoomId);
        }

        return lockVOList;

    }

    /**
     * 根据门锁uuid查询密码列表信息
     */
    @Override
    public List<SmartLockPassword> fetchSmartLockPassword(String uuid, String userId) throws SmartLockException, ParseException {
        Log.info("根据第三方uuid查询门锁密码列表，uuid:{}", uuid);
        String url = BASE_URL + "/fetch_passwords";
        String managerPasswordUrl = BASE_URL + "/get_default_password_plaintext";
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", YunDingSmartLockUtil.getAccessToken(userId));
        map.put("uuid", uuid);
        String lockPasswordResult = HttpClientUtil.doGet(url, map);
        JSONObject resJSON = JSONObject.parseObject(lockPasswordResult);
        List<SmartLockPassword> smartLockPasswordList = new ArrayList<>();
        if ("0".equals(resJSON.getString("ErrNo"))) {
            JSONObject passwords = resJSON.getJSONObject("passwords");
            for (Map.Entry<String, Object> entry : passwords.entrySet()) {
                JSONObject password = JSONObject.parseObject(entry.getValue().toString());
                SmartLockPassword smartLockPassword = new SmartLockPassword();
                smartLockPassword.setThreeId(password.getString("id"));
                smartLockPassword.setLock3Id(uuid);
                smartLockPassword.setName(password.getString("name"));
                smartLockPassword.setStatus(password.getString("pwd_state"));
                smartLockPassword.setPwdType(password.getJSONObject("permission").getString("status"));
                smartLockPassword.setSendToMobile(password.getString("send_to"));
                //等于1为永久性密码，等于2为时效密码
                if (smartLockPassword.getPwdType().equals("2")) {
                    smartLockPassword.setValidTimeStart(DateUtils.longToString(password.getJSONObject("permission").getLong("begin") * 1000L, "yyyy-MM-dd HH:mm:ss"));
                    smartLockPassword.setValidTimeEnd(DateUtils.longToString(password.getJSONObject("permission").getLong("end") * 1000L, "yyyy-MM-dd HH:mm:ss"));
                }
                //等于999为管理员密码
                if (MANAGER_SMART_LOCK_PASSWORD_ID.equals(password.getString("id"))) {
                    String managerPasswordJsonStr = HttpClientUtil.doGet(managerPasswordUrl, map);
                    JSONObject managerPasswordJson = JSONObject.parseObject(managerPasswordJsonStr);
                    String passwordStr = managerPasswordJson.getString("password");
                    smartLockPassword.setPassword(passwordStr);
                }
                smartLockPassword.setDescription(password.getString("description"));
                smartLockPassword.setIsDefault(password.getString("is_default"));
                smartLockPasswordList.add(smartLockPassword);

            }

        } else {
            throw new SmartLockException("第三方接口调用失败");

        }

        return smartLockPasswordList;

    }


    /**
     * 根据网关的uuid查询网关信息
     *
     * @param params
     * @return
     * @throws SmartLockException
     */
    public String searchGataWayInfo(Map<String, Object> params) throws SmartLockException {
        Log.info("根据网关uuid查询网关信息,uuid:{}", params.get("uuid"));
        String url = BASE_URL + "/get_center_info";
        Map<String, Object> map = new HashMap<>();
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
