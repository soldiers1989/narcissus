package com.ih2ome.hardware_service.service.entity.narcissus;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartMistakeInfo implements Serializable{

  private Long id;
  private java.sql.Timestamp createdAt;
  private Long smartDeviceType;
  private String uuid;
  private String sn;
  private String exceptionType;

}
