package com.ih2ome.hardware_service.service.model.narcissus;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartGatewayBind implements Serializable{

  private Long smartGatewayId;
  private Double smartDeviceType;
  private Long smartId;

}
