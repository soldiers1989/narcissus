package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

@Data
public class HouseRentorder {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private String uuid;
  private String code;
  private long number;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private double amount;
  private String payMethod;
  private double rentDeposit;
  private double rentUtilities;
  private java.sql.Timestamp generateTime;
  private java.sql.Date oughtPayTime;
  private java.sql.Date deadlinePayTime;
  private java.sql.Timestamp actualPayTime;
  private long payStatus;
  private String comments;
  private String todoStatus;
  private long isMeterNeed;
  private long contractId;
  private long apartmentId;
  private String tradeSerialNo;
  private double deposit;
  private double oldDeposit;

}
