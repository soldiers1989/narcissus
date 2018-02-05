package com.ih2ome.sunflower.vo.pageVo.smartLock;

import lombok.Data;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/31
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class SmartLockGatewayAndHouseInfoVO {
    /**
     * 网关id
     */
    private long gatewayId;
    /**
     * 网关编码
     */
    private String gatewayCode;
    /**
     * 房间名称
     */
    private String houseAddress;
    /**
     * 已绑定在线门锁数量百分比
     */
    private String bindOnlineDeviceCountPercentage;
    /**
     * 连接状态
     */
    private String connectionState;

}
