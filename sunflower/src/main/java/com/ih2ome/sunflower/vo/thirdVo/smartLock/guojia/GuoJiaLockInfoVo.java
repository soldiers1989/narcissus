package com.ih2ome.sunflower.vo.thirdVo.smartLock.guojia;


import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockVO;
import lombok.Data;

import java.util.List;

/**
 * 果家门锁基本信息
 * @author Sky
 * @create 2017/12/26
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class GuoJiaLockInfoVo extends LockVO {
    //门锁分类
    private String lockKind;
    //门锁编码
    private String lockNo;
    //网关编码
    private String nodeNo;
    //电池电量
    private Long power;
    //电池更新时间
    private Long powerUpdateTime;
    //网关通信状态
    private String nodeComuStatus;
    //门锁通信状态
    private String comuStatus;
    //门锁通信状态更新
    private Long comuStatusUpdateTime;
    //网关接收到的锁信号强度
    private Long rssi;
    //安装地区
    private List<GuoJiaRegionVo> region;
    //安装地址
    private String address;
    //业务编码
    private String houseCode;
    //安装日期
    private Long installTime;
    //质保日期（起）
    private Long guaranteeTimeStart;
    //质保日期(止)
    private Long guaranteeTimeEnd;
    //描述
    private String description;

    public LockVO third2Standard(){
        LockVO lockInfoVO = new LockVO();
        lockInfoVO.setLockKind(this.getLockKind());
        lockInfoVO.setLockNo(this.getLockNo());
        lockInfoVO.setNodeNo(this.getNodeNo());
        lockInfoVO.setPower(this.getPower());
        lockInfoVO.setPowerUpdateTime(this.getPowerUpdateTime());
        lockInfoVO.setNodeComuStatus(this.getNodeComuStatus());
        lockInfoVO.setComuStatus(this.getComuStatus());
        lockInfoVO.setComuStatusUpdateTime(this.getComuStatusUpdateTime());
        lockInfoVO.setRssi(this.getRssi());
        lockInfoVO.setAddress(this.getAddress());
        lockInfoVO.setHouseCode(this.getHouseCode());
        lockInfoVO.setInstallTime(this.getInstallTime());
        lockInfoVO.setGuaranteeTimeStart(this.getGuaranteeTimeStart());
        lockInfoVO.setGuaranteeTimeEnd(this.getGuaranteeTimeEnd());
        lockInfoVO.setDescription(this.getDescription());
        return lockInfoVO;
    }

    public static GuoJiaLockInfoVo standart2Third(LockVO lockInfoVO){
        GuoJiaLockInfoVo guoJiaLockInfoVo = new GuoJiaLockInfoVo();
        guoJiaLockInfoVo.setLockKind(lockInfoVO.getLockKind());
        guoJiaLockInfoVo.setLockNo(lockInfoVO.getLockNo());
        guoJiaLockInfoVo.setNodeNo(lockInfoVO.getNodeNo());
        guoJiaLockInfoVo.setPower(lockInfoVO.getPower());
        guoJiaLockInfoVo.setPowerUpdateTime(lockInfoVO.getPowerUpdateTime());
        guoJiaLockInfoVo.setNodeComuStatus(lockInfoVO.getNodeComuStatus());
        guoJiaLockInfoVo.setComuStatus(lockInfoVO.getComuStatus());
        guoJiaLockInfoVo.setComuStatusUpdateTime(lockInfoVO.getComuStatusUpdateTime());
        guoJiaLockInfoVo.setRssi(lockInfoVO.getRssi());
        guoJiaLockInfoVo.setAddress(lockInfoVO.getAddress());
        guoJiaLockInfoVo.setHouseCode(lockInfoVO.getHouseCode());
        guoJiaLockInfoVo.setInstallTime(lockInfoVO.getInstallTime());
        guoJiaLockInfoVo.setGuaranteeTimeStart(lockInfoVO.getGuaranteeTimeStart());
        guoJiaLockInfoVo.setGuaranteeTimeEnd(lockInfoVO.getGuaranteeTimeEnd());
        guoJiaLockInfoVo.setDescription(lockInfoVO.getDescription());
        return guoJiaLockInfoVo;
    }
}
