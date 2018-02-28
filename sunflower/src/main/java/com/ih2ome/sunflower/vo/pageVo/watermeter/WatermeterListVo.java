package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

import java.util.ArrayList;

@Data
public class WatermeterListVo {
    //分散式具体房间名称,水表列表
    private ArrayList<HMWatermeterListVO> list;
}
