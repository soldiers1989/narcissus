package com.ih2ome.watermeter.model;

import lombok.Data;

import java.util.Date;

@Data
public class Watermeter {
    private int smartWatermeterId;
    private int apartmentId;
    private Date createdAt;
    private int createdBy;
    private Date deletedAt;
    private int deletedBy;
    private int floorId;
    private int houseCatalog;
    private int houseId;
    private int lastAmount;
    private int meterAmount;
    private int meterType;
    private Date meterUpdatedAt;
    private int price;
    private int roomId;
    private Date updatedAt;
    private int updatedBy;
    private String uuid;

}
