package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

import java.util.List;

@Data
public class SmartLock {

  private String smartLockId;
  private String sn;
  private String mac;
  private String model;
  private String modelName;
  private String power;
  private String powerRefreshtime;
  private String onoffTime;
  private String lqi;
  private String lqiRefreshtime;
  private String bindTime;
  private String guaranteeTimeStart;
  private String guaranteeTimeEnd;
  private String versions;
  private String description;
  private String gatewayUuid;
  private SmartDeviceV2 smartDeviceV2;
  private List<SmartLockPassword> smartLockPasswordList;

}
