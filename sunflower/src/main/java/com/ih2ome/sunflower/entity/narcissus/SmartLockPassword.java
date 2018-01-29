package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

@Data
public class SmartLockPassword {

  private long smartLockPasswordId;
  private long smartLockId;
  private long providerCode;
  private String threeId;
  private String lock3Id;
  private String createdAt;
  private String name;
  private double status;
  private double pwdType;
  private String validTimeStart;
  private String validTimeEnd;
  private String sendToMobile;
  private String pwdUserName;
  private String password;
  private String description;
  private String isDefault;
  private String extra;

}
