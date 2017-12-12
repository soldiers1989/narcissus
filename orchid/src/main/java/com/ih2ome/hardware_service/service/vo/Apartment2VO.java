package com.ih2ome.hardware_service.service.vo;

import lombok.Data;

@Data
public class Apartment2VO {
    private int id;
    private String name;
    private int floorNum;
    private int watermeterNum;//水表总数
    private int onoffNum;//水表在线数
}
