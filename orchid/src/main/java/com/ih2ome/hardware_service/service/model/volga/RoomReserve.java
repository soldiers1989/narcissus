package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoomReserve implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private String status;
  private String customerName;
  private String customerPhone;
  private java.sql.Date contractDate;
  private double earnestMoney;
  private String comments;
  private double refund;
  private String reason;
  private long contractId;
  private long roomId;

}
