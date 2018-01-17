package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

import java.util.List;

@Data
public class JZWatermeterListVo {
    //公寓列表
    private List<ApartmentVO> apartmentVOS;
    //集中式具体房间名称,水表列表
    private List<JZWatermeterDetailVO> jzWatermeterDetailVOS;


}
