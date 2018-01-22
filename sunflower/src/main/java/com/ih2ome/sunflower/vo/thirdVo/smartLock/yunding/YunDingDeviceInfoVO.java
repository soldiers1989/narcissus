package com.ih2ome.sunflower.vo.thirdVo.smartLock.yunding;

import lombok.Data;

/**
 * @author Sky
 * @create 2018/01/18
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class YunDingDeviceInfoVO {
    //gateway：网关 ；home_lock：外门锁 ；room_lock：内门锁 ；elemeter: 电表 ；elecollector: 采集器
    private String type;
    //设备uuid
    private String uuid;
    //设备序列号
    private String sn;
    //设备绑定的网关id
    private String centerUuid;
    //描述
    private String description;
    //房间id
    private String roomId;
    //水表特有( 默认值为：ym)
    private String manufactory;

}
