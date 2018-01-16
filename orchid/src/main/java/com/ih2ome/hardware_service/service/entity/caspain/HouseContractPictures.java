package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class HouseContractPictures implements Serializable{

  private long id;
  private long housecontractId;
  private long pictureId;

}
