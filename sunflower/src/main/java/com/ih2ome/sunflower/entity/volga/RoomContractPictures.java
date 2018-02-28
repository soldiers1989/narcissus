package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoomContractPictures implements Serializable{

  private long id;
  private long roomcontractId;
  private long pictureId;

}
