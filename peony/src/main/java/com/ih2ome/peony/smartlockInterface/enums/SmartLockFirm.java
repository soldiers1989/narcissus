package com.ih2ome.peony.smartlockInterface.enums;

/**
 * @author Sky
 * @create 2017/12/25
 * @email sky.li@ixiaoshuidi.com
 **/
public enum SmartLockFirm {
    GUO_JIA("com.ih2ome.peony.smartlockInterface.guojia.GuoJiaSmartLock");
    private String clazz;

    SmartLockFirm(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }
}
