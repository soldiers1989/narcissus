package com.ih2ome.hardware_service.service.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SmartGatewayVO {
    private int smartGatewayId;
    private int apartmentId;
    private String brand;
    private Date createdAt;
    private int  createdBy;
    private Date deletedAt;
    private int deletedBy;
    private int floor;
    private int houseCatalog;
    private int houseId;
    private String installMobile;
    private String installName;
    private int installStatus;
    private Date installTime;
    private String mac;
    private String model;
    private String modelName;
    private String name;
    private int onoffStatus;
    private String operator;
    private String remark;
    private String sn;
    private Date updatedAt;
    private int updatedBy;
    private String  uuid;
    private int ID;//所属房间ID

}
