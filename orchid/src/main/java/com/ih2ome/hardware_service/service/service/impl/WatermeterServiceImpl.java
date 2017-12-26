package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ih2ome.hardware_service.service.dao.WatermeterMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartGatewayBind;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeterRecord;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import com.ih2ome.hardware_service.service.vo.*;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
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
        List<Integer> roomIds = watermeterDao.findRoomIdByCreatebyid(id);
        return roomIds;
    }

    /**
     * 通过ids查询水表详情列表
     * @param ids
     * @return
     */
    @Override
    public List<HMWatermeterListVO> findWatermetersByids(List<Integer> ids) {
        List<HMWatermeterListVO> watermeterDetailVOS= watermeterDao.finWatermeterByRoomIds(ids);
        return watermeterDetailVOS;
    }

    /**
     * 通过水表id查询水表详情
     * @param id
     * @return
     */
    @Override
    public WatermeterVO findWatermeterByid(String id) {
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
    public List<HMWatermeterListVO> findWatermetersByGatewayId(int smartGatewayId) {
        List<HMWatermeterListVO> watermeterDetailVOS=watermeterDao.findWatermeterByGatewayId(smartGatewayId);
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
     * 查询集中式水表列表通过楼层id
     * @param floorId
     * @return
     */
    @Override
    public List<JZWatermeterDetailVO> findWatermetersByFloorId(int floorId) {
        //通过楼层ids查询水表
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
        Integer flag= watermeterDao.updataWaterPrice(price,watermeterId);

        if(flag != null) {
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
    public List<HMWatermeterListVO> findWatermetersByid(int id) {
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
     * 水表异常记录
     * @param watermeterId
     * @return
     */
    @Override
    public List<ExceptionVO> findWatermeterException(int watermeterId) {
        return watermeterDao.findWatermeterExceptionByWaterId(watermeterId);
    }

    /**
     * 网关异常记录by网关id
     * @param gatewayId
     * @return
     */
    @Override
    public List<ExceptionVO> findWatermeterGatewayException(int gatewayId) {
        return watermeterDao.findWatermeterGatewayExceptionByGatewayId(gatewayId);
    }


    /**
     * 发送抄表请求
     * @param watermeterId
     * @return
     */
    @Override
    public String readWatermeterLastAmountByWatermeterId(int watermeterId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException{
        //查询水表uuid，和供应商
        WatermeterRecordParamsVo params=watermeterDao.findWatermeterRecordParamsByWatermeterId(watermeterId);
        //查询水表实时抄表记录
        IWatermeter iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        String reslut= iWatermeter.readWatermeter(params.getUuid(),params.getManufactory());

        return reslut;
    }




    /**
     * 更新水表抄表读数
     * @param uuid
     * @param amount
     * @param time
     */
    @Override
    public void updataWaterLastAmount(String uuid, int amount, int time) {
        watermeterDao.updataWaterLastAmount(uuid, amount, time);
    }


    /**
     * 添加水表
     * @param smartWatermeter
     */
    @Override
    public void createSmartWatermeter(SmartWatermeter smartWatermeter) {
        watermeterDao.addSmartWatermeter(smartWatermeter);
    }

    /**
     * 添加网关绑定
     * @param smartGatewayBind
     */
    @Override
    public void addSmartGatewayBind(SmartGatewayBind smartGatewayBind) {
        watermeterDao.addSmartGatewayBind(smartGatewayBind);
    }

    /**
     * 查询HouseCreatedByByHouseId
     * @param houseId
     * @return
     */
    @Override
    public int findHouseCreatedByByHouseId(Long houseId) {
        return watermeterDao.selectHouseCreatedByByHouseId(houseId);
    }

    /**
     * 查询HouseCreatedByByapartmentId
     * @param apartmentId
     * @return
     */
    @Override
    public Integer findApartmentCreatedByByApartmentId(Long apartmentId) {
        return watermeterDao.selectApartmentCreatedByByApartmentId(apartmentId);
    }

    /**
     * 查询水表idByuuid
     * @param uuid
     * @return
     */
    @Override
    public Integer findWatermeterIdByUuid(String uuid) {
        Integer watermeterId=watermeterDao.findWatermetersByUuId(uuid);
        return watermeterId;
    }

    /**
     * 查询水表读数by水表id
     * @param watermeterId
     * @return
     */
    @Override
    public Integer findWatermeterLastAmountByWatermeterId(int watermeterId) {
        return watermeterDao.findWatermeterAmountByWatermeterId(watermeterId);
    }

    /**
     * 更新水表在线离线状态
     * @param uuid
     * @param code
     */
    @Override
    public void updataWatermerterOnoffStatus(String uuid, Integer code) {
        watermeterDao.updataWatermerterOnoffStatusByUuid(uuid,code);
    }

    /**
     * 查询水表所有的uuidandManufactory
     * @return
     */
    @Override
    public List<UuidAndManufactoryVO> findWatermeterUuidAndManufactory() {
        return watermeterDao.selectWatermeterUuidAndManufactory();
    }

    /**
     * 最近一次抄表时间
     * @param uuid
     * @return
     */
    @Override
    public Timestamp findWatermeterMeterUpdatedAt(String uuid) {
        return watermeterDao.selectWatermeterMeterUpdatedAt(uuid);
    }

    /**
     * 集中式水表listby网关id
     * @param smartGatewayId
     * @return
     */
    @Override
    public List<JZWatermeterDetailVO> findJzWatermetersByGatewayId(int smartGatewayId) {
        return watermeterDao.selectJzWatermetersByGatewayId(smartGatewayId);
    }

    @Override
    public List<JZWatermeterGatewayVO> findGatewaysByUserId(int userId) {
        return watermeterDao.selectGatewaysByUserId(userId);
    }

    /**
     * 查询水表在线状态byuuid
     * @param uuid
     * @return
     */
    @Override
    public Integer findWatermeterOnOffStatusByUuid(String uuid) {
        return watermeterDao.selectWatermeterOnOffStatusByUuid(uuid);
    }

    /**
     * 查询所有水表id
     * @return
     */
    @Override
    public List<Integer> findAllWatermeterIds() {
        return watermeterDao.selectAllWatermeterIds();
    }

    /**
     * 查询水表月初抄表读数
     * @param watermeterId
     * @return
     */
    @Override
    public Integer findMeterAmountByWatermeterId(Integer watermeterId) {
        return watermeterDao.selectMeterAmountByWatermeterId(watermeterId);
    }

    /**
     * 更新水表月初读数
     * @param watermeterId
     * @param meterAmount
     */
    @Override
    public void updataWatermeterMeterAmount(Integer watermeterId, Integer meterAmount) {
        watermeterDao.updataWatermeterMeterAmount(watermeterId,meterAmount);
    }


}
