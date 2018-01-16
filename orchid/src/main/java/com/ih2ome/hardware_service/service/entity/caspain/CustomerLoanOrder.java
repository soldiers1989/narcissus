package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class CustomerLoanOrder implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private long number;
  private long roomContractId;
  private double amount;
  private long isPay;
  private String dueDate;
  private String realDate;
  private long term;
  private long paymentFlag;
  private long contractId;
  private long repaymentPlanId;
  private double realRepayment;
  private double repayment;
  private double capital;
  private double interest;
  private double penaltyInterest;
  private long invalidSign;
  private long createdById;
  private long deletedById;
  private long loanId;
  private long updatedById;

}
