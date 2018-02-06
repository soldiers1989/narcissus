package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

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

    private String exceptionName;
    private String description;

}
