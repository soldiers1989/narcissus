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
     * 公寓类型
     */
    private String houseCatalog;
    /**
     * 公寓名称
     */
    private String homeName;
    /**
     * 公寓地址
     */
    private  String houseAddress;
    /**
     * 公共区域id
     */
    private String publicZoneId;
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 房间名称
     */
    private String roomName;
    /**
     * 门锁Id
     */
    private String lockId;
    /**
     * 门锁序列号
     */
    private String lockCode;
    /**
     * 通讯状态
     */
    private String connectionStatus;
    /**
     * 电量
     */
    private String power;
    /**
     * 信号
     */
    private String lqi;
    /**
     * 信号刷新时间
     */
    private String lqiRefreshtime;
    /**
     * 网关id
     */
    private long gatewayId;
    /**
     * 网关编号
     */
    private String gatewayCode;
    /**
     * 网关名称
     */
    private String gatewayName;
    /**
     * 绑定时间
     */
    private String bindTime;
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
     * 更新时间
     */
    private String updateTime;
    /**
     * 设备型号名称
     */
    private String modelName;

    /**
     * 将versionJson切割赋值给各个version
     */
    public void splitVersion() {
        JSONObject jsonObject = JSONObject.parseObject(this.versionJson);
        this.fireWareVersion = jsonObject.getString("app_version");
        this.hardwareVersion = jsonObject.getString("hardware_version");
        this.mediaVersion = jsonObject.getString("media_version");
        this.kernelVersion = jsonObject.getString("kernel_version");
        this.zigBeeVersion = jsonObject.getString("zigbee_version");
    }

}
