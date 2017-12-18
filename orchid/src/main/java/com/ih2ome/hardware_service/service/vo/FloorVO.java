package com.ih2ome.hardware_service.service.vo;

import lombok.Data;

@Data
public class FloorVO {

    private int watermeterNum;//楼层水表总数
    private int watermeterOnoffNum;//水表在线数
    private String floorName;
    private int floorId;

}
