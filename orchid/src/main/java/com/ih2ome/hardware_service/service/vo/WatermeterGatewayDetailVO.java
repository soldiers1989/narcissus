package com.ih2ome.hardware_service.service.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Data;

import java.util.Date;

@Data
public class WatermeterGatewayDetailVO {
    //网关编码，房源名称，更新时间，绑定时间，绑定型号，所绑定的水表列表
    private int smartGatewayId;
    private int roomId;
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date updatedAt;
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date createdAt;
    private String brand;//绑定型号 （品牌名称）
    //private List watermeterList;//所绑定的水表列表


}
