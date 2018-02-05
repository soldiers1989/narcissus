package com.ih2ome.sunflower.vo.pageVo.smartLock;

import lombok.Data;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/2/5
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class FloorVo {
    /**
     * 楼层id
     */
    private long floorId;
    /**
     * 楼层名称
     */
    private String floorName;
    /**
     * 房间列表
     */
    private List<RoomAndPublicZoneVo> roomAndPublicZoneVoList;
}
