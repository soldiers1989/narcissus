package com.ih2ome.peony.smartlockInterface.vo.yunding;

import com.ih2ome.peony.smartlockInterface.vo.LockPasswordVo;
import lombok.Data;

/**
 * @author Sky
 * @create 2018/01/16
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class YunDingLockPasswordVO {
    //公寓Id
    private String homeId;
    //房间Id
    private String roomId;
    //门锁 uuid
    private String uuid;
    //密码 id
    private String passwordId;
    //密码生成成功后要发送到的目标电话号码
    private String phonenumber;
    //是否是管理员密码(0否，1是)
    private String isDefault;
    //短信发送激活码是否携带公寓信息(默认不携带)
    private Boolean isSendLocation;
    //开锁密码（6 位）
    private String password;
    //密码的名称
    private String name;
    //密码有效期开始(单位S)
    private String permissionBegin;
    //密码有效期结束(单位S)
    private String permissionEnd;

    public static LockPasswordVo toH2ome(YunDingLockPasswordVO yunDingLockPasswordVO) {
        LockPasswordVo lockPasswordVo = new LockPasswordVo();
        lockPasswordVo.setUuid(yunDingLockPasswordVO.getUuid());
        lockPasswordVo.setPwdNo(yunDingLockPasswordVO.getPasswordId());
        lockPasswordVo.setMobile(yunDingLockPasswordVO.getPhonenumber());
        lockPasswordVo.setPassword(yunDingLockPasswordVO.getPassword());
        lockPasswordVo.setEnableTime(String.valueOf(Long.valueOf(yunDingLockPasswordVO.getPermissionBegin()) * 1000));
        lockPasswordVo.setDisableTime(String.valueOf(Long.valueOf(yunDingLockPasswordVO.getPermissionEnd()) * 1000));
        return lockPasswordVo;
    }

    public static YunDingLockPasswordVO fromH2ome(LockPasswordVo lockPasswordVo) {
        YunDingLockPasswordVO yunDingLockPasswordVO = new YunDingLockPasswordVO();
        yunDingLockPasswordVO.setUuid(lockPasswordVo.getUuid());
        yunDingLockPasswordVO.setPasswordId(lockPasswordVo.getPwdNo());
        yunDingLockPasswordVO.setPhonenumber(lockPasswordVo.getMobile());
        yunDingLockPasswordVO.setPassword(lockPasswordVo.getPassword());
        yunDingLockPasswordVO.setPermissionBegin(String.valueOf(Long.valueOf(lockPasswordVo.getEnableTime()) / 1000));
        yunDingLockPasswordVO.setPermissionEnd(String.valueOf(Long.valueOf(lockPasswordVo.getDisableTime()) / 1000));
        return null;
    }
}
