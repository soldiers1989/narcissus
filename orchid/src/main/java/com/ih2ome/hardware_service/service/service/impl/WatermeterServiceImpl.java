package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ih2ome.hardware_service.service.dao.WatermeterMapper;
import com.ih2ome.sunflower.entity.narcissus.SmartGatewayBind;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeter;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeterRecord;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.vo.pageVo.watermeter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * 水表service
 */
@Service
public class WatermeterServiceImpl implements WatermeterService {

    private static final Logger Log = LoggerFactory.getLogger(WatermeterServiceImpl.class);

    @Resource
    private WatermeterMapper watermeterDao;

    //通过用户create_by_id查询用户房源id
    @Override
    public List<Integer> findRoomIdByUserId(String id) {
        Log.info(" 查询用户房源id，用户id：{}",id);
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
        Log.info(" 通过水表id查询水表详情，水表ids：{}",ids);
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
        Log.info(" 通过水表id查询水表详情，水表id：{}",id);
        SmartWatermeter smartWatermeter = watermeterDao.selectByPrimaryKey(id);
        return null;
    }

    /**
     * 通过网关id查询网关详情
     * @param smartGatewayId
     * @return
     */
    @Override
    public WatermeterGatewayDetailVO findGatewaybyId(int smartGatewayId) {
        Log.info("通过网关id查询网关详情，网关smartGatewayId：{}",smartGatewayId);
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterDao.findGatewaybySmartGatewayId(smartGatewayId);
        return watermeterGatewayDetailVO;
    }
    /**
     * 通过网关id查询网关详情
     * @param smartGatewayId
     * @return
     */
    @Override
    public WatermeterGatewayDetailVO findhmGatewaybyId(int smartGatewayId) {
        Log.info("通过网关id查询网关详情，网关smartGatewayId：{}",smartGatewayId);
        WatermeterGatewayDetailVO watermeterGatewayDetailVO = watermeterDao.findhmGatewaybySmartGatewayId(smartGatewayId);
        return watermeterGatewayDetailVO;
    }

    /**
     * 查询水表idbygatewayId
     * @param uuid
     * @return
     */
    @Override
    public List<String> findWatermeterIdByGatewayUuid(String uuid) {
        return watermeterDao.selectWatermeterIdByGatewayUuid(uuid);
    }

    /**
     * 通过网关id查询绑定的水表
     * @param smartGatewayId
     * @return
     */
    @Override
    public List<HMWatermeterListVO> findWatermetersByGatewayId(int smartGatewayId) {
        Log.info("通过网关id查询绑定的水表，水表网关smartGatewayId：{}",smartGatewayId);
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
        Log.info("查询水表抄表记录,水表smartWatermeterId：{}",smartWatermeterId);
        // 开启分页
        PageHelper.startPage(page, count);

        // 根据用户名查询，并且按照创建时间降序排列
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
        Log.info("查询集中式水表列表,楼层floorId：{}",floorId);
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
        Log.info("修改水价,水表id：{}",watermeterId);
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
        Log.info("查询水表网关,公寓apartmentId：{}"+apartmentId);
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
        Log.info("分散式查询水表列表,用户id：{}",id);
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
        Log.info("水表抄表记录筛选,水表watermeterId：{},开始时间：{},截止时间：{}",watermeterId,startTime,endTime);
        // 根据用户名查询，并且按照创建时间降序排列
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
        Log.info("水表异常记录,水表watermeterId：{}",watermeterId);
        return watermeterDao.findWatermeterExceptionByWaterId(watermeterId);
    }

    /**
     * 网关异常记录by网关id
     * @param gatewayId
     * @return
     */
    @Override
    public List<ExceptionVO> findWatermeterGatewayException(int gatewayId) {
        Log.info("网关异常记录,网关id：",gatewayId);
        return watermeterDao.findWatermeterGatewayExceptionByGatewayId(gatewayId);
    }


