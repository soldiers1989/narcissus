package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class ShuididaiCreditapplication {

  private long id;
  private java.sql.Timestamp createdAt;
  private double credit;
  private String name;
  private String idNumber;
  private String address;
  private String phone;
  private long status;
  private java.sql.Timestamp handleAt;
  private long isDeleted;
  private long handleById;
  private long userId;
  private String company;

}
