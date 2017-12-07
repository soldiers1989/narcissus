package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class EmployerHouses implements Serializable{

  private long id;
  private long employerId;
  private long houseId;

}
