package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class LandlordBankCard implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String cardNo;
  private String bankName;
  private String ownerName;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private long userId;
  private String branchBank;

}
