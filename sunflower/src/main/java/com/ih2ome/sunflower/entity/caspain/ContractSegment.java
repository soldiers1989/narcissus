package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class ContractSegment implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private long number;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private double monthRental;
  private long createdById;
  private long deletedById;
  private long houseContractId;
  private long roomContractId;
  private long updatedById;
  private long renewTimes;
  private double deposit;

}
