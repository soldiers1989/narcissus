package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class CustomerLoanApplyRecord implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String customerPhone;
  private String customerName;
  private String customerId;
  private String landlordName;
  private String landlordPhone;
  private long unixTime;
  private long roomContractId;
  private String loanContractId;
  private double monthRental;
  private String address;
  private String houseInfo;
  private String action;
  private long status;
  private String bankCard;
  private String sceneId;
  private String sceneCustId;
  private String retCode;
  private String retMsg;
  private String cheMsg;
  private String custNo;
  private String bankCode;
  private String applyNo;
  private String amount;
  private long createdById;
  private long deletedById;
  private long updatedById;

}
