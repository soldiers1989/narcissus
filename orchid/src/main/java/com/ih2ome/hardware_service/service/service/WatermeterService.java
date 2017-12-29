package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.model.narcissus.SmartGatewayBind;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeterRecord;
import com.ih2ome.hardware_service.service.vo.*;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;

import java.sql.Timestamp;
import java.util.List;

public interface WatermeterService {
    /**
     * 查询分散式水表列表
     * @param id
     * @return
     */
    List<Integer> findRoomIdByUserId(String id);


    List<HMWatermeterListVO> findWatermetersByids(List<Integer> ids);

    /**
     * 通过水表id查询水表详情
     * @param id
     * @return
     */
    WatermeterVO findWatermeterByid(String id);

    /**
     * 通过网关id查询网关详情
     * @param smartGatewayId
     * @return
     */
    WatermeterGatewayDetailVO findGatewaybyId(int smartGatewayId);

    /**
     * 通过网关id查询绑定的水表列表
     * @param smartGatewayId
     * @return
     */
    List<HMWatermeterListVO> findWatermetersByGatewayId(int smartGatewayId);

    /**
     * 查询水表抄表列表
     *
     * @param smartWatermeterId
     * @param page
     *@param count
     * @return
     */
    PageResult<SmartWatermeterRecord> findWatermeterRecordByWatermeterId(int smartWatermeterId, int page, int count);



    /**
     * 更改水价
     * @param price
     * @return
     */
    Boolean updataWaterPrice(int price, int watermeterId) throws AmmeterException, ClassNotFoundException, IllegalAccessException, InstantiationException;

    /**
     * 通过公寓id查询水表网关列表
     * @param apartmentId
     * @return
     */
    List<JZWatermeterGatewayVO> findGatewaysByApartmentId(int apartmentId);

    /**
     * 通过Create_by_id查询分散式水表列表
     * @param id
     * @return
     */
    List<HMWatermeterListVO> findWatermetersByid(int id);

    /**
     * 筛选水表抄表记录
     * @param watermeterId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SmartWatermeterRecord> findWatermeterRecordByWatermeterIdAndTime(int watermeterId, String startTime, String endTime);



    /**
     * 查找水表异常记录
     * @param watermeterId
     * @return
     */
    List<ExceptionVO> findWatermeterException(int watermeterId);

    /**
     * 查找水表网关异常记录
     * @param gatewayId
     * @return
     */
    List<ExceptionVO> findWatermeterGatewayException(int gatewayId);

    /**
     * 查询实时抄表记录
     * @param watermeterId
     * @return
     */
    String readWatermeterLastAmountByWatermeterId(int watermeterId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;


    /**
     * 更新水表抄表读数
     * @param uuid
     * @param amount
     * @param time
     */
    void updataWaterLastAmount(String uuid, int amount, Long time);

    /**
     * 集中式通过楼层Id查询水表详情
     * @param floorId
     * @return
     */
    List<JZWatermeterDetailVO> findWatermetersByFloorId(int floorId);

    /**
     * 添加水表smartWatermeter
     * @param smartWatermeter
     */
    void createSmartWatermeter(SmartWatermeter smartWatermeter);

    /**
     * 添加网关绑定
     * @param smartGatewayBind
     */
    void addSmartGatewayBind(SmartGatewayBind smartGatewayBind);

    /**
     * 查询HouseCreatedByByHouseId
     * @param houseId
     * @return
     */
    int findHouseCreatedByByHouseId(Long houseId);

    /**
     * 查询HouseCreatedByByapartmentId
     * @param apartmentId
     * @return
     */
    Integer findApartmentCreatedByByApartmentId(Long apartmentId);

    /**
     * 查询水表idByuuid
     * @param uuid
     * @return
     */
    Integer findWatermeterIdByUuid(String uuid);

    /**
     * 查询水表读数by水表id
     * @param watermeterId
     * @return
     */
    Integer findWatermeterLastAmountByWatermeterId(int watermeterId);

    /**
     * 更新水表在线离线状态
     * @param uuid
     * @param code
     */
    void updataWatermerterOnoffStatus(String uuid, Integer code);

    /**
     * 查询所有UuidAndManufactory
     * @return
     */
    List<UuidAndManufactoryVO> findWatermeterUuidAndManufactory();

    /**
     * 最近一次抄表时间
     * @param uuid
     * @return
     */
    Timestamp findWatermeterMeterUpdatedAt(String uuid);

    /**
     * 集中式水表listby网关id
     * @param smartGatewayId
     * @return
     */
    List<JZWatermeterDetailVO> findJzWatermetersByGatewayId(int smartGatewayId);

    /**
     * 分散式网关listbyUserId
     * @param userId
     * @return
     */
    List<JZWatermeterGatewayVO> findGatewaysByUserId(int userId);

    /**
     * 查询水表在线状态byuuid
     * @param uuid
     * @return
     */
    Integer findWatermeterOnOffStatusByUuid(String uuid);

    /**
     * 查询所有水表id
     * @return
     */
    List<Integer> findAllWatermeterIds();

    /**
     * 查询水表月初抄表读数
     * @param watermeterId
     * @return
     */
    Integer findMeterAmountByWatermeterId(Integer watermeterId);

    /**
     * 更新水表月初读数
     * @param watermeterId
     * @param meterAmount
     */
    void updataWatermeterMeterAmount(Integer watermeterId, Integer meterAmount);
}
