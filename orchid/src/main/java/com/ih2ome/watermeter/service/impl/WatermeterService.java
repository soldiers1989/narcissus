package com.ih2ome.watermeter.service.impl;

import com.ih2ome.watermeter.dao.WatermeterDao;
import com.ih2ome.watermeter.model.Watermeter;
import com.ih2ome.watermeter.service.IWatermeterService;
import com.ih2ome.watermeter.vo.WaterMeterRecordVO;
import com.ih2ome.watermeter.vo.WatermeterDetailVO;
import com.ih2ome.watermeter.vo.WatermeterGatewayDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 水表service
 */
@Service
public class WatermeterService implements IWatermeterService{
    @Autowired(required = false)
    private WatermeterDao watermeterDao;

    //通过用户create_by_id查询用户房源id
    @Override
    public List<Integer> findRoomIdByUserId(String id) {
        //通过create_by_id查询houseID查询roomId
        //SELECT id FROM room WHERE house_id in (SELECT house_id FROM house_contract WHERE created_by_id = 3);
        List<Integer> roomIds = watermeterDao.findRoomIdByCreatebyid(id);
        return roomIds;
    }

    /**
     * 通过ids查询水表详情列表
     * @param ids
     * @return
     */
    @Override
    public List<WatermeterDetailVO> findWatermetersByids(List<Integer> ids) {
        List<WatermeterDetailVO> watermeterDetailVOS= watermeterDao.finWatermeterByRoomIds(ids);
        return watermeterDetailVOS;
    }

    /**
     * 通过水表id查询水表详情
     * @param id
     * @return
     */
    @Override
    public Watermeter findWatermeterByid(String id) {
        Watermeter watermeter = (Watermeter) watermeterDao.selectByPrimaryKey(id);
        return watermeter;
    }

    /**
     * 通过网关id查询网关详情
     * @param smartGatewayId
     * @return
     */
    @Override
    public WatermeterGatewayDetailVO findGatewaybyId(String smartGatewayId) {
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterDao.findGatewaybySmartGatewayId(smartGatewayId);
        return watermeterGatewayDetailVO;
    }

    /**
     * 通过网关id查询绑定的水表
     * @param smartGatewayId
     * @return
     */
    @Override
    public List<WatermeterDetailVO> findWatermetersByGatewayId(String smartGatewayId) {
        List<WatermeterDetailVO> watermeterDetailVOS=watermeterDao.finWatermeterByGatewayId(smartGatewayId);
        return watermeterDetailVOS;
    }

    /**
     * 水表抄表读数清单
     * @param smartWatermeterId
     * @return
     */
    @Override
    public List<WaterMeterRecordVO> findWatermeterRecordByWatermeterId(int smartWatermeterId) {
        WaterMeterRecordVO waterMeterRecordVO = new WaterMeterRecordVO();
        waterMeterRecordVO.setSmartWatermeterId(smartWatermeterId);
        List<WaterMeterRecordVO> waterMeterRecordVOS=watermeterDao.select(waterMeterRecordVO);
        return waterMeterRecordVOS;
    }


}
