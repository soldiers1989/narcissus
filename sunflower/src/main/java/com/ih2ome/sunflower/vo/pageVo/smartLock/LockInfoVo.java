package com.ih2ome.sunflower.vo.pageVo.smartLock;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Sky
 * @create 2017/12/26
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class LockInfoVo implements Serializable {
    //门锁Id
    private String id;
    //房源地址
    private String houseAddress;
    //房屋编号
    private String roomNo;
    //门锁编码
    private String serialNum;
    //通讯状态
    private String status;
    //通讯状态更新时间
    private String statusUpdateTime;
    //剩余电量
    private String remainingBattery;
    //安装时间
    private String installTime;
    //备注
    private String remark;
    //网关编码
    private String gateway;
    //厂商
    private String providerName;
    //质保日期（起）
    private String guaranteeTimeStart;
    //质保日期(止)
    private String guaranteeTimeEnd;

}
