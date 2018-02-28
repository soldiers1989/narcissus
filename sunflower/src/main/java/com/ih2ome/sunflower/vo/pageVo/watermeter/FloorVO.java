package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

@Data
public class FloorVO {

    private int watermeterNum;//楼层水表总数
    private int watermeterOnoffNum;//水表在线数
    private String floorName;
    private int floorId;

}
