package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

/**
 * @author Hunter Pan
 * create by 2018/5/24
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@Data
public class RoomDetailVO {
    public String customerName;
    public String customerPhone;
    public String startTime;
    public String roomName;
    public String apartmentName;
    public String contractStatus;
    public double coldPrice;
    public double hotPrice;
    public double balance;
}
