package com.ih2ome.sunflower.vo.pageVo;

import lombok.Data;

import java.util.Date;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/2/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class Ammeter {


    /**
     * 电表的devId
     */

    private String devId;

    /**
     * 用于线下同步设备devId,线上同步不涉及
     */

    private String offLineSynCode;

    /**
     * 电表计费单价，多少钱一度电
     */

    private Double electricityPrice;

    /**
     * 电表的付费模式,BEFORE:预付费，AFTER:后付费
     */
    private String payMod;

    /**
     * 电表的剩余电量
     */
    private Double electricityLeftValue;

    /**
     * 集中器id
     */
    private String cId;
    /**
     * 节点id
     */
    private String nId;
    /**
     * 当前读数
     */
    private Double currentReading;

    /**
     * 当日公摊值
     */
    private Double todayShareNum;

    /**
     * 当月公摊值
     */
    private Double monthShareNum;

    /**
     * 当前电流
     */
    private Double electricity;

    /**
     * 当前电压
     */
    private Double voltage;
    /**
     * 在线状态 on：在线 off：离线
     */
    private String hardwareOnlineStatus;

    /**
     * 电闸状态 on：通电 off：断电
     */
    private String hardwareElectricitySwitchStatus;

    /**
     * 安装的时间
     */
    private Date installTime;

    /**
     * 分摊比例
     */
    private Double share;

    /**
     * IPZ:独立公共区域, PZ:总公共区域, ROOM:个人房间;
     */
    private String useCase;

    /**
     * 所属供应商
     */
    private String supplierProduct;

    /**
     * 所属硬件
     */
    private String hardware;

    /**
     * 主表或子表
     */
    private String ammeterType;

    /**
     * 父表
     */
    private String parentAmmeter;

    /**
     * 逾期断电
     */
    private String exceedPowerOff;

    /**
     * 被分摊电表id
     */
    private Long shareAmmeterId;

    /**
     * 旧主键
     */
    private Long oldId;

    /**
     * 是否分摊 仅作数据同步用
     */
    private Long isShare;
}
