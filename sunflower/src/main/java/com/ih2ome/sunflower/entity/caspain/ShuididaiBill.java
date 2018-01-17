package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class ShuididaiBill implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private long sequenceNumber;
  private java.sql.Date deadlinePayDay;
  private double amount;
  private java.sql.Timestamp payAt;
  private double payAmount;
  private long status;
  private long isDeleted;
  private long contractId;
  private long updatedById;

}
