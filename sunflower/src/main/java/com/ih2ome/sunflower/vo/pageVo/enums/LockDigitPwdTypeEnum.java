package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * @author Sky
 * @create 2017/12/28
 * @email sky.li@ixiaoshuidi.com
 **/
public enum LockDigitPwdTypeEnum {
    RENT_PERSON("房客","0"),
    CLEAN_PERSON("保洁","1"),
    OWNER_PERSON("业主","2");
    private String name;
    private String code;

    LockDigitPwdTypeEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static String getByCode(String code) {
        for (LockDigitPwdTypeEnum lockDigitPwdTypeEnum : LockDigitPwdTypeEnum.values()) {
            if (lockDigitPwdTypeEnum.getCode().equals(code)) {
                return lockDigitPwdTypeEnum.getName();
            }
        }
        return null;
}

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
