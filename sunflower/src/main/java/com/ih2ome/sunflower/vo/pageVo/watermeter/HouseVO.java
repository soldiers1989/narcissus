package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

@Data
public class HouseVO {
    private int id;//houseId
    private String area;//小区
    private String buildingNum;//栋
    private String floorNum;//楼
    private String houseNum;//门牌号
    private int synchronous;//房源同步1.云丁
}
