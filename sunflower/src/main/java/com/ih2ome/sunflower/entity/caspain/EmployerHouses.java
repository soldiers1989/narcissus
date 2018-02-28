package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class EmployerHouses implements Serializable{

  private long id;
  private long employerId;
  private long houseId;

}
