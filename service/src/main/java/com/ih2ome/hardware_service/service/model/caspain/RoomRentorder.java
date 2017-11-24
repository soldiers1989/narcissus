package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class RoomRentorder {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String code;
  private long number;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private double amount;
  private String payMethod;
  private java.sql.Date oughtPayTime;
  private java.sql.Date deadlinePayTime;
  private java.sql.Timestamp actualPayTime;
  private long payStatus;
  private long payOutStatus;
  private String comments;
  private String wxTradeNo;
  private long contractId;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private long byH2Ome;
  private java.sql.Timestamp lastRemindTimes;
  private long remindTimes;
  private double rentDeposit;
  private double rentUtilities;
  private String todoStatus;
  private long isMeterNeed;
  private String uuid;
  private long houseId;
  private String tradeSerialNo;
  private double deposit;
  private double oldDeposit;

}
