package com.ih2ome.peony.smartlockInterface.vo;


import lombok.Data;

import java.util.List;

/**
 * 果家门锁基本信息
 * @author Sky
 * @create 2017/12/26
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class GuoJiaLockInfoVo {
    //门锁分类
    private String lockKind;
    //门锁编码
    private String lockNo;
    //网关编码
    private String nodeNo;
    //电池电量
    private Long power;
    //电池更新时间
    private Long powerUpdateTime;
    //网关通信状态
    private String nodeComuStatus;
    //门锁通信状态
    private String comuStatus;
    //门锁通信状态更新
    private Long comuStatusUpdateTime;
    //网关接收到的锁信号强度
    private Long rssi;
    //安装地区
    private List<GuoJiaRegionVo> region;
    //安装地址
    private String address;
    //业务编码
    private String houseCode;
    //安装日期
    private Long installTime;
    //质保日期（起）
    private Long guaranteeTimeStart;
    //质保日期(止)
    private Long guaranteeTimeEnd;
    //描述
    private String description;
}
