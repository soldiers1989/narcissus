package com.ih2ome.peony.smartlockInterface.vo.guojia;

import com.ih2ome.peony.smartlockInterface.vo.LockPasswordVo;
import lombok.Data;

/**
 * @author Sky
 * @create 2017/12/29
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class GuoJiaLockPasswordVO {
    //门锁编码
    private String lockNo;
    //密码编号
    private String pwdNo;
    //密码内容（密码）
    private String pwdText;
    //密码有效期（起）
    private String validTimeStart;
    //密码有效期（止）
    private String validTimeEnd;
    //密码使用人姓名
    private String pwdUserName;
    //密码使用人手机号
    private String pwdUserMobile;
    //密码使用人证件号
    private String pwdUserIdcard;
    //描述
    private String description;
    //辅助信息
    private String extra;

    public static LockPasswordVo toH2ome(GuoJiaLockPasswordVO guoJiaLockPasswordVO){
        LockPasswordVo lockPasswordVo=new LockPasswordVo();
        lockPasswordVo.setSerialNum(guoJiaLockPasswordVO.getLockNo());
        lockPasswordVo.setPwdNo(guoJiaLockPasswordVO.getPwdNo());
        lockPasswordVo.setPassword(guoJiaLockPasswordVO.getPwdText());
        lockPasswordVo.setEnableTime(guoJiaLockPasswordVO.getValidTimeStart());
        lockPasswordVo.setDisableTime(guoJiaLockPasswordVO.getValidTimeEnd());
        lockPasswordVo.setUserName(guoJiaLockPasswordVO.getPwdUserName());
        lockPasswordVo.setMobile(guoJiaLockPasswordVO.getPwdUserMobile());
        lockPasswordVo.setRemark(guoJiaLockPasswordVO.getExtra());
        return lockPasswordVo;
    }

    public static GuoJiaLockPasswordVO fromH2ome(LockPasswordVo lockPasswordVo){
        GuoJiaLockPasswordVO guoJiaLockPasswordVO=new GuoJiaLockPasswordVO();
        guoJiaLockPasswordVO.setLockNo(lockPasswordVo.getSerialNum());
        guoJiaLockPasswordVO.setPwdNo(lockPasswordVo.getPwdNo());
        guoJiaLockPasswordVO.setPwdText(lockPasswordVo.getPassword());
        guoJiaLockPasswordVO.setValidTimeStart(lockPasswordVo.getEnableTime());
        guoJiaLockPasswordVO.setValidTimeEnd(lockPasswordVo.getDisableTime());
        guoJiaLockPasswordVO.setPwdUserName(lockPasswordVo.getUserName());
        guoJiaLockPasswordVO.setPwdUserMobile(lockPasswordVo.getMobile());
        guoJiaLockPasswordVO.setExtra(lockPasswordVo.getRemark());
        return guoJiaLockPasswordVO;
    }

}
