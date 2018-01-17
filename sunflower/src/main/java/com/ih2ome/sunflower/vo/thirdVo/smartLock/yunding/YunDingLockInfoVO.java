package com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding;

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
    private Integer bindTime;
    /**
     * 门锁在线状态（1在线，2离线）
     */
    private Integer onoffLine;
    /**
     * 门锁名字
     */
    private String name;
    /**
     * 门锁电量信息（-1 表示未知）
     */
    private Integer power;
    /**
     * 最近一次更新电量时间
     */
    private Integer powerRefreshtime;
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
    private Integer lqi;
    /**
     * 门锁信号刷新
     */
    private Integer lqiRefreshtime;
    /**
     * 门锁所绑定的网关名称，描述。
     */
    private  String centerDescription;
     /**
     * 门锁所绑定的网关 uuid
     */
    private String centerUuid;
}
