package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SmartMistakeInfo {

    private Long id;
    private java.sql.Timestamp createdAt;
    private Long smartDeviceType;
    private String uuid;
    private String sn;
    private String exceptionType;
    private String smartLockPasswordId;
    private String passwordName;
    private String userName;
    private String mobile;
    private String passname;
    private String exceptionName;
    private String description;
    private String createdTime;
    private String operatorType;

    private String yearMonthDay;
    private String hourMinuteSecond;


    public void splitCreatedTime() {
        String[] array = this.createdTime.split(" ");
        this.yearMonthDay=array[0];
        this.hourMinuteSecond=array[1];
    }
}
