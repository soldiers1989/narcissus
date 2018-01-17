package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class SmartWatermeterRecord implements Serializable{

  @Column(name = "created_at")
  private java.sql.Timestamp createdAt;//抄表时间
  @Column(name = "device_amount")
  private int deviceAmount;//设备读数
  @Column(name = "smart_watermeter_id")
  private int smartWatermeterId;//水表设备表ID

}
