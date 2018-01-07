package com.ih2ome.hardware_service.service.vo;

import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ExceptionWebVO extends BaseEntity implements Serializable{
    private int daviceId;
    private String createdAt;
    private String exceptionType;//异常类型
}
