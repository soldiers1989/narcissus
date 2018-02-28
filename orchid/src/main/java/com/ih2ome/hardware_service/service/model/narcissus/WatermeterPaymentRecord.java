package com.ih2ome.hardware_service.service.model.narcissus;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 水表缴费记录
 *
 * @auther Administrator young
 * @create 2018/2/27
 */
@Data
public class WatermeterPaymentRecord implements Serializable {
    private Integer smartWatermeterId;//水表id
    private Integer lastNum;//最后读数
    private Integer amount;//用水量
    private Long price;//水费单价
    private Date meterUpdateAt;//创建时间
    private Integer meterType;//水表类型

}
