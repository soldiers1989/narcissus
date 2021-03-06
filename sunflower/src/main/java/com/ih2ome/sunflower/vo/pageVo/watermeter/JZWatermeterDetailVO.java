package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

import java.util.List;

@Data
public class JZWatermeterDetailVO {

    //水表序列号，房间号，楼层，房源名称，产品类型-水表型号，绑定时间，当月累计水表量，电费单价，通讯状态，绑定网关，
    private int roomId;
    private String roomName;
    List<WatermeterDetailVO> watermeterDetailVOS;

}
