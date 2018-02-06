package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * @author Sky
 * @create 2018/02/06
 * @email sky.li@ixiaoshuidi.com
 **/
public enum SmartLockOperatorTypeEnum {
    SMARTLOCK_PASSWORD_ADD(1, "新增密码"),
    SMARTLOCK_PASSWORD_UPDATE(2, "更新密码"),
    SMARTLOCK_PASSWORD_DELETE(3, "删除密码"),
    SMARTLOCK_PASSWORD_FROZEN(4, "冻结密码"),
    SMARTLOCK_PASSWORD_UNFROZEN(5, "解冻密码");
    private Integer code;
    private String name;

    SmartLockOperatorTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
