package com.ih2ome.peony.smartlockInterface.vo;

import lombok.Data;

/**
 * @author Sky
 * @create 2017/12/29
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class GuoJiaLockPwdVo {
    //门锁编码
    private String lock_no;
    //密码内容（密码）
    private String pwd_text;
    //密码有效期（起）
    private String valid_time_start;
    //密码有效期（止）
    private String valid_time_end;
    //密码使用人姓名
    private String pwd_user_name;
    //密码使用人手机号
    private String pwd_user_mobile;
    //密码使用人证件号
    private String pwd_user_idcard;
    //描述
    private String description;
    //辅助信息
    private String extra;

}
