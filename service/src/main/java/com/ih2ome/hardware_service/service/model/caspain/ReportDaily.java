package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class ReportDaily {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private java.sql.Timestamp reportDate;
  private long total;
  private long news;
  private long delete;
  private long rent;
  private long empty;
  private long newContract;
  private long renewTimes;
  private double occupancyRate;
  private double averagePrice;
  private long newCustomer;
  private long followCustomer;
  private long signCustomer;
  private double processCustomer;
  private double businessCustomer;
  private long totalBill;
  private long receivableBill;
  private long payableBill;
  private long unreceivedBill;
  private long unpaidBill;
  private double receivableFee;
  private double unreceivedFee;
  private double returnedRate;
  private double totalIncome;
  private double rentalIncome;
  private double depositIncome;
  private double utilitiesIncome;
  private double totalOutlay;
  private double rentalOutlay;
  private double depositOutlay;
  private double utilitiesOutlay;
  private double forecastIncome;
  private double forecastOutlay;
  private double forecastExposure;
  private long createdById;
  private long deletedById;
  private long updatedById;

}
