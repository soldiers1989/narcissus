package com.ih2ome.sunflower.vo.pageVo.smartLock;

import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class SmartDoorLockGatewayVO extends BaseEntity implements Serializable {
    private String gatewayId;//网关id
    private String apartmentName;//公寓名称
    private String authUserName;//用户名
    private String provinceName;//省名
    private String cityName;//市名
    private String districtName;//区名
    private String areaName;//小区名
    private String houseAddress;//房源地址
    private String gatewayCode;//网关编码
    private String communicationStatus;//通讯状态
    private String installDate;//安装时间
    private String installState;//安装状态
    private String remark;//备注
    private String type;//集中式分散式
    private String guaranteeTimeStart;//质保日期（起）
    private String guaranteeTimeEnd;//质保日期（止）

}
