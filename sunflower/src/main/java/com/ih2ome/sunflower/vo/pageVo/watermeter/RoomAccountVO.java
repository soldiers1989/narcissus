package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

import java.util.List;

/**
 * @author Hunter Pan
 * create by 2018/5/25
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@Data
public class RoomAccountVO {
    private int roomId;
    private int houseCatalog;
    private double balance;
    private String updatedAt;
    private List<RoomWaterAccountVO> waterAccountList;
}
