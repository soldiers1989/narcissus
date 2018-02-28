package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * @author Sky
 * @create 2018/02/02
 * @email sky.li@ixiaoshuidi.com
 **/
public enum SmartLockPasswordValidTypeEnum {
    PASSWORD_FOREVER("1", "永久密码"),
    PASSWORD_TIMEVALID("2", "时间有效密码");;
    private String code;
    private String name;

    SmartLockPasswordValidTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
