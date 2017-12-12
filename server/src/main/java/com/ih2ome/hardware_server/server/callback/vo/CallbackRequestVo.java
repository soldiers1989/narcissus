package com.ih2ome.hardware_server.server.callback.vo;

import lombok.Data;

@Data
public class CallbackRequestVo {
    private String even;//事件类别 'deviceInstall',' deviceReplace',deviceUninstall,waterGatewayOfflineAlarm,waterGatewayOnlineAlarm,watermeterAmountAsync
    private int time;//时间戳，单位 ms
    private String uuid;//设备唯一标识
    private String manufactory;//水表供应商
    private String home_id;//公寓标识
    private String gateway_uuid;//
    private String room_id;//房间标识，内门锁才有
    private Object detail;//通知的详细信息
    private String sign;//签名
}
