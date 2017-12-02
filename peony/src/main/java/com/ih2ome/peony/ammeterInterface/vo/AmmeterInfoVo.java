package com.ih2ome.peony.ammeterInterface.vo;

import lombok.Data;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/27
 * @Emial Lucius.li@ixiaoshuidi.com
 */

@Data
public class AmmeterInfoVo {
    private String id;//电表id
    private String ammeterName;//电表序列号
    private String online;//是否在线
    private String electrifyStatus;//通电状态
    private String wifiName;//wifi名称
    private String wifiPassword;//wifi密码
    private String useCase;//使用场景
    private String payMod;//付费模式
    private String powerRate;//电费单价
    private Double surplus;//剩余电量
    private Double allPower;//当前度数
    private Double voltage;//当前电压
    private Double powerDay;//今日耗电
    private String lastTime;//更新时间
    private Double powerOutput;//功率
    private Double current;//当前电流
    private String powerMonth;//当月耗电
    private String customerName;//租客姓名
    private String customerPhone;//租客电话
    private String checkInDate;//入住时间
    private String address;//房源地址
    private String roomNo;//房源编号

    public void initPowerOutput(){
        this.powerOutput = this.voltage*this.current;
    }
}
