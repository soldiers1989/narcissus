package com.ih2ome.watermeter.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Data;

import java.util.Date;

@Data
public class ExceptionVO {
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date date;
    private String exceotionType;//异常类型
}
