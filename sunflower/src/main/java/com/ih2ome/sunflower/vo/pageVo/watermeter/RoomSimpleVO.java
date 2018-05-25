package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

import java.util.List;

/**
 * @author Hunter Pan
 * create by 2018/5/23
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@Data
public class RoomSimpleVO {
    private int roomId;
    private String roomName;
    List<WaterSimpleVO> waterSimpleList;
}
