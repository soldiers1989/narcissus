package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.common.base.MyMapper;
import com.ih2ome.hardware_service.service.model.volga.Apartment;
import com.ih2ome.hardware_service.service.vo.*;
import com.ih2ome.peony.watermeterInterface.vo.AddHomeVo;
import com.ih2ome.peony.watermeterInterface.vo.AddRoomVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SynchronousHomeMapper extends MyMapper<Apartment>{

    /**
     * 查询房源信息byApartmentId
     * @param apartmentId
     * @return
     */
    AddHomeVo findHouseByApartmentId(int apartmentId);

    /**
     * 查询room信息byFloorId
     * @param floorId
     * @return
     */
    List<AddRoomVO> findRoomByFloorId(int floorId);

    /**
     * 查询room信息byApartmentId
     * @param apartmentId
     * @return
     */
    List<AddRoomVO> findRoomByApartmentId(int apartmentId);

    /**
     * 查询公寓列表
     * @param id
     * @return
     */
    List<ApartmentVO> findApartmentByUserId(int id);

    /**
     * 查询floorIdbyRoomId
     * @param room_id
     * @return
     */
    Integer findFloorIdByRoomId(Long room_id);

    /**
     * 更新房源为已同步至云丁
     * @param apartmentId
     */
    void updateHomeSyncByApartmentId(@Param("homeSync") int homeSync, @Param("apartmentId") int apartmentId);

    /**
     * 更新room为已同步至云丁
     * @param roomSync
     * @param list
     */
    void updataRoomSyncByRoomId(@Param("roomSync") Integer roomSync, @Param("list") List<Integer> list);

    /**
     * 查询集中式房源同步信息
     * @param userid
     * @return
     */
    List<HomeSyncVO> findApartmentIsSynchronousedByUserId(int userid);

    /**
     * 查询分散式房源同步信息
     * @param userid
     * @return
     */
    List<HomeSyncVO> findHouseIsSynchronousedByUserId(int userid);

    /**
     * 更新floor为已同步至云丁
     * @param homeSync
     * @param floorId
     */
    void updataFloorSyncByFloorId(@Param("homeSync") int homeSync, @Param("floorId") int floorId);

    /**
     * 查询楼层idsByapartmentId
     * @param apartmentId
     * @return
     */
    List<Integer> findFloorIdsByApartmentId(int apartmentId);

    /**
     * 更新批量floor为已同步至云丁
     * @param floorSync
     * @param floorIds
     */
    void updataFloorSyncByFloorIds(@Param("floorSync") Integer floorSync, @Param("list") List<Integer> floorIds);

    /**
     * 分散式用户房源
     * @param id
     * @return
     */
    List<HouseVO> findHouseByUserId(int id);

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
     * 更新分散式房源为已同步至云丁
     * @param houseId
     */
    void updateHouseSyncByHouseId(@Param("homeSync") Integer homeSync, @Param("houseId") int houseId);

    /**
     * 更新分散式room为已同步至云丁
     * @param roomSync
     * @param list
     */
    void updataHmRoomSyncByRoomId(@Param("roomSync") Integer roomSync, @Param("list") List<Integer> list);

    /**
     * 查询集中式公寓by公寓id
     * @param apartmentId
     * @return
     */
    ApartmentVO selectApartmentIdByApartmentId(int apartmentId);

    /**
     * 查询集中式floor同步状态
     * @param apartmentId
     * @return
     */
    List<HomeSyncVO> selectFloorsIsSynchronousedByApartmentId(int apartmentId);

    /**
     * 查询集中式未同步rooms
     * @param apartmentId
     * @return
     */
    List<HomeSyncVO> selectRoomsIsSynchronousedByApartmentId(int apartmentId);

    /**
     * 查询房间byrooms
     * @param roomIds
     * @return
     */
    List<AddRoomVO> findRoomByRoomIds(int[] roomIds);

    /**
     * 查询分散式未同步room
     * @param userId
     * @return
     */
    List<HomeSyncVO> selectHmRoomsIsSynchronousedByUserId(int userId);

    /**
     * 查询houseIdByroomId
     * @param roomId
     */
    Integer selectHouseIdByRoomId(int roomId);

    /**
     * 查询分散式roombyroomId
     * @param roomId
     * @return
     */
    AddRoomVO findhmRoomByRoomId(int roomId);

    /**
     * 查询分散式roombyroomids
     * @param roomIds
     * @return
     */
    List<AddRoomVO> findHmRoomByRoomIds(List<Integer> roomIds);
}
