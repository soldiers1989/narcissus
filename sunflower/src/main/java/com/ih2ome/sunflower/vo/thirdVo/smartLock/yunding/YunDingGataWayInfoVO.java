package com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author Sky
 * @create 2018/01/25
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class YunDingGataWayInfoVO {
    /**
     * 公寓id
     */
    private String homeId;
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 网关 uuid
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
     * 网关在线状态（1在线，2离线）
     */
    private String onoffLine;
    /**
     * 最近一次在线状态更新时间戳
     */
    private String onoffTime;
    /**
     * 网关名字
     */
    private String name;

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
     * 网关版本信息
     */
    private YunDingDeviceVersion versions;


    public static YunDingGataWayInfoVO jsonToObject(JSONObject jsonObject) {
        YunDingGataWayInfoVO yunDingGataWayInfoVO = new YunDingGataWayInfoVO();
        yunDingGataWayInfoVO.setMac(jsonObject.getString("mac"));
        yunDingGataWayInfoVO.setSn(jsonObject.getString("sn"));
        yunDingGataWayInfoVO.setBindTime(jsonObject.getString("bind_time"));
        yunDingGataWayInfoVO.setOnoffLine(jsonObject.getString("onoff_line"));
        yunDingGataWayInfoVO.setOnoffTime(jsonObject.getString("onoff_time"));
        yunDingGataWayInfoVO.setUuid(jsonObject.getString("uuid"));
        yunDingGataWayInfoVO.setName(jsonObject.getString("name"));
        yunDingGataWayInfoVO.setModel(jsonObject.getString("model"));
        yunDingGataWayInfoVO.setModelName(jsonObject.getString("model_name"));
        yunDingGataWayInfoVO.setBrand(jsonObject.getString("brand"));
        JSONObject jsonVersion = JSONObject.parseObject(jsonObject.getString("versions"));
        YunDingDeviceVersion yunDingDeviceVersion = new YunDingDeviceVersion();
        yunDingDeviceVersion.setAppVersion(jsonVersion.getString("app_version"));
        yunDingDeviceVersion.setHardwareVersion(jsonVersion.getString("hardware_version"));
        yunDingDeviceVersion.setKernelVersion(jsonVersion.getString("kernel_version"));
        yunDingDeviceVersion.setMediaVersion(jsonVersion.getString("media_version"));
        yunDingDeviceVersion.setZigbeeVersion(jsonVersion.getString("zigbee_version"));
        yunDingGataWayInfoVO.setVersions(yunDingDeviceVersion);
        return yunDingGataWayInfoVO;
    }
}
