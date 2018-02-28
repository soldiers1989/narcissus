package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class MonthReportForms implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp reportDate;
  private long createdById;
  private long isDelete;
  private long receivableNum;
  private double receivableMoney;
  private long receivedNum;
  private double receivedMoney;
  private long unreceivedNum;
  private double unreceivedMoney;
  private long payableNum;
  private double payableMoney;
  private long paidNum;
  private double paidMoney;
  private long unpaidNum;
  private double unpaidMoney;
  private long houseTotal;
  private long houseRenew;
  private long houseEviction;
  private long houseUnnormal;
  private long roomTotal;
  private long roomRenew;
  private long roomEviction;
  private long roomUnnormal;
  private long disabledTotal;
  private long disabledNormal;
  private long disabledUnnormal;
  private long newHouseNum;
  private long newRoomNum;
  private long newOwnerNum;
  private long newCustomerNum;
  private long houseNum;
  private long roomNum;
  private long ownerNum;
  private long customerNum;
  private double roomRate;
  private double evictionRate;
  private double renewRate;
  private double grossProfitRate;
  private long forecastReceivableNum;
  private double forecastReceivableMoney;
  private long forecastPayableNum;
  private double forecastPayableMoney;

}
