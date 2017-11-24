package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class RoomReserve {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
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
  private long createdById;
  private long deletedById;
  private long updatedById;
  private long roomId;

}
