package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class DailyReportForms implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp reportDate;
  private long createdById;
  private long isDelete;
  private long houseNum;
  private long roomNum;
  private long ownerNum;
  private long customerNum;
  private long addOwnerNum;
  private long addCustomerNum;
  private long renewOwnerNum;
  private long renewCustomerNum;
  private long unrentOwnerNum;
  private long unrentCustomerNum;
  private double rentAmount;
  private double rentDeposit;
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
  private double decorationFees;
  private double otherFees;
  private double freeFees;
  private double income;
  private double outcome;
  private double totalFees;

}
