package com.ih2ome.sunflower.vo.pageVo.smartLock;

import lombok.Data;

/**
 * @author Sky
 * @create 2018/02/09
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class SmartLockGateWayHadBindInnerLockVO {
    /**
     * 网关id
     */
    private Long gatewayId;
    /**
     * 第三方网关uuid
     */
    private String gatewayThirdId;
    /**
     * 已经绑定的内门锁id
     */
    private Long innerLockId;
}
