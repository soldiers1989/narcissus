package com.ih2ome.peony.smartlockInterface.guojia;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ih2ome.common.utils.HttpClientUtil;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.guojia.util.GuoJiaSmartLockUtil;
import com.ih2ome.peony.smartlockInterface.vo.GuoJiaLockInfoVo;
import com.ih2ome.peony.smartlockInterface.vo.GuoJiaRegionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sky
 * @create 2017/12/25
 * @email sky.li@ixiaoshuidi.com
 **/
public class GuoJiaSmartLock implements ISmartLock{
    private static final Logger Log = LoggerFactory.getLogger(GuoJiaSmartLock.class);
    private static final String BASE_URL = "http://ops.huohetech.com:80";

    /**
     * 根据门锁编码获取门锁基本信息
     * @param lockNo
     * @return
     */
    @Override
    public GuoJiaLockInfoVo getGuoJiaLockInfo(String lockNo) throws SmartLockException {
        Log.info("获取门锁信息");
        Log.info("门锁编码:"+lockNo);
        GuoJiaLockInfoVo guoJiaLockInfoVo=new GuoJiaLockInfoVo();
        String url=BASE_URL+"/lock/view";
        Map<String, String> headers = GuoJiaSmartLockUtil.getHeaders();
        JSONObject json=new JSONObject();
        json.put("lock_no",lockNo);
        String result = HttpClientUtil.doPostJson(url,json, headers);
        JSONObject resJson = null;
        try {
             resJson= JSONObject.parseObject(result);
        }catch (Exception e){
            Log.error("json格式解析错误",e);
            throw new SmartLockException("json格式解析错误"+e.getMessage());
        }
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            String rlt_msg = resJson.get("rlt_msg").toString();
            Log.error("第三方请求失败/n"+rlt_msg);
            throw new SmartLockException("第三方请求失败/n" + resJson.get("rlt_msg"));
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
        List<GuoJiaRegionVo> regionList=new ArrayList<GuoJiaRegionVo>();
        JSONArray regionJson = dataJson.getJSONArray("region");
        regionList=JSONObject.parseArray(regionJson.toString(),GuoJiaRegionVo.class);
        guoJiaLockInfoVo.setRegion(regionList);
        System.out.println(guoJiaLockInfoVo.toString());
        return guoJiaLockInfoVo;

    }
}
