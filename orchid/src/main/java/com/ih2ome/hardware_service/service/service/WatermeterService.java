package com.ih2ome.hardware_service.service.service;

import com.ih2ome.sunflower.entity.narcissus.SmartDeviceV2;
import com.ih2ome.sunflower.entity.narcissus.SmartGatewayBind;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeter;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeterRecord;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.vo.pageVo.watermeter.*;
import org.apache.ibatis.annotations.Param;

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
     * 通过水表Id查询水表详情
     * @param waterId 水表Id
     * @return 水表详情
     */
    WatermeterVO getWatermeterById(int waterId);

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
    String readWatermeterLastAmountByWatermeterId(int watermeterId, String userId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;


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
     * 根据第三方homeid查询网关id
     * @return
     */
    String findGateWay(String homeid);
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
    SmartWatermeter getWatermeterByUuId(String uuid);
    SmartWatermeter getWatermeterByDeviceId(int deviceId);

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
    List<JZWaterMeterVO> findJzWatermetersByGatewayId(int smartGatewayId);

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

    /**
     * 查询分散式网关详情
     * @param smartGatewayId
     * @return
     */
    WatermeterGatewayDetailVO findhmGatewaybyId(int smartGatewayId);

    /**
     * 查询水表idBygatewayId
     * @param uuid
     * @return
     */
    List<String> findWatermeterIdByGatewayUuid(String uuid);

    /**
     * 根据userId和第三方厂商
     * 查询已绑定设备（包括网关）的集中式房源
     * @param userId 用户Id
     * @param brand 第三方标识符
     * @return 房源列表
     */
    List<HomeVO> getApartmentListByUserId(int userId, String brand);

    /**
     * 集中式：根据公寓Id查询公寓内楼层水表数
     * @param apartmentId 公寓Id
     * @return 公寓水表数 + 各楼层水表数
     */
    List<FloorVO> getFloorWithWater(int apartmentId);

    /**
     * 集中式：根据楼层Id查询楼层下房间水表列表
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
     * 更新房间内冷热水单价
     * @param price 用水单价（分/吨）
     * @param roomId 房间Id
     * @param meterType 水表类型 1-冷 2-热
     * @return 结果
     */
    Boolean updateRoomPrice(int price, int roomId, int meterType);

    RoomAccountVO getRoomAmount(int roomId, int meterType);

    int changeBalance(int roomId,int houseCatalog,int amount,String payChannel,String action, String actionId);

    int makeWaterZero(int roomId, int houseCatalog);

    SmartDeviceV2 getSmartDeviceV2(long deviceId);

    List<SmartDeviceV2> getSmartDeviceV2List(int userId, String brand);
    List<SmartDeviceV2> getAllSmartDeviceV2List();
}
