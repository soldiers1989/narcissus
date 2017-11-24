package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class ShuididaiBill {

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
