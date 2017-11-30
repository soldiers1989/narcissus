package com.ih2ome.watermeter.service;

import com.ih2ome.watermeter.model.Watermeter;
import com.ih2ome.watermeter.vo.WaterMeterRecordVO;
import com.ih2ome.watermeter.vo.WatermeterDetailVO;
import com.ih2ome.watermeter.vo.WatermeterGatewayDetailVO;

import java.util.List;

public interface IWatermeterService {
    List<Integer> findRoomIdByUserId(String id);


    List<WatermeterDetailVO> findWatermetersByids(List<Integer> ids);

    /**
     * 通过水表id查询水表详情
     * @param id
     * @return
     */
    Watermeter findWatermeterByid(String id);

    /**
     * 通过网关id查询网关详情
     * @param smartGatewayId
     * @return
     */
    WatermeterGatewayDetailVO findGatewaybyId(String smartGatewayId);

    /**
     * 通过网关id查询绑定的水表列表
     * @param smartGatewayId
     * @return
     */
    List<WatermeterDetailVO> findWatermetersByGatewayId(String smartGatewayId);

    /**
     * 查询水表抄表列表
     * @param watermeterId
     * @return
     */
    List<WaterMeterRecordVO> findWatermeterRecordByWatermeterId(int watermeterId);
}
