package com.ih2ome.sunflower.model.backup;

import lombok.Data;

/**
 * @author Sky
 * @create 2018/01/22
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class RoomVO {
    private String homeId;
    private String roomId;
    private String roomName;
    //房屋关联状态(1是已关联，2是未关联(默认是未关联))
    private String roomAssociationStatus = "2";

    //第三方房屋id
    private String thirdHomeId;
    //第三方房间id
    private String thirdRoomId;
    //第三方房间名称
    private String thirdRoomName;
}
