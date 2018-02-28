package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

import java.util.List;

@Data
public class HomeAndRoomSyncVO {
    private int homeId;
    private List<Integer> roomIds;
}
