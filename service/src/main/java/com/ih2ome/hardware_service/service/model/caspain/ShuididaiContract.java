package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class ShuididaiContract {

  private long id;
  private java.sql.Timestamp createdAt;
  private double credit;
  private double rate;
  private java.sql.Date startTime;
  private long months;
  private java.sql.Date endTime;
  private long aheadOfPayDays;
  private long isDeleted;
  private long applicationId;
  private long userId;

}
