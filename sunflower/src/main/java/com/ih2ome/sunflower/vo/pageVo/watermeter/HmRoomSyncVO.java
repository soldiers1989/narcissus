package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

/**
 * 分散式为同步的roomVO
 */
@Data
public class HmRoomSyncVO {
    private int id;//room的id
    private int houseId;//房源id
    private String name;//名称
    private int synchronous;//同步状态

}
