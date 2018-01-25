package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
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
}
