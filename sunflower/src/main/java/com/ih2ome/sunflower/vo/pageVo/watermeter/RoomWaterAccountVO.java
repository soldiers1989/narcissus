package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

/**
 * @author Hunter Pan
 * create by 2018/5/25
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@Data
public class RoomWaterAccountVO {
    //当前用量
    private int used;
    //1-冷水 2-热水
    private int meterType;
    //单价 元/吨
    private double price;
    //费用
    private double amount;
}
