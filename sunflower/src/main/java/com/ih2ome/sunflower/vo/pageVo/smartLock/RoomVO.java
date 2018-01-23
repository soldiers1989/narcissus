package com.ih2ome.sunflower.vo.pageVo.smartLock;

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
    //房屋关联状态(0是已关联，1是未关联)
    private String roomAssociationStatus;
}
