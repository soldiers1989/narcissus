package com.ih2ome.hardware_service.service.vo;

import lombok.Data;

import java.util.List;

@Data
public class HomeAndRoomSyncVO {
    private int homeId;
    private List<Integer> roomIds;
}
