package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartGatewayBind implements Serializable{

  private Long smartGatewayId;
  private Long smartDeviceType;
  private Long smartId;

}
