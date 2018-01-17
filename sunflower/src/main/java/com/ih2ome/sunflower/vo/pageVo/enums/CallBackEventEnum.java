package com.ih2ome.sunflower.vo.pageVo.enums;

public enum CallBackEventEnum {
    DEVICE_INSTALL("deviceinstall"),
    DEVICE_REPLACE("deviceReplace"),
    DEVICE_UNINSTALL("deviceUninstall") ,
    WATER_GATEWAY_OFF_LINE_ALARM("waterGatewayOfflineAlarm") ,
    WATER_GATEWAY_ON_LINE_ALARM("waterGatewayOnlineAlarm"),
    WATERMETER_AMOUNT_ASYNC("watermeterAmountAsync");

    private String event;
    CallBackEventEnum(String event){
        this.event = event;
    }
    public String getEvent() {
        return event;
    }
}
