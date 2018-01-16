package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class ShuididaiContract implements Serializable{

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
