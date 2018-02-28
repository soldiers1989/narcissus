package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class HouseRentorder implements Serializable{

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
  private java.sql.Timestamp generateTime;
  private java.sql.Date oughtPayTime;
  private java.sql.Date deadlinePayTime;
  private java.sql.Timestamp actualPayTime;
  private long payStatus;
  private String comments;
  private long contractId;
  private long createdById;
  private long deletedById;
  private long updatedById;
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
