package com.ih2ome.sunflower.vo.thirdVo.smartLock.enums;

/**
 * @author Sky
 * @create 2017/12/25
 * @email sky.li@ixiaoshuidi.com
 **/
public enum SmartLockFirm {
    GUO_JIA("guojia","com.ih2ome.peony.smartlockInterface.guojia.GuoJiaSmartLock"),
    YUN_DING("yunding","com.ih2ome.peony.smartlockInterface.yunding.YunDingSmartLock");
    private String clazz;
    private String code;

    public static SmartLockFirm getByCode(String code) {
        for (SmartLockFirm smartLockFirm : SmartLockFirm.values()) {
            if (smartLockFirm.getCode().equals(code)) {
                return smartLockFirm;
            }
        }
        return null;
    }

    SmartLockFirm(String code,String clazz) {
        this.code = code;
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
