package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class ShuididaiCreditaccount implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp systemCreditGenAt;
  private java.sql.Timestamp updatedAt;
  private double systemCredit;
  private double fixedCredit;
  private long isShowSystemCredit;
  private long creditStatus;
  private long updatedById;
  private long userId;
  private double grossProfitRate;
  private double rentRate;

}
