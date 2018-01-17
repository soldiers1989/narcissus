package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserProfile implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String company;
  private String name;
  private String phone;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private long userId;
  private String address;
  private long isFranchisee;
  private long isFirstLogin;
  private String area;
  private double creditCeiling;
  private double creditUsed;
  private String idNumber;
  private long isTest;
  private String terminal;
  private String companyPostfix;
  private String urlFrom;
  private String city;
  private String companyBrand;
  private String province;
  private long avatarId;
  private java.sql.Timestamp backupsAt;
  private String email;
  private long interval;
  private long isFreeze;
  private long isOnline;
  private long level;
  private long isChannel;
  private long isLoan;
  private String referral;
  private long status;
  private long vipAction;
  private java.sql.Date vipExpireAt;
  private long vipLevel;
  private long vipStatus;
  private String legalPerson;
  private String operateCity;
  private long operateMode;
  private long idauthType;

}
