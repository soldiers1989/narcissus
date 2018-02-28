package com.ih2ome.sunflower.vo.pageVo.watermeter;

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
