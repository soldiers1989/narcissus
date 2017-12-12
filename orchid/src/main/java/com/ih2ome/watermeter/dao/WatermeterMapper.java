package com.ih2ome.watermeter.dao;

import com.ih2ome.common.base.MyMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartGatewayBind;
import com.ih2ome.hardware_service.service.model.narcissus.SmartWatermeter;
import com.ih2ome.peony.watermeterInterface.vo.AddHomeVo;
import com.ih2ome.watermeter.model.SmartWatermeterRecord;
import com.ih2ome.watermeter.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatermeterMapper extends MyMapper<SmartWatermeterRecord> {


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
    WatermeterGatewayDetailVO findGatewaybySmartGatewayId(int id);

    /**
     * 通过网关id查询绑定的水表
     * @param smartGatewayId
     * @return
     */
    List<WatermeterDetailVO> findWatermeterByGatewayId(int smartGatewayId);

    /**
     * 查询公寓列表
     * @param id
     * @return
     */
    List<ApartmentVO> findApartmentByUserId(int id);

    /**
     * 通过楼层id查询水表
     * @return
     * @param floorId
     */
    List<JZWatermeterDetailVO> findWatermetersByFloorId(int floorId);

    /**
     * 改水价
     * @param price
     * @return
     */
    int updataWaterPrice(@Param("price") int price, @Param("watermeterId") int watermeterId);

    /**
     * 通过公寓id查询网关
     * @param apartmentId
     * @return
     */
    List<JZWatermeterGatewayVO> findGatewayByApartmentId(int apartmentId);

    /**
     * 通过用户id查询分散式水表列表
     * @param id
     * @return
     */
    List<WatermeterDetailVO> findWatermetersByUserId(int id);

    /**
     * 查询水表抄表记录by水表id
     * @param smartWatermeterId
     * @return
     */
    List<SmartWatermeterRecord> findWatermeterRecordByWatermeterId(int smartWatermeterId);

    /**
     * 筛选水表抄表记录
     * @param watermeterId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SmartWatermeterRecord> findWatermeterRecordByWatermeterIdAndTime(@Param("id") int watermeterId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 分散式用户房源
     * @param id
     * @return
     */
    List<HouseVO> findHouseByUserId(int id);

    /**
     * 水表异常记录
     * @param watermeterId
     * @return
     */
    List<ExceptionVO> findWatermeterExceptionByWaterId(int watermeterId);

    /**
     * 网关异常记录
     * @param gatewayId
     * @return
     */
    List<ExceptionVO> findWatermeterGatewayExceptionByGatewayId(int gatewayId);

    /**
     * 查询水表抄表参数by水表id
     * @param watermeterId
     * @return
     */
    WatermeterRecordParamsVo findWatermeterRecordParamsByWatermeterId(int watermeterId);

    /**
     * 查询房源信息byhouseid
     * @param houseId
     * @return
     */
    AddHomeVo findHouseByHouseId(int houseId);

    /**
     * 查询room信息byHouseId
     * @param houseId
     * @return
     */
    List<AddRoomVO> findRoomByHouseId(int houseId);

    /**
     * 查询房源信息byApartmentId
     * @param apartmentId
     * @return
     */
    AddHomeVo findHouseByApartmentId(int apartmentId);

    /**
     * 查询room信息byApartmentId
     * @param apartmentId
     * @return
     */
    List<AddRoomVO> findRoomByApartmentId(int apartmentId);

    /**
     * 查询room信息byFloorId
     * @param floorId
     * @return
     */
    List<AddRoomVO> findRoomByFloorId(int floorId);

    /**
     * 添加已同步的room到room_device
     * @param roomIds
     */
    void addSynchronousRooms(List roomIds);

    /**
     * 更新水表抄表读数
     * @param uuid
     * @param amount
     * @param time
     */
    void updataWaterLastAmount(@Param("uuid") String uuid,@Param("amount") int amount,@Param("time") int time);

    /**
     * 查询floorIdbyRoomId
     * @param room_id
     * @return
     */
    Long findFloorIdByRoomId(Long room_id);

    /**
     * 添加水表
     * @param smartWatermeter
     */
    void addSmartWatermeter(SmartWatermeter smartWatermeter);

    /**
     * 添加网关绑定
     * @param smartGatewayBind
     */
    void addSmartGatewayBind(SmartGatewayBind smartGatewayBind);
}
