package com.ih2ome.peony.smartlockInterface.vo;

import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sky
 * @create 2017/12/27
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class LockPasswordVo extends BaseEntity implements Serializable {
    //主键
    private String id;
    //门锁编码
    private String serialNum;
    //创建时间
    private String createdAt;
    //修改时间
    private String updatedAt;
    //删除时间
    private String deletedAt;
    //创建者ID
    private String createdById;
    //修改者ID
    private String updatedById;
    //删除者ID
    private String deletedById;
    //默认为0
    private String version = "0";
    //是否被删除(默认为0,0是未删除，1是已删除)
    private String isDelete = "0";
    //门锁密码
    private String password;
    // 门锁密码类型"0=数字密码,1=门卡
    private String passwordType;
    // 数字密码类型
    private String digitPwdType;
    //密码状态(默认已启用)
    private String status = "1";
    //智能门锁ID
    private String lockId;
    //生效时间
    private String enableTime;
    //失效时间
    private String disableTime;
    //使用者姓名
    private String userName;
    //使用者手机
    private String mobile;
    //短信内容
    private String messageContent;
    //其他说明
    private String remark;
    private String pwdNo;
    private String rdata;
    private String rtime;
    //判断是集中式门锁还是分散式门锁
    private String type;
}
