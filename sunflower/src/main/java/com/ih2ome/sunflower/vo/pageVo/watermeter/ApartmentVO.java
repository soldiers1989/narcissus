package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

import java.util.List;

@Data
public class ApartmentVO {
    //公寓id，公寓名称，公寓水表总数，在线数，楼层名称list
    private int id;
    private String name;
    private List<FloorVO> floorVOS;
}
