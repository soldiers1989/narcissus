package com.ih2ome.sunflower.vo.thirdVo.smartLock.guojia;

import com.ih2ome.sunflower.vo.thirdVo.smartLock.GatewayInfoVO;
import lombok.Data;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/3
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class GuoJiaGateWayVo {
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

    public GatewayInfoVO third2Standard(){
        GatewayInfoVO gatewayInfoVO = new GatewayInfoVO();
        gatewayInfoVO.setNodeKind(this.getNodeKind());
        gatewayInfoVO.setNodeNo(this.getNodeNo());
        gatewayInfoVO.setName(this.getName());
        gatewayInfoVO.setComuStatus(this.getComuStatus());
        gatewayInfoVO.setComuStatusUpdateTime(this.getComuStatusUpdateTime());
        gatewayInfoVO.setRegion(this.getRegion());
        gatewayInfoVO.setAddress(this.getAddress());
        gatewayInfoVO.setHouseCode(this.getHouseCode());
        gatewayInfoVO.setInstallTime(this.getInstallTime());
        gatewayInfoVO.setGuaranteeTimeStart(this.getGuaranteeTimeStart());
        gatewayInfoVO.setGuaranteeTimeEnd(this.getGuaranteeTimeEnd());
        gatewayInfoVO.setDescription(this.getDescription());
        return gatewayInfoVO;
    }

    public static GuoJiaGateWayVo standard2Third(GatewayInfoVO gatewayInfoVO){
        GuoJiaGateWayVo guoJiaGateWayVo = new GuoJiaGateWayVo();
        guoJiaGateWayVo.setNodeKind(gatewayInfoVO.getNodeKind());
        guoJiaGateWayVo.setNodeNo(gatewayInfoVO.getNodeNo());
        guoJiaGateWayVo.setName(gatewayInfoVO.getName());
        guoJiaGateWayVo.setComuStatus(gatewayInfoVO.getComuStatus());
        guoJiaGateWayVo.setComuStatusUpdateTime(gatewayInfoVO.getComuStatusUpdateTime());
        guoJiaGateWayVo.setRegion(gatewayInfoVO.getRegion());
        guoJiaGateWayVo.setAddress(gatewayInfoVO.getAddress());
        guoJiaGateWayVo.setHouseCode(gatewayInfoVO.getHouseCode());
        guoJiaGateWayVo.setInstallTime(gatewayInfoVO.getInstallTime());
        guoJiaGateWayVo.setGuaranteeTimeStart(gatewayInfoVO.getGuaranteeTimeStart());
        guoJiaGateWayVo.setGuaranteeTimeEnd(gatewayInfoVO.getGuaranteeTimeEnd());
        guoJiaGateWayVo.setDescription(gatewayInfoVO.getDescription());
        return guoJiaGateWayVo;
    }

}
