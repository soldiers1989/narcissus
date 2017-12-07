package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class CustomerLoan implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String customerPhone;
  private String customerName;
  private java.sql.Timestamp confirmAt;
  private long roomContractId;
  private String loanContractId;
  private double loan;
  private long status;
  private long action;
  private java.sql.Date lastPayDate;
  private long lastPayRepaymentId;
  private long lastPayTerm;
  private double fromTongniu;
  private double toTongniu;
  private double tongniuInterest;
  private double toLandlord;
  private double fromLandlord;
  private double h2OmeInterest;
  private double h2OmeMargin;
  private double h2OmeDefault;
  private long createdById;
  private long deletedById;
  private long landlordId;
  private long updatedById;
  private String landlordName;
  private String landlordPhone;
  private long payStatus;
  private String sceneCustId;
  private double h2OmeDefaultPercent;
  private double h2OmeInterestPercent;
  private double h2OmeMarginPercent;
  private double tongniuInterestPercent;
  private long createdOrder;
  private long loanSource;
  private String address;
  private double monthRental;
  private String houseInfo;
  private double tongniuDefaultPercent;

}
