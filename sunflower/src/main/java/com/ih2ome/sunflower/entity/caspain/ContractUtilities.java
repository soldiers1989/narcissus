package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class ContractUtilities implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String name;
  private String charge;
  private double unitPrice;
  private double monthPrice;
  private double meterCurrent;
  private java.sql.Date meterTime;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private double publicMeterCurrent;
  private double oneoffPrice;

}
