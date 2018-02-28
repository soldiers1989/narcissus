package com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author Sky
 * @create 2018/01/12
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class YunDingLockInfoVO {
    /**
     * 公寓id
     */
    private String homeId;
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 门锁 uuid
     */
    private String uuid;
    /**
     * 设备的 MAC 地址
     */
    private String mac;
    /**
     * 设备序列号
     */
    private String sn;
    /**
     * 设备注册时间
     */
    private String bindTime;
    /**
     * 门锁在线状态（1在线，2离线）
     */
    private String onoffLine;
    /**
     * 最近一次在线状态更新时间戳
     */
    private String onoffTime;
    /**
     * 门锁名字
     */
    private String name;
    /**
     * 门锁电量信息（-1 表示未知）
     */
    private String power;
    /**
     * 最近一次更新电量时间
     */
    private String powerRefreshtime;
    /**
     * 设备类型
     */
    private String model;
    /**
     * 设备型号名称
     */
    private String modelName;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 门锁信号
     */
    private String lqi;
    /**
     * 门锁信号刷新
     */
    private String lqiRefreshtime;
    /**
     * 门锁所绑定的网关名称，描述。
     */
    private String centerDescription;
    /**
     * 门锁所绑定的网关 uuid
     */
    private String centerUuid;
    /**
     * 门锁版本信息
     */
    private YunDingDeviceVersion versions;


    public static YunDingLockInfoVO jsonToObject(JSONObject jsonObject) {
        YunDingLockInfoVO yunDingLockInfoVO = new YunDingLockInfoVO();
        yunDingLockInfoVO.setMac(jsonObject.getString("mac"));
        yunDingLockInfoVO.setSn(jsonObject.getString("sn"));
        yunDingLockInfoVO.setBindTime(jsonObject.getString("bind_time"));
        yunDingLockInfoVO.setOnoffLine(jsonObject.getString("onoff_line"));
        yunDingLockInfoVO.setOnoffTime(jsonObject.getString("onoff_time"));
        yunDingLockInfoVO.setUuid(jsonObject.getString("uuid"));
        yunDingLockInfoVO.setName(jsonObject.getString("name"));
        yunDingLockInfoVO.setPower(jsonObject.getString("power"));
        yunDingLockInfoVO.setPowerRefreshtime(jsonObject.getString("power_refreshtime"));
        yunDingLockInfoVO.setModel(jsonObject.getString("model"));
        yunDingLockInfoVO.setModelName(jsonObject.getString("model_name"));
        yunDingLockInfoVO.setBrand(jsonObject.getString("brand"));
        yunDingLockInfoVO.setLqi(jsonObject.getString("lqi"));
        yunDingLockInfoVO.setLqiRefreshtime(jsonObject.getString("lqi_refreshtime "));
        yunDingLockInfoVO.setCenterDescription(jsonObject.getString("center_description"));
        yunDingLockInfoVO.setCenterUuid(jsonObject.getString("center_uuid"));
        JSONObject jsonVersion = JSONObject.parseObject(jsonObject.getString("versions"));
        YunDingDeviceVersion yunDingDeviceVersion = new YunDingDeviceVersion();
        yunDingDeviceVersion.setAppVersion(jsonVersion.getString("app_version"));
        yunDingDeviceVersion.setHardwareVersion(jsonVersion.getString("hardware_version"));
        yunDingDeviceVersion.setKernelVersion(jsonVersion.getString("kernel_version"));
        yunDingDeviceVersion.setMediaVersion(jsonVersion.getString("media_version"));
        yunDingDeviceVersion.setZigbeeVersion(jsonVersion.getString("zigbee_version"));
        yunDingLockInfoVO.setVersions(yunDingDeviceVersion);
        return yunDingLockInfoVO;
    }
}
