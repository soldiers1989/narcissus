package com.ih2ome.hardware_service.service.vo;

import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/2
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class SmartLockOperationRecordVO extends BaseEntity implements Serializable {
    private String lockId;//门锁id
    private String gatewayId;//网关id
    private String apartmentName;//公寓名称
    private String apartmentType;//公寓类型
    private String authUserName;//房东手机(即用户名)
    private String houseAddress;//房源地址
    private String roomNo;//房间编号
    private String customerName;//租客姓名
    private String customerPhone;//租客电话
    private String serialNum;//门锁编码
    private String communicationStatus;//通讯状态
    private String remainingBattery;//电池电量
    private String gateway;//网关编码
    private String provinceName;//省名
    private String cityName;//市名
    private String districtName;//区名
    private String areaName;//小区名
    private String operator;//操作人
    private String operateTime;//操作时间
    private String operateRecord;//操作记录
    private String type;//集中式或分散式
}
