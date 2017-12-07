package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class ContractSegment implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private long renewTimes;
  private long number;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private double monthRental;
  private long houseContractId;
  private long roomContractId;
  private double deposit;

}
