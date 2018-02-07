package com.ih2ome.sunflower.vo.pageVo.smartLock;

import lombok.Data;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/2/5
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class OutSmartLockVo {
    /**
     * 门锁id
     */
    private long smartLockId;
    /**
     * 在线离线
     */
    private String communicationStatus;
    /**
     * 电量
     */
    private String powerRate;

    private String lockName;
}
