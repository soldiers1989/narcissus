package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class LandlordLoan implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private double loan;
  private double interest;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private String brand;
  private long customerNum;
  private double defaults;
  private long loanStatus;
  private String location;
  private double margin;
  private double maxLoan;
  private String name;
  private long permission;
  private String phone;
  private long roomNum;
  private long landlordId;

}
