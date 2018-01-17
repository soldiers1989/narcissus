package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class Financialcostorder implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private String roomId;
  private String direction;
  private long feesName;
  private double amount;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private long houseContractId;
  private long roomContractId;
  private long apartmentId;

}
