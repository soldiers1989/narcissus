package com.ih2ome.sunflower.vo.thirdVo.smartLock;

import com.ih2ome.sunflower.vo.thirdVo.smartLock.guojia.GuoJiaRegionVo;
import lombok.Data;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/16
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class GatewayInfoVO {
    //门锁类型
    private String nodeKind;
    //网关编码
    private String nodeNo;
    //网关名称
    private String name;
    //通信状态
    private String comuStatus;
    //通信状态最近更新时间
    private String comuStatusUpdateTime;
    //安装地区
    private List<GuoJiaRegionVo> region;
    //安装地址
    private String address;
    //业务编码
    private String houseCode;
    //安装日期
    private String installTime;
    //质保日期（起）
    private String guaranteeTimeStart;
    //质保日期（止）
    private String guaranteeTimeEnd;
    //描述
    private String description;

}
