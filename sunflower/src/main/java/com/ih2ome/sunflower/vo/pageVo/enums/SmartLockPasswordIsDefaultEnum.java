package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * @author Sky
 * @create 2018/02/02
 * @email sky.li@ixiaoshuidi.com
 **/
public enum SmartLockPasswordIsDefaultEnum {
    /**
     * 是管理员密码
     */
    PASSWORD_ISDEFAULT("1"),
    /**
     * 不是管理员密码
     */
    PASSWORD_ISNOTDEFAULT("0");

    private String code;

    SmartLockPasswordIsDefaultEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
