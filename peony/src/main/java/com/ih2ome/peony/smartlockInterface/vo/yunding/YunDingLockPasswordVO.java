package com.ih2ome.peony.smartlockInterface.vo.yunding;

import com.ih2ome.common.utils.DateUtils;
import com.ih2ome.peony.smartlockInterface.vo.LockPasswordVo;
import lombok.Data;

import java.text.ParseException;

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

    public static LockPasswordVo toH2ome(YunDingLockPasswordVO yunDingLockPasswordVO) throws ParseException {
        LockPasswordVo lockPasswordVo = new LockPasswordVo();
        lockPasswordVo.setUuid(yunDingLockPasswordVO.getUuid());
        lockPasswordVo.setPwdNo(yunDingLockPasswordVO.getPasswordId());
        lockPasswordVo.setMobile(yunDingLockPasswordVO.getPhonenumber());
        lockPasswordVo.setPassword(yunDingLockPasswordVO.getPassword());
        Long permissionBegin=Long.valueOf(yunDingLockPasswordVO.getPermissionBegin())*1000L;
        String permissionBeginFormatStr=DateUtils.longToString(permissionBegin, "yyyy-MM-dd HH:mm:ss");
        lockPasswordVo.setEnableTime(permissionBeginFormatStr);
        Long permissionEnd=Long.valueOf(yunDingLockPasswordVO.getPermissionEnd())*1000L;
        String permissionEndFormatStr=DateUtils.longToString(permissionEnd, "yyyy-MM-dd HH:mm:ss");
        lockPasswordVo.setDisableTime(permissionEndFormatStr);
        return lockPasswordVo;
    }

    public static YunDingLockPasswordVO fromH2ome(LockPasswordVo lockPasswordVo) throws ParseException {
        YunDingLockPasswordVO yunDingLockPasswordVO = new YunDingLockPasswordVO();
        yunDingLockPasswordVO.setUuid(lockPasswordVo.getUuid());
        yunDingLockPasswordVO.setPasswordId(lockPasswordVo.getPwdNo());
        yunDingLockPasswordVO.setPhonenumber(lockPasswordVo.getMobile());
        yunDingLockPasswordVO.setPassword(lockPasswordVo.getPassword());
        Long permissionBeginLong = Long.valueOf(DateUtils.stringToLong(lockPasswordVo.getEnableTime(), "yyyy-MM-dd HH:mm:ss"))/1000L;
        yunDingLockPasswordVO.setPermissionBegin(String.valueOf(permissionBeginLong));
        Long permissionEndLong = Long.valueOf(DateUtils.stringToLong(lockPasswordVo.getDisableTime(), "yyyy-MM-dd HH:mm:ss"))/1000L;
        yunDingLockPasswordVO.setPermissionEnd(String.valueOf(permissionEndLong));
        return yunDingLockPasswordVO;
    }
}
