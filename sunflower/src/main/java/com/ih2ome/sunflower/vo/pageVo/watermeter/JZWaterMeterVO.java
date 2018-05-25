package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

@Data
public class JZWaterMeterVO {
    //房间名
    private String name;
    //水表类型
    private String waterType;
    //水表编号
    private String uuid;
    //状态
    private String connectionStatus;
}
