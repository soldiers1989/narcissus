package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.vo.ApartmentVO;
import com.ih2ome.hardware_service.service.vo.JZWatermeterDetailVO;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.peony.watermeterInterface.vo.YunDingResponseVo;

import java.util.List;

public interface SynchronousHomeService {
    /**
     * 集中式同步房源by楼层
     * @param apartmentId
     * @param floorId
     * @return
     */
    String synchronousHousingByFloorId(int apartmentId, int floorId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;

    /**
     * 集中式同步房源
     * @param apartmentId
     * @return
     */
    String synchronousHousingByApartmenId(int apartmentId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;

    /**
     * 查询floorIdByroomId
     * @param room_id
     * @return
     */
    Long findFloorIdByRoomId(Long room_id);

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
     * 查询集中式房源是否已同步by房源ids
     * @param homeIds
     * @return
     */
    List<YunDingResponseVo> findHomeIsSynchronousedByHomeIds(String[] homeIds) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException ;





}
