package com.ih2ome.hardware_service.service.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Data;

import java.util.Date;

@Data
public class ExceptionVO {
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date createdAt;
    private String exceptionType;//异常类型
}
