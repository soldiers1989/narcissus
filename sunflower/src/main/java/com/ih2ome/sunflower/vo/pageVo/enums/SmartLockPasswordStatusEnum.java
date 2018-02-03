package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * @author Sky
 * @create 2018/02/02
 * @email sky.li@ixiaoshuidi.com
 **/
public enum SmartLockPasswordStatusEnum {
    PASSWORD_ORIGINAL("1", "初始状态"),
    PASSWORD_START("2", "已生效"),
    PASSWORD_WILLSTART("3", "将生效"),
    PASSWORD_END("4", "已过期"),
    PASSWORD_FROZEN("5", "已冻结"),
    PASSWORD_DELETED("6", "已删除");
    private String code;
    private String name;

    SmartLockPasswordStatusEnum(String code, String name) {
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
