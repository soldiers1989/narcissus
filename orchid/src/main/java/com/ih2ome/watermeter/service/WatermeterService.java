package com.ih2ome.watermeter.service;

import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.peony.watermeterInterface.vo.YunDingResponseVo;
import com.ih2ome.watermeter.model.SmartWatermeterRecord;
import com.ih2ome.watermeter.model.Watermeter;
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
    Watermeter findWatermeterByid(String id);

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
     * @param watermeterId
     * @return
     */
    List<ExceptionVO> findWatermeterGatewayException(int gatewayId);

    /**
     * 查询房源是否已同步by房源id
     * @param homeId
     * @return
     */
    YunDingResponseVo findHomeIsSynchronousedByHomeId(int homeId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException, WatermeterException;
}
