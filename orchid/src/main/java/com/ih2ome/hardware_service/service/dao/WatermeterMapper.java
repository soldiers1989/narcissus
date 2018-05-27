package com.ih2ome.hardware_service.service.dao;


import com.ih2ome.common.base.MyMapper;
import com.ih2ome.sunflower.entity.narcissus.SmartGatewayBind;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeter;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeterRecord;
import com.ih2ome.sunflower.vo.pageVo.watermeter.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WatermeterMapper extends MyMapper<SmartWatermeter> {


    List<Integer> findRoomIdByCreatebyid(String id);


    /**
     * 分散式水表列表查询
     *
     * @param ids
     * @return
     */
    List<HMWatermeterListVO> finWatermeterByRoomIds(List<Integer> ids);

    /**
     * 根据网关id查询网关详情
     *
     * @param id
     * @return
     */
    WatermeterGatewayDetailVO findGatewaybySmartGatewayId(int id);

    /**
     * 通过网关id查询绑定的水表
     *
     * @param smartGatewayId
     * @return
     */
    List<HMWatermeterListVO> findWatermeterByGatewayId(int smartGatewayId);


    /**
     * 改水价
     *
     * @param price
     * @return
     */
    int updataWaterPrice(@Param("price") int price, @Param("watermeterId") int watermeterId);

    /**
     * 通过公寓id查询网关
     *
     * @param apartmentId
     * @return
     */
    List<JZWatermeterGatewayVO> findGatewayByApartmentId(int apartmentId);

    /**
     * 通过用户id查询分散式水表列表
     *
     * @param id
     * @return
     */
    List<HMWatermeterListVO> findWatermetersByUserId(int id);

    /**
     * 查询水表抄表记录by水表id
     *
     * @param smartWatermeterId
     * @return
     */
    List<SmartWatermeterRecord> findWatermeterRecordByWatermeterId(int smartWatermeterId);

    /**
     * 筛选水表抄表记录
     *
     * @param watermeterId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SmartWatermeterRecord> findWatermeterRecordByWatermeterIdAndTime(@Param("id") int watermeterId, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 水表异常记录
     *
     * @param watermeterId
     * @return
     */
    List<ExceptionVO> findWatermeterExceptionByWaterId(int watermeterId);

    /**
     * 网关异常记录
     *
     * @param gatewayId
     * @return
     */
    List<ExceptionVO> findWatermeterGatewayExceptionByGatewayId(int gatewayId);

    /**
     * 查询水表抄表参数by水表id
     *
     * @param watermeterId
     * @return
     */
    WatermeterRecordParamsVo findWatermeterRecordParamsByWatermeterId(int watermeterId);


    /**
     * 更新水表抄表读数
     *
     * @param uuid
     * @param amount
     * @param time
     */
    void updataWaterLastAmount(@Param("uuid") String uuid, @Param("amount") int amount, @Param("time") Long time);

    /**
     * 通过楼层id查询水表
     *
     * @param floorId
     * @return
     */
    List<JZWatermeterDetailVO> findWatermetersByFloorId(int floorId);

    /**
     * 根据第三方id查询网关id
     * @param homeid
     * @return
     */
    String findGateWay(String homeid);

    /**
     * 添加水表
     *
     * @param smartWatermeter
     */
    void addSmartWatermeter(SmartWatermeter smartWatermeter);

    /**
     * 添加网关绑定
     *
     * @param smartGatewayBind
     */
    void addSmartGatewayBind(SmartGatewayBind smartGatewayBind);

    /**
     * 查询CreatedByByHouseId
     *
     * @param houseId
     * @return
     */
    int selectHouseCreatedByByHouseId(Long houseId);

    /**
     * 查询CreatedByByapartmentId
     *
     * @param apartmentId
     * @return
     */
    Integer selectApartmentCreatedByByApartmentId(Long apartmentId);

    /**
     * 查询水表byuuid
     *
     * @param uuid
     * @return
     */
    Integer findWatermetersByUuId(String uuid);

    /**
     * 查询水表读数by水表id
     *
     * @param watermeterId
     * @return
     */
    Integer findWatermeterAmountByWatermeterId(int watermeterId);

    /**
     * 更新网关在线离线状态
     *
     * @param uuid
     * @param code
     */
    void updataWatermerterOnoffStatusByUuid(@Param("uuid") String uuid, @Param("code") Integer code);

    /**
     * 查询所有水表的UuidAndManufactory
     *
     * @return
     */
    List<UuidAndManufactoryVO> selectWatermeterUuidAndManufactory();

    /**
     * 查询最近一次抄表时间
     *
     * @param uuid
     * @return
     */
    Timestamp selectWatermeterMeterUpdatedAt(String uuid);


    /**
     * 集中式水表listby网关id
     *
     * @param smartGatewayId
     * @return
     */
    List<JZWaterMeterVO> selectJzWatermetersByGatewayId(int smartGatewayId);

    /**
     * 分散式网关list
     *
     * @param userId
     * @return
     */
    List<JZWatermeterGatewayVO> selectGatewaysByUserId(int userId);

    Integer selectWatermeterOnOffStatusByUuid(String uuid);

    /**
     * 查询所有水表id
     *
     * @return
     */
    List<Integer> selectAllWatermeterIds();

    /**
     * 查询月初水表抄表读数
     *
     * @param watermeterId
     * @return
     */
    Integer selectMeterAmountByWatermeterId(Integer watermeterId);

    /**
     * 更新水表月初读数
     *
     * @param watermeterId
     * @param meterAmount
     */
    void updataWatermeterMeterAmount(@Param("watermeterId") Integer watermeterId, @Param("meterAmount") Integer meterAmount);

    /**
     * 查询分散式网关详情
     *
     * @param smartGatewayId
     * @return
     */
    WatermeterGatewayDetailVO findhmGatewaybySmartGatewayId(int smartGatewayId);

    /**
     * 查询水表idBygateway
     *
     * @param uuid
     * @return
     */
    List<String> selectWatermeterIdByGatewayUuid(String uuid);


    void saveWaterDevice(SmartWatermeter smartWatermeter);

    /**
     * 根据userId和第三方厂商
     * 查询已绑定设备（包括网关）的房源
     *
     * @param userId 用户Id
     * @param brand  第三方标识符
     * @return 房源列表
     */
    List<HomeVO> getApartmentListByUserId(@Param("userId") int userId, @Param("brand") String brand);

    /**
     * 集中式：根据公寓Id查询公寓内楼层水表数
     *
     * @param apartmentId 公寓Id
     * @return 公寓水表数 + 各楼层水表数
     */
    List<FloorVO> getFloorWithWater(int apartmentId);

    /**
     * 集中式：根据楼层Id查询楼层下房间水表列表
     *
     * @param floorId 楼层Id
     * @return 房间列表内嵌水表列表
     */
    List<RoomSimpleVO> getRoomWithWater(int floorId);

    /**
     * 集中式：根据房间Id查询水表详情
     * @param roomId 房间Id
     * @return 水表详情列表
     */
    List<WaterDetailVO> getWaterInRoom(int roomId);

    /**
     * 查询房间详情
     * @param roomId 房间Id
     * @return 房间信息
     */
    RoomDetailVO getRoomDetail(int roomId);

    /**
     * 根据水表Id查水表
     * @param waterId 水表Id
     * @return 水表
     */
    WatermeterVO getWatermeterById(int waterId);

    /**
     * 更新房间内冷热水单价
     * @param price     用水单价（分/吨）
     * @param roomId    房间Id
     * @param meterType 水表类型 1-冷 2-热
     * @return 结果
     */
    int updateRoomPrice(@Param("price") int price, @Param("roomId") int roomId, @Param("meterType") int meterType);
}
