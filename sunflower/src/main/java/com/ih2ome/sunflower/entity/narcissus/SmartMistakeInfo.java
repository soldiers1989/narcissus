package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

@Data
public class SmartMistakeInfo {

  private long id;
  private java.sql.Timestamp createdAt;
  private long smartDeviceType;
  private String uuid;
  private String sn;
  private String exceptionType;

}
