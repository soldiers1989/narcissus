package com.ih2ome.watermeter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.watermeter.dao.WatermeterMapper;
import com.ih2ome.watermeter.model.SmartWatermeterRecord;
import com.ih2ome.watermeter.model.Watermeter;
import com.ih2ome.watermeter.service.WatermeterService;
import com.ih2ome.watermeter.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 水表service
 */
@Service
public class WatermeterServiceImpl implements WatermeterService {
    @Resource
    private WatermeterMapper watermeterDao;

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
        //Watermeter watermeter = (Watermeter) watermeterDao.selectByPrimaryKey(id);
        return null;
    }

    /**
     * 通过网关id查询网关详情
     * @param smartGatewayId
     * @return
     */
    @Override
    public WatermeterGatewayDetailVO findGatewaybyId(int smartGatewayId) {
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterDao.findGatewaybySmartGatewayId(smartGatewayId);
        return watermeterGatewayDetailVO;
    }

    /**
     * 通过网关id查询绑定的水表
     * @param smartGatewayId
     * @return
     */
    @Override
    public List<WatermeterDetailVO> findWatermetersByGatewayId(int smartGatewayId) {
        List<WatermeterDetailVO> watermeterDetailVOS=watermeterDao.findWatermeterByGatewayId(smartGatewayId);
        return watermeterDetailVOS;
    }

    /**
     * 水表抄表读数清单
     *
     * @param smartWatermeterId
     * @param page
     *@param count  @return
     */
    @Override
    public PageResult<SmartWatermeterRecord> findWatermeterRecordByWatermeterId(int smartWatermeterId, int page, int count) {
        // 开启分页
        PageHelper.startPage(page, count);

        // 根据用户名查询，并且按照创建时间降序排列
        /*Example example = new Example(SmartWatermeterRecord.class);
        // 创建查询条件对象
        example.createCriteria().andEqualTo("smartWatermeterId",smartWatermeterId);
        // 实现排序
        example.setOrderByClause("created_at desc");
        List<SmartWatermeterRecord> waterMeterRecordVOS=watermeterDao.selectByExample(example);*/
        List<SmartWatermeterRecord> waterMeterRecordVOS=watermeterDao.findWatermeterRecordByWatermeterId(smartWatermeterId);
        PageInfo<SmartWatermeterRecord> info = new PageInfo<>(waterMeterRecordVOS);

        // 返回分页结果对象
        return new PageResult<>(info.getTotal(), waterMeterRecordVOS);

    }

    /**
     * 查询公寓信息
     * @param id
     * @return
     */
    @Override
    public List<ApartmentVO> findApartmentIdByUserId(int id) {
        //查询公寓信息
        List<ApartmentVO> apartmentVOS=watermeterDao.findApartmentByUserId(id);
        return apartmentVOS;
    }

    /**
     * 查询集中式水表列表通过楼层id
     * @param floorId
     * @return
     */
    @Override
    public List<JZWatermeterDetailVO> findWatermetersByFloorId(int floorId) {
        //通过楼层ids查询水表
        /*List<Integer> floorIds = new ArrayList<Integer>();
        for (ApartmentVO apartmentVO : apartmentVOS) {
            floorIds.add(apartmentVO.getFloorId());
        }*/
        List<JZWatermeterDetailVO> jzWatermeterDetailVOS = watermeterDao.findWatermetersByFloorId(floorId);
        return jzWatermeterDetailVOS;

    }

    /**
     * 改水价
     * @param price
     * @return
     */
    @Override
    public Boolean updataWaterPrice(int price,int watermeterId) throws AmmeterException, ClassNotFoundException, IllegalAccessException, InstantiationException{
        int flag= watermeterDao.updataWaterPrice(price,watermeterId);
        if(flag==1) {
            return true;
        }
        return false;
    }

    /**
     * 通过公寓id查询水表网关
     * @param apartmentId
     * @return
     */
    @Override
    public List<JZWatermeterGatewayVO> findGatewaysByApartmentId(int apartmentId) {
        List<JZWatermeterGatewayVO> jzWatermeterGatewayVOS = watermeterDao.findGatewayByApartmentId(apartmentId);
        return jzWatermeterGatewayVOS;
    }

    /**
     * 分散式通过用用户id查询水表列表
     * @param id
     * @return
     */
    @Override
    public List<WatermeterDetailVO> findWatermetersByid(int id) {
        return watermeterDao.findWatermetersByUserId(id);
    }

    /**
     * 筛选抄表记录根据时间区间
     * @param watermeterId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<SmartWatermeterRecord> findWatermeterRecordByWatermeterIdAndTime(int watermeterId, String startTime, String endTime) {
        // 根据用户名查询，并且按照创建时间降序排列
        /*Example example = new Example(SmartWatermeterRecord.class);
        // 创建查询条件对象
        example.createCriteria().andEqualTo("smartWatermeterId",watermeterId);
        example.createCriteria().andBetween("created_at",startTime,endTime);
        // 实现排序
        example.setOrderByClause("created_at desc");
        List<SmartWatermeterRecord> waterMeterRecordVOS=watermeterDao.selectByExample(example);*/
        List<SmartWatermeterRecord> waterMeterRecordVOS=watermeterDao.findWatermeterRecordByWatermeterIdAndTime(watermeterId,startTime,endTime);
        return waterMeterRecordVOS;
    }

    /**
     * 分散式用户房源
     * @param id
     * @return
     */
    @Override
    public List<HouseVO> findHouseByUserId(int id) {
        return watermeterDao.findHouseByUserId(id);
    }

    /**
     * 水表异常记录
     * @param watermeterId
     * @return
     */
    @Override
    public List<ExceptionVO> findWatermeterException(int watermeterId) {
        return watermeterDao.findWatermeterExceptionByWaterId(watermeterId);
    }

    @Override
    public List<ExceptionVO> findWatermeterGatewayException(int gatewayId) {
        return watermeterDao.findWatermeterGatewayExceptionByGatewayId(gatewayId);
    }

}
