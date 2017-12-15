package com.ih2ome.hardware_service.service.vo;

import lombok.Data;

@Data
public class HouseVO {
    private int id;//houseId
    private String area;//小区
    private String building_num;//栋
    private String floor_num;//楼
    private String house_num;//门牌号
    private int synchronous;//房源同步1.云丁
}
