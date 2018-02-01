package com.ih2ome.sunflower.vo.pageVo.smartLock;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/31
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class SmartLockDetailVO {
    /**
     * 网关id
     */
    private String gatewayId;
    /**
     * 网关编号
     */
    private String gatewayCode;
    /**
     * 绑定时间
     */
    private String bindTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 硬件厂商
     */
    private String provider;
    /**
     * 固件版本
     */
    private String fireWareVersion;
    /**
     * 硬件版本
     */
    private String hardwareVersion;
    /**
     * 媒体版本
     */
    private String mediaVersion;
    /**
     * 内核版本
     */
    private String kernelVersion;
    /**
     * zig_bee版本
     */
    private String zigBeeVersion;

    /**
     * 第三方版本数据json字符串
     */
    private String versionJson;

    /**
     * 将versionJson切割赋值给各个version
     */
    public void splitVersion(){
        JSONObject jsonObject = JSONObject.parseObject(this.versionJson);
        this.fireWareVersion = jsonObject.getString("app_version");
        this.hardwareVersion = jsonObject.getString("hardware_version");
        this.mediaVersion = jsonObject.getString("media_version");
        this.kernelVersion = jsonObject.getString("zigbee_version");
        this.zigBeeVersion = jsonObject.getString("zigbee_version");

    }

}
