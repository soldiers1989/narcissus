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
    private long homeId;
    /**
     * 网关id
     */
    private long gatewayId;
    /**
     * 外门锁
     */
    private OutSmartLockVo outSmartLockVo;
    /**
     * 房源名称
     */
    private String homeName;
    /**
     * 楼层列表
     */
    private List <FloorVo> floorVoList;
}
