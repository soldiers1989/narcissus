package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class HouseContractOwnerIdPictures implements Serializable{

  private long id;
  private long housecontractId;
  private long pictureId;

}
