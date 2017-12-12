package com.ih2ome.watermeter.service;

import com.ih2ome.hardware_service.service.model.narcissus.SmartGatewayBind;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeterRecord;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.peony.watermeterInterface.vo.YunDingResponseVo;
import com.ih2ome.watermeter.vo.*;

import java.util.List;

public interface WatermeterService {
    /**
     * 查询分散式水表列表
     * @param id
     * @return
     */
    List<Integer> findRoomIdByUserId(String id);


    List<WatermeterDetailVO> findWatermetersByids(List<Integer> ids);

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
    List<WatermeterDetailVO> findWatermetersByGatewayId(int smartGatewayId);

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
     * 通过用户id查询用户所有公寓信息
     * @param id
     * @return
     */
    List<ApartmentVO> findApartmentIdByUserId(int id);

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
    List<WatermeterDetailVO> findWatermetersByid(int id);

    /**
     * 筛选水表抄表记录
     * @param watermeterId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SmartWatermeterRecord> findWatermeterRecordByWatermeterIdAndTime(int watermeterId, String startTime, String endTime);

    /**
     * 分散式用户房源
     * @param id
     * @return
     */
    List<HouseVO> findHouseByUserId(int id);

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
     * 查询房源是否已同步by房源id
     * @param homeId
     * @return
     */
    YunDingResponseVo findHomeIsSynchronousedByHomeId(int homeId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException, WatermeterException;

    /**
     * 查询实时抄表记录
     * @param watermeterId
     * @return
     */
    int findWatermeterLastAmountByWatermeterId(int watermeterId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;

    /**
     * 分散式同步房源
     * @param houseId
     * @return
     */
    String synchronousHousingByHouseId(int houseId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;

    /**
     * 集中式同步房源
     * @param apartmentId
     * @return
     */
    String synchronousHousingByApartmenId(int apartmentId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;

    /**
     * 集中式同步房源by楼层
     * @param apartmentId
     * @param floorId
     * @return
     */
    String synchronousHousingByFloorId(int apartmentId, int floorId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;

    /**
     * 查询集中式房源是否已同步by房源ids
     * @param homeIds
     * @return
     */
    List<YunDingResponseVo> findHomeIsSynchronousedByHomeIds(String[] homeIds) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException ;


    /**
     * 更新水表抄表读数
     * @param uuid
     * @param amount
     * @param time
     */
    void updataWaterLastAmount(String uuid, int amount, int time);

    /**
     * 查询floorIdByroomId
     * @param room_id
     * @return
     */
    Long findFloorIdByRoomId(Long room_id);

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
    int findApartmentCreatedByByApartmentId(Long apartmentId);

    /**
     * 查询水表idByuuid
     * @param uuid
     * @return
     */
    int findWatermeterIdByUuid(String uuid);
}
