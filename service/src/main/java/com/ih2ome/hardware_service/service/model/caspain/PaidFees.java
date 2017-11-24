package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class PaidFees {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String payUuid;
  private double waterFees;
  private double powerFees;
  private double gasFees;
  private double propertyFees;
  private double serviceFees;
  private double upkeepFees;
  private double cleaningFees;
  private double materialFees;
  private double catvFees;
  private double internetFees;
  private double sanitationFees;
  private double otherFees;
  private double freeFees;
  private double rentDeposit;
  private double rentAmount;
  private long createdById;
  private long deletedById;
  private long orderId;
  private long updatedById;

}
