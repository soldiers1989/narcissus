package com.ih2ome.hardware_service.service.model.caspain;

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
  private double totalFees;
  private long addCustomerNum;
  private long addOwnerNum;
  private double income;
  private double outcome;
  private long renewCustomerNum;
  private long renewOwnerNum;
  private long unrentCustomerNum;
  private long unrentOwnerNum;

}
