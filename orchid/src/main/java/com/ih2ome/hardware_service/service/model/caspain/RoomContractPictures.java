package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoomContractPictures implements Serializable{

  private long id;
  private long roomcontractId;
  private long pictureId;

}