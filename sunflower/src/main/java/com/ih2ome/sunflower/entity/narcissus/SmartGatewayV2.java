package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

@Data
public class SmartGatewayV2 {

    private String smartGatewayId;
    private String uuid;
    private String sn;
    private String mac;
    private String model;
    private String modelName;
    private String installTime;
    private String installName;
    private String installMobile;
    private String brand;
    private String operator;
    private String installStatus;
    private String remark;
    private SmartDeviceV2 smartDeviceV2;
    private String versions;


}
