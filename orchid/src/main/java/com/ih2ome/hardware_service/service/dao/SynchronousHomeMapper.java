package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.common.base.MyMapper;
import com.ih2ome.hardware_service.service.model.volga.Apartment;
import com.ih2ome.hardware_service.service.vo.AddRoomVO;
import com.ih2ome.hardware_service.service.vo.ApartmentVO;
import com.ih2ome.hardware_service.service.vo.JZWatermeterDetailVO;
import com.ih2ome.peony.watermeterInterface.vo.AddHomeVo;
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
    Long findFloorIdByRoomId(Long room_id);

    /**
     * 通过楼层id查询水表
     * @return
     * @param floorId
     */
    List<JZWatermeterDetailVO> findWatermetersByFloorId(int floorId);


    /**
     * 更新房源未已同步至云丁
     * @param apartmentId
     */
    void updateHomeSyncByApartmentId(@Param("homeSyn") int homeSyn,@Param("apartmentId") int apartmentId);
}
