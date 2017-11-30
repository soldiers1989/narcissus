package com.ih2ome.watermeter.service;

import com.ih2ome.watermeter.model.Watermeter;
import com.ih2ome.watermeter.vo.*;

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

    /**
     * 通过用户id查询用户所有公寓信息
     * @param id
     * @return
     */
    List<ApartmentVO> findApartmentIdByUserId(String id);

    /**
     * 集中式通过楼层Id查询水表详情
     * @param floorId
     * @return
     */
    List<JZWatermeterDetailVO> findWatermetersByFloorId(int floorId);

    /**
     * 更改水价
     * @param price
     * @return
     */
    Boolean updataWaterPrice(int price,int watermeterId);
}
