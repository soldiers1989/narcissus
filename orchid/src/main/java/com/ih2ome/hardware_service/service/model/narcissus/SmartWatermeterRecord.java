package com.ih2ome.hardware_service.service.model.narcissus;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class SmartWatermeterRecord implements Serializable{

  @Column(name = "created_at")
  @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
  private Date createdAt;//抄表时间
  @Column(name = "device_amount")
  private int deviceAmount;//设备读数
  @Column(name = "smart_watermeter_id")
  private int smartWatermeterId;//水表设备表ID

}
