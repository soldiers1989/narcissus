package com.ih2ome.sunflower.vo.thirdVo.watermeter.enums;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/1
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum WATERMETER_FIRM {
    YUN_DING("com.ih2ome.hardware_service.service.peony.watermeterInterface.yunding.YunDingWatermeter");
    private String clazz;

    WATERMETER_FIRM(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }
}
