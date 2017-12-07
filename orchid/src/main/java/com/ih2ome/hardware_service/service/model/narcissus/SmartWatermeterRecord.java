package com.ih2ome.hardware_service.service.model.narcissus;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartWatermeterRecord implements Serializable{

  private Long smartWatermeterId;
  private java.sql.Timestamp createdAt;
  private Long deviceAmount;

}
