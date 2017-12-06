package com.ih2ome.peony.watermeterInterface.vo;

import lombok.Data;

@Data
public class AddHomeVo {
    private String access_token;
    private int home_type;//公寓类型，1.分散式，2.集中式
    private String country;
    private String city;
    private String zone;//公寓所在区域
    private String location;//公寓具体地址信息
    private String block;//小区名
    private String home_id;
    private String home_name;//公寓名称，比如公寓编号
    private String description;//公寓描述

}
