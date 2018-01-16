package com.ih2ome.hardware_service.service.entity.narcissus;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartGatewayBind implements Serializable{

  private Long smartGatewayId;
  private Long smartDeviceType;
  private Long smartId;

}
