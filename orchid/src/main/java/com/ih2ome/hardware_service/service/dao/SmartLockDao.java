package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sky
 * @create 2018/01/22
 * @email sky.li@ixiaoshuidi.com
 **/
@Repository
public interface SmartLockDao {
    /**
     * 根据用户id查询该用户下的分散式房源信息
     *
     * @param userId
     * @return
     */
    List<HomeVO> findDispersedHomes(String userId);

    /**
     * 根据用户id查询该用户下的集中式房源信息
     *
     * @param userId
     * @return
     */
    List<HomeVO> findConcentrateHomes(String userId);

    /**
     * 取消关联房源(分散式，集中式)
     *
     * @param smartHouseMappingVO
     */
    void cancelAssociation(SmartHouseMappingVO smartHouseMappingVO);

    /**
     * 查询房源映射表中的记录
     *
     * @param houseMapping
     * @return
     */
    SmartHouseMappingVO findHouseMappingRecord(SmartHouseMappingVO houseMapping);

    /**
     * 新增房间关联记录
     *
     * @param houseMapping
     */
    void addAssociation(SmartHouseMappingVO houseMapping);

    /**
     * 修改房间关联记录
     *
     * @param houseMapping
     */
    void updateAssociation(SmartHouseMappingVO houseMapping);

    /**
     * 根据公共区域，查询该房源下的所有关联记录(分散式)
     *
     * @param houseMapping
     * @return
     */
    List<SmartHouseMappingVO> findDispersedRoomMappingByPublicZone(SmartHouseMappingVO houseMapping);

    /**
     * 根据公共区域，查询该房源下的所有关联记录(集中式)
     *
     * @param houseMapping
     * @return
     */
    List<SmartHouseMappingVO> findConcentrateRoomMappingByPublicZone(SmartHouseMappingVO houseMapping);

    /**
     * 根据房间ID查询该房间所属房源的公共区域Id(分散式)
     *
     * @param roomId
     * @return
     */
    String findDispersedPublicZoneByRoomId(String roomId);

    /**
     * 根据房间ID查询该房间所属房源的公共区域Id(集中式)
     *
     * @param roomId
     * @return
     */
    String findConcentratePublicZoneByRoomId(String roomId);

    /**
     * 通过roomId清除该房间下的设备
     *
     * @param type
     * @param roomId
     */
    void clearDevicesByRoomId(@Param("type") String type, @Param("roomId") String roomId, @Param("providerCode") String providerCode);

    /**
     * 通过publicZoneId清除该公共区域下的设备
     *
     * @param type
     * @param publicZoneId
     */
    void clearDevicesByPublicZoneId(@Param("type") String type, @Param("publicZoneId") String publicZoneId, @Param("providerCode") String providerCode);
}
