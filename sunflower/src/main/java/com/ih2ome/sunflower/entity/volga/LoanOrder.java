package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class LoanOrder implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private long number;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private double amount;
  private java.sql.Date oughtPayTime;
  private java.sql.Date deadlinePayTime;
  private java.sql.Timestamp actualPayTime;
  private long payStatus;
  private long remindTimes;
  private java.sql.Timestamp lastRemindTimes;
  private String comments;
  private long loanId;

}
