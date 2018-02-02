package com.ih2ome.sunflower.vo.pageVo.smartLock;

import lombok.Data;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/2/2
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class SmartLockHadBindHouseVo {
    /**
     * 房源id
     */
    private String homeId;
    /**
     * 网关id
     */
    private String gatewayId;
    /**
     * 房源名称
     */
    private String homeName;
    /**
     * 房间列表
     */
    private List<RoomAndPublicZoneVo> roomAndPublicZoneVoList;
}
