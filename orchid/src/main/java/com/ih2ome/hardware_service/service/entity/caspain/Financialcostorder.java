package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Financialcostorder implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String roomId;
  private String direction;
  private long feesName;
  private double amount;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private long createdById;
  private long deletedById;
  private long houseId;
  private long updatedById;
  private long houseContractId;
  private long roomContractId;

}
