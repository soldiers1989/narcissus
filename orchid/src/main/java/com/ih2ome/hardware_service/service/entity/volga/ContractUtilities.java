package com.ih2ome.hardware_service.service.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class ContractUtilities implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private String name;
  private String charge;
  private double unitPrice;
  private double monthPrice;
  private double meterCurrent;
  private java.sql.Date meterTime;
  private double publicMeterCurrent;
  private double oneoffPrice;

}