    /**
     * 发送抄表请求
     * @param watermeterId
     * @return
     */
    @Override
    public String readWatermeterLastAmountByWatermeterId(int watermeterId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException{
        Log.info("水表watermeterId：{}",watermeterId);
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
    public void updataWaterLastAmount(String uuid, int amount, Long time) {
        Log.info("更新水表读数,水表uuid:{},水表读数:{},更新时间：{}",uuid,amount,time);
        watermeterDao.updataWaterLastAmount(uuid, amount, time);
    }


    /**
     * 添加水表
     * @param smartWatermeter
     */
    @Override
    public void createSmartWatermeter(SmartWatermeter smartWatermeter) {
        Log.info("添加水表,水表信息smartWatermeter：{}",smartWatermeter.toString());
        watermeterDao.addSmartWatermeter(smartWatermeter);
    }

    /**
     * 添加网关绑定
     * @param smartGatewayBind
     */
    @Override
    public void addSmartGatewayBind(SmartGatewayBind smartGatewayBind) {
        Log.info("添加网关绑定,网关信息:{}",smartGatewayBind.toString());
        watermeterDao.addSmartGatewayBind(smartGatewayBind);
    }

    /**
     * 查询HouseCreatedByByHouseId
     * @param houseId
     * @return
     */
    @Override
    public int findHouseCreatedByByHouseId(Long houseId) {
        Log.info("查询房源用户id，房源houseId：{}",houseId);
        return watermeterDao.selectHouseCreatedByByHouseId(houseId);
    }

    /**
     * 查询HouseCreatedByByapartmentId
     * @param apartmentId
     * @return
     */
    @Override
    public Integer findApartmentCreatedByByApartmentId(Long apartmentId) {
        Log.info("查询房源用户id，公寓apartmentId：{}",apartmentId);
        return watermeterDao.selectApartmentCreatedByByApartmentId(apartmentId);
    }

    /**
     * 查询水表idByuuid
     * @param uuid
     * @return
     */
    @Override
    public Integer findWatermeterIdByUuid(String uuid) {
        Log.info("查询水表id,水表uuid：{}"+uuid);
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
        Log.info("查询水表读数,水表watermeterId：{}",watermeterId);
        return watermeterDao.findWatermeterAmountByWatermeterId(watermeterId);
    }

    /**
     * 更新水表在线离线状态
     * @param uuid
     * @param code
     */
    @Override
    public void updataWatermerterOnoffStatus(String uuid, Integer code) {
        Log.info("更新水表在线离线状态，水表uuid：{},状态code：{}",uuid,code);
        watermeterDao.updataWatermerterOnoffStatusByUuid(uuid,code);
    }

    /**
     * 查询水表所有的uuidandManufactory
     * @return
     */
    @Override
    public List<UuidAndManufactoryVO> findWatermeterUuidAndManufactory() {
        Log.info("查询水表所有的uuid和Manufactory");
        return watermeterDao.selectWatermeterUuidAndManufactory();
    }

    /**
     * 最近一次抄表时间
     * @param uuid
     * @return
     */
    @Override
    public Timestamp findWatermeterMeterUpdatedAt(String uuid) {
        Log.info("最近一次抄表时间,水表uuid:{}",uuid);
        return watermeterDao.selectWatermeterMeterUpdatedAt(uuid);
    }

    /**
     * 集中式水表listby网关id
     * @param smartGatewayId
     * @return
     */
    @Override
    public List<JZWaterMeterVO> findJzWatermetersByGatewayId(int smartGatewayId) {
        Log.info("集中式水表列表,网关smartGatewayId:{}",smartGatewayId);
        return watermeterDao.selectJzWatermetersByGatewayId(smartGatewayId);
    }

    /**
     * 分散式水表网关详情
     * @param userId
     * @return
     */
    @Override
    public List<JZWatermeterGatewayVO> findGatewaysByUserId(int userId) {
        Log.info("分散式网关详情,userId：{}",userId);
        return watermeterDao.selectGatewaysByUserId(userId);
    }

    /**
     * 查询水表在线状态byuuid
     * @param uuid
     * @return
     */
    @Override
    public Integer findWatermeterOnOffStatusByUuid(String uuid) {
        Log.info("查询水表在线状态,水表uuid：{}",uuid);
        return watermeterDao.selectWatermeterOnOffStatusByUuid(uuid);
    }

    /**
     * 查询所有水表id
     * @return
     */
    @Override
    public List<Integer> findAllWatermeterIds() {
        Log.info("查询所有水表id");
        return watermeterDao.selectAllWatermeterIds();
    }

    /**
     * 查询水表月初抄表读数
     * @param watermeterId
     * @return
     */
    @Override
    public Integer findMeterAmountByWatermeterId(Integer watermeterId) {
        Log.info("查询水表月初抄表读数，水表watermeterId：{}",watermeterId);
        return watermeterDao.selectMeterAmountByWatermeterId(watermeterId);
    }

    /**
     * 更新水表月初读数
     * @param watermeterId
     * @param meterAmount
     */
    @Override
    public void updataWatermeterMeterAmount(Integer watermeterId, Integer meterAmount) {
        Log.info("更新水表月初读数，水表watermeterId：{},水表读数meterAmount：{}",watermeterId,meterAmount);
        watermeterDao.updataWatermeterMeterAmount(watermeterId,meterAmount);
    }


}
