package com.ih2ome.sunflower.vo.pageVo.smartLock;

import lombok.Data;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/2/2
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class RoomAndPublicZoneVo {
    /**
     * 门锁id
     */
    private long smartLockId;
    /**
     * 房间编号
     */
    private String roomNo;
    /**
     * 在线离线
     */
    private String communicationStatus;
    /**
     * 电量
     */
    private String powerRate;
}
