package com.ih2ome.watermeter.dao;

import com.ih2ome.watermeter.vo.WatermeterDetailVO;
import com.ih2ome.watermeter.vo.WatermeterGatewayDetailVO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface WatermeterDao<T> extends Mapper<T> {

    List<Integer> findRoomIdByCreatebyid(String id);

    /*SELECT g.smart_gateway_id FROM (SELECT
    w.smart_watermeter_id,r.`name`,w.meter_type,w.created_at,w.last_amount,w.price,w.onoff_status
            FROM
    narcissus.smart_watermeter w ,caspain_test.room r
    WHERE room_id = 3) temp,narcissus.smart_gateway_bind g WHERE smart_id = temp.smart_gateway_id;*/

    /**
     * 分散式水表列表查询
     * @param ids
     * @return
     */
    List<WatermeterDetailVO> finWatermeterByRoomIds(List<Integer> ids);

    /**
     * 根据网关id查询网关详情
     * @param id
     * @return
     */
    WatermeterGatewayDetailVO findGatewaybySmartGatewayId(String id);

    /**
     * 通过网关id查询绑定的水表
     * @param smartGatewayId
     * @return
     */
    List<WatermeterDetailVO> finWatermeterByGatewayId(String smartGatewayId);
}
