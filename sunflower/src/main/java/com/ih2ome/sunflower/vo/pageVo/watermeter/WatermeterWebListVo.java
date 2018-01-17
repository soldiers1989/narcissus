package com.ih2ome.sunflower.vo.pageVo.watermeter;

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
public class WatermeterWebListVo extends BaseEntity implements Serializable {
    private String watermeterId;//水表id
    private String gatewayId;//网关id
    private String apartmentName;//公寓名称
    private String apartmentType;//公寓类型
    private String authUserName;//用户名(房东手机号)
//    private String landlordName;//房东姓名
    private String provinceName;//省名
    private String cityName;//市名
    private String districtName;//区名
    private String areaName;//小区名
    private String houseAddress;//房源地址
    private String roomNo;//房间编号
    private String customerName;//租客姓名
    private String customerPhone;//租客电话
    private String deviceName;//设备号（水表序列号）
    private String communicationStatus;//通讯状态
    private String updatedAt;
//    private String isHub;//是否是主表
    private String amount;//水表读数
    private String type;//集中式或分散式
    private String gatewayUuid;//水表网关
}
