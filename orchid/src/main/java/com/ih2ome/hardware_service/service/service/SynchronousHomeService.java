package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.ApartmentVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.HouseVO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface SynchronousHomeService {

    /**
     * 查询floorIdByroomId
     * @param room_id
     * @return
     */
    Integer findFloorIdByRoomId(Long room_id);

    /**
     * 通过用户id查询用户所有公寓信息
     * @param id
     * @return
     */
    List<ApartmentVO> findApartmentIdByUserId(int id);

    /**
     * 分散式用户房源
     * @param id
     * @return
     */
    List<HouseVO> findHouseByUserId(int id);

    /**
     * 集中式公寓查询by公寓id
     * @param apartmentId
     * @return
     */
    ApartmentVO findApartmentIdByApartmentId(int apartmentId);

    /**
     * 查询roomIdsByfloorIds
     * @param floorList
     * @return
     */
    List<Integer> findRoomIdsByfloorIds(List<Integer> floorList);

    /**
     * 查询公寓id和nameby用户id
     * @param id
     * @return
     *//*
    List<ApartmentVO> findApartmentByUserId(int id);*/

    /**
     * 获取第三方及本地房源信息
     * @param userId
     * @param type
     * @param factoryType
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SmartLockException
     */

    Map<String, List<HomeVO>> serchWater(String userId, String type, String factoryType)throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException;

    /**
     * 房间关联
     * @param smartHouseMappingVO
     * @throws SmartLockException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ParseException
     */
    String confirmAssociation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException, ClassNotFoundException, IllegalAccessException, InstantiationException, ParseException;

    /**
     * 取消关联
     * @param smartHouseMappingVO
     * @throws SmartLockException
     */
    void cancelRelation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException;

}
