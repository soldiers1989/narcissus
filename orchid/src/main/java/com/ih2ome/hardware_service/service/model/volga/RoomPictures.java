package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoomPictures implements Serializable{

  private long id;
  private long roomId;
  private long pictureId;

}
