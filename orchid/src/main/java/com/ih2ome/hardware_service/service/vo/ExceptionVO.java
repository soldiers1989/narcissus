package com.ih2ome.hardware_service.service.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ExceptionVO extends BaseEntity implements Serializable{
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private int daviceId;
    private Date createdAt;
    private String exceptionType;//异常类型
}
