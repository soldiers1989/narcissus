package com.ih2ome.peony.smartlockInterface.yunding;

import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.yunding.util.YunDingSmartLockUtil;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.GatewayInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding.YunDingLockPasswordVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

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

}
