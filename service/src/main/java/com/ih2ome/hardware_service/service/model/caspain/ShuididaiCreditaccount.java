package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class ShuididaiCreditaccount {

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
