package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

@Data
public class SmartWatermeterRecord {

  private long smartWatermeterId;
  private java.sql.Timestamp createdAt;
  private long deviceAmount;

}
