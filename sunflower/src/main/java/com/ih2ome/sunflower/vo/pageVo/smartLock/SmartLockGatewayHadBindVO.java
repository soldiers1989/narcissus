package com.ih2ome.sunflower.vo.pageVo.smartLock;

import lombok.Data;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/25
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class SmartLockGatewayHadBindVO{
    private String gatewayId;
    private String gatewayName;
    private String gatewayCode;
    private String houseName;
    private String houseAddress;
    private List<SmartLockGatewayHadBindRoomVO> smartLockGatewayHadBindRoomVOList;

}
