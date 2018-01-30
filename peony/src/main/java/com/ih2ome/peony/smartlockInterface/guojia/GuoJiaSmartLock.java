package com.ih2ome.peony.smartlockInterface.guojia;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.DateUtils;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.guojia.util.GuoJiaSmartLockUtil;
import com.ih2ome.sunflower.entity.narcissus.SmartLock;
import com.ih2ome.sunflower.entity.narcissus.SmartLockPassword;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.GatewayInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.guojia.GuoJiaGateWayVo;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.guojia.GuoJiaLockInfoVo;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.guojia.GuoJiaRegionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sky
 * @create 2017/12/25
 * @email sky.li@ixiaoshuidi.com
 **/
public class GuoJiaSmartLock implements ISmartLock {
    private static final Logger Log = LoggerFactory.getLogger(GuoJiaSmartLock.class);
    private static final String BASE_URL = "http://ops.huohetech.com:80";

    /**
     * 根据门锁编码获取门锁基本信息
     *
     * @param lockNo
     * @return
     */
    @Override
    public LockVO getLockInfo(String lockNo) throws SmartLockException {
        Log.info("获取门锁信息");
        Log.info("门锁编码:" + lockNo);
        GuoJiaLockInfoVo guoJiaLockInfoVo = new GuoJiaLockInfoVo();
        String url = BASE_URL + "/lock/view";
        Map<String, String> headers = GuoJiaSmartLockUtil.getHeaders();
        JSONObject json = new JSONObject();
        json.put("lock_no", lockNo);
        String result = HttpClientUtil.doPostJson(url, json, headers);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("json格式解析错误", e);
            throw new SmartLockException("json格式解析错误" + e.getMessage());
        }
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            String rlt_msg = resJson.get("rlt_msg").toString();
            Log.error("第三方请求失败/n" + rlt_msg);
            throw new SmartLockException("第三方请求失败/n" + rlt_msg);
        }
        JSONObject dataJson = JSONObject.parseObject(resJson.get("data").toString());
        guoJiaLockInfoVo.setLockKind(dataJson.getString("lock_kind"));
        guoJiaLockInfoVo.setLockNo(dataJson.getString("lock_no"));
        guoJiaLockInfoVo.setNodeNo(dataJson.getString("node_no"));
        guoJiaLockInfoVo.setPower(dataJson.getLong("power"));
        guoJiaLockInfoVo.setPowerUpdateTime(dataJson.getLong("power_update_time"));
        guoJiaLockInfoVo.setNodeComuStatus(dataJson.getString("node_comu_status"));
        guoJiaLockInfoVo.setComuStatus(dataJson.getString("comu_status"));
        guoJiaLockInfoVo.setComuStatusUpdateTime(dataJson.getLong("comu_status_update_time"));
        guoJiaLockInfoVo.setRssi(dataJson.getLong("rssi"));
        guoJiaLockInfoVo.setAddress(dataJson.getString("address"));
        guoJiaLockInfoVo.setHouseCode(dataJson.getString("house_code"));
        guoJiaLockInfoVo.setInstallTime(dataJson.getLong("install_time"));
        guoJiaLockInfoVo.setGuaranteeTimeStart(dataJson.getLong("guarantee_time_start"));
        guoJiaLockInfoVo.setGuaranteeTimeEnd(dataJson.getLong("guarantee_time_end"));
        guoJiaLockInfoVo.setDescription(dataJson.getString("description"));
        List<GuoJiaRegionVo> regionList = new ArrayList<GuoJiaRegionVo>();
        JSONArray regionJson = dataJson.getJSONArray("region");
        regionList = JSONObject.parseArray(regionJson.toString(), GuoJiaRegionVo.class);
        guoJiaLockInfoVo.setRegion(regionList);
        System.out.println(guoJiaLockInfoVo.toString());
        return guoJiaLockInfoVo;

    }

    @Override
    public GatewayInfoVO getGateWayInfo(String gateNo) throws SmartLockException {
        Log.info("获取网关信息");
        Log.info("网关编码:" + gateNo);
        GuoJiaGateWayVo guoJiaGateWayVo = new GuoJiaGateWayVo();
        String url = BASE_URL + "/node/view";
        Map<String, String> headers = GuoJiaSmartLockUtil.getHeaders();
        JSONObject json = new JSONObject();
        json.put("node_no", gateNo);
        String result = HttpClientUtil.doPostJson(url, json, headers);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("json格式解析错误", e);
            throw new SmartLockException("json格式解析错误" + e.getMessage());
        }
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            String rlt_msg = resJson.get("rlt_msg").toString();
            Log.error("第三方请求失败/n" + rlt_msg);
            throw new SmartLockException("第三方请求失败/n" + rlt_msg);
        }
        JSONObject dataJson = JSONObject.parseObject(resJson.get("data").toString());
        guoJiaGateWayVo.setNodeKind(dataJson.getString("node_kind"));
        guoJiaGateWayVo.setNodeNo(dataJson.getString("node_no"));
        guoJiaGateWayVo.setName(dataJson.getString("name"));
        guoJiaGateWayVo.setComuStatus(dataJson.getString("(comu_status"));
        guoJiaGateWayVo.setComuStatusUpdateTime(dataJson.getString("comu_status_update_time"));
        guoJiaGateWayVo.setAddress(dataJson.getString("address"));
        guoJiaGateWayVo.setHouseCode(dataJson.getString("house_code"));
        guoJiaGateWayVo.setInstallTime(dataJson.getString("install_time"));
        guoJiaGateWayVo.setGuaranteeTimeStart(GuoJiaSmartLockUtil.stampToDate(dataJson.getString("guarantee_time_start")));
        guoJiaGateWayVo.setGuaranteeTimeEnd(GuoJiaSmartLockUtil.stampToDate(dataJson.getString("guarantee_time_end")));
        guoJiaGateWayVo.setDescription(dataJson.getString("description"));
        List<GuoJiaRegionVo> regionList = null;
        JSONArray regionJson = dataJson.getJSONArray("region");
        regionList = JSONObject.parseArray(regionJson.toString(), GuoJiaRegionVo.class);
        guoJiaGateWayVo.setRegion(regionList);
        return guoJiaGateWayVo.third2Standard();
    }

    /**
     * 新增门锁密码
     *
     * @param lockPassword
     * @return
     * @throws SmartLockException
     */
    @Override
    public String addLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException {
        Log.info("新增密码");
        Log.info("新增密码信息：" + lockPassword.toString());
        Map<String, String> headers = GuoJiaSmartLockUtil.getHeaders();
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("lock_no", lockPassword.getSerialNum());
        pwdJson.put("pwd_text", GuoJiaSmartLockUtil.desEncode(lockPassword.getPassword()));
        pwdJson.put("valid_time_start", String.valueOf(DateUtils.stringToLong
                (lockPassword.getEnableTime(), "yyyy-MM-dd hh:mm:ss")));
        pwdJson.put("valid_time_end", String.valueOf(DateUtils.stringToLong
                (lockPassword.getDisableTime(), "yyyy-MM-dd hh:mm:ss")));
        pwdJson.put("pwd_user_name", lockPassword.getUserName());
        pwdJson.put("pwd_user_mobile", lockPassword.getMobile());
        pwdJson.put("description", lockPassword.getRemark());
        String url = BASE_URL + "/pwd/add";
        //请求第三方保存密码
        String result = HttpClientUtil.doPostJson(url, pwdJson, headers);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("json格式解析错误", e);
            throw new SmartLockException("json格式解析错误" + e.getMessage());
        }
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            String rlt_msg = resJson.get("rlt_msg").toString();
            Log.error("第三方请求失败/n" + rlt_msg);
            throw new SmartLockException("第三方请求失败/n" + rlt_msg);
        }
        return result;
    }

    /**
     * 修改门锁密码
     *
     * @param lockPassword
     * @return
     */
    @Override
    public String updateLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException {
        Log.info("修改密码");
        Log.info("修改密码信息：" + lockPassword.toString());
        Map<String, String> headers = GuoJiaSmartLockUtil.getHeaders();
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("lock_no", lockPassword.getSerialNum());
        pwdJson.put("pwd_no", lockPassword.getPwdNo());
        pwdJson.put("pwd_text", GuoJiaSmartLockUtil.desEncode(lockPassword.getPassword()));
        pwdJson.put("valid_time_start", String.valueOf(DateUtils.stringToLong
                (lockPassword.getEnableTime(), "yyyy-MM-dd hh:mm:ss")));
        pwdJson.put("valid_time_end", String.valueOf(DateUtils.stringToLong
                (lockPassword.getDisableTime(), "yyyy-MM-dd hh:mm:ss")));
        pwdJson.put("extra", lockPassword.getRemark());

        String url = BASE_URL + "/pwd/update";
        //请求第三方修改密码
        String result = HttpClientUtil.doPostJson(url, pwdJson, headers);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("json格式解析错误", e);
            throw new SmartLockException("json格式解析错误" + e.getMessage());
        }
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            String rlt_msg = resJson.get("rlt_msg").toString();
            Log.error("第三方请求失败/n" + rlt_msg);
            throw new SmartLockException("第三方请求失败/n" + rlt_msg);
        }
        return result;
    }

    /**
     * 删除门锁密码
     *
     * @param lockPassword
     * @return
     */
    @Override
    public String deleteLockPassword(LockPasswordVo lockPassword) throws SmartLockException {
        Log.info("删除密码");
        Log.info("删除密码内容：" + lockPassword.toString());
        Map<String, String> headers = GuoJiaSmartLockUtil.getHeaders();
        JSONObject pwdJson = new JSONObject();
        pwdJson.put("lock_no", lockPassword.getSerialNum());
        pwdJson.put("pwd_no", lockPassword.getPwdNo());
        String url = BASE_URL + "/pwd/delete";
        //请求第三方删除密码
        String result = HttpClientUtil.doPostJson(url, pwdJson, headers);
        JSONObject resJson = null;
        try {
            resJson = JSONObject.parseObject(result);
        } catch (Exception e) {
            Log.error("json格式解析错误", e);
            throw new SmartLockException("json格式解析错误" + e.getMessage());
        }
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            String rlt_msg = resJson.get("rlt_msg").toString();
            Log.error("第三方请求失败/n" + rlt_msg);
            throw new SmartLockException("第三方请求失败/n" + rlt_msg);
        }
        return result;
    }

    /**
     * 冻结门锁密码
     *
     * @param lockPassword
     * @return
     * @throws SmartLockException
     * @throws ParseException
     */
    @Override
    public String frozenLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException {
        throw new SmartLockException("该厂商不支持此操作");

    }

    /**
     * 解冻门锁密码
     *
     * @param lockPassword
     * @return
     * @throws SmartLockException
     * @throws ParseException
     */
    @Override
    public String unfrozenLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException {
        throw new SmartLockException("该厂商不支持此操作");
    }

    /**
     * 查询所有的房源信息
     *
     * @return
     * @throws SmartLockException
     */
    @Override
    public String searchHomeInfo(Map<String,Object> params) throws SmartLockException {
        return null;
    }

    @Override
    public Map<String, Object> searchHouseDeviceInfo(String userId, String thirdHomeId) throws SmartLockException {
        throw new SmartLockException("该厂商不支持此操作");
    }

    @Override
    public List<SmartLock> searchRoomDeviceInfo(String userId, String thirdRoomId) throws SmartLockException {
        throw new SmartLockException("该厂商不支持此操作");
    }

    @Override
    public List<SmartLockPassword> fetchSmartLockPassword(String uuid,String userId) throws SmartLockException {
        throw new SmartLockException("该厂商不支持此操作");
    }


}
