package com.ih2ome.sunflower.vo.pageVo.watermeter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ExceptionVO extends BaseEntity implements Serializable{
    private int daviceId;
    private Date createdAt;
    private String exceptionType;//异常类型
    //状态
    private String status;
}
