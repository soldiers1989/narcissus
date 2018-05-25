package com.ih2ome.hardware_service.service.service;

import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockGatewayAndHouseInfoVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.ApartmentVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.HomeSyncVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.HouseVO;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.YunDingResponseVo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
    Integer findFloorIdByRoomId(Long room_id);

    /**
     * 通过用户id查询用户所有公寓信息
     * @param id
     * @return
     */
    List<ApartmentVO> findApartmentIdByUserId(int id);

    /**
     * 查询集中式房源是否已同步by房源ids
     * @param userId
     * @return
     */
    List<HomeSyncVO> findHomeIsSynchronousedByUserId(int userId) ;

    /**
     * 查询房源是否已同步by房源id
     * @param homeId
     * @param type
     * @return
     */
    YunDingResponseVo findHomeIsSynchronousedByHomeId(int homeId, int type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException, WatermeterException;

    /**
     * 查询分散式房源是否已同步by房源ids
     * @param userId
     * @return
     */
    List<HomeSyncVO> findHmHomeIsSynchronousedByUserId(int userId);

    /**
     * 分散式用户房源
     * @param id
     * @return
     */
    List<HouseVO> findHouseByUserId(int id);

    /**
     * 分散式同步房源
     * @param houseId
     * @return
     */
    String synchronousHousingByHouseId(int houseId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;


    /**
     * 集中式公寓查询by公寓id
     * @param apartmentId
     * @return
     */
    ApartmentVO findApartmentIdByApartmentId(int apartmentId);

    /**
     * 查询floor同步状态
     * @param apartmentId
     * @return
     */
    List<HomeSyncVO> findFloorsIsSynchronousedByApartmentId(int apartmentId);

    /**
     * 查询room同步状态
     * @param apartmentId
     * @return
     */
    List<HomeSyncVO> findRoomsIsSynchronousedByApartmentId(int apartmentId);

    /**
     * 同步房源byRooms
     * @param homeId
     * @param roomId
     * @return
     */
    String synchronousHousingByRooms(int homeId, List<Integer> roomId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;

    /**
     * 查询分散式为同步房间
     * @param userId
     * @return
     */
    List<HomeSyncVO> findHmRoomsIsSynchronousedByUserId(int userId);

    /**
     * 同步分散式roomByroomIds
     * @param roomIds
     * @return
     */
    String synchronousHousingByHmRooms(int roomIds) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;

    /**
     * 同步分散房源byhouseIdandroomids
     * @param homeId
     * @param roomIds
     */
    String synchronousHousingByHmHomeIdRoomIds(int homeId, List<Integer> roomIds) throws ClassNotFoundException, IllegalAccessException, InstantiationException, WatermeterException;

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
    void confirmAssociation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException, ClassNotFoundException, IllegalAccessException, InstantiationException, ParseException;

    /**
     * 取消关联
     * @param smartHouseMappingVO
     * @throws SmartLockException
     */
    void cancelRelation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException;

}
