package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class DjangoContentType implements Serializable{

  private long id;
  private String appLabel;
  private String model;

}
