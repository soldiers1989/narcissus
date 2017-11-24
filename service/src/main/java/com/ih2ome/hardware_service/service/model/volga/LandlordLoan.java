package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

@Data
public class LandlordLoan {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private long landlord;
  private double loan;
  private double interest;
  private double repayment;
  private long phase;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private long repaymentDay;
  private long payStatus;

}
