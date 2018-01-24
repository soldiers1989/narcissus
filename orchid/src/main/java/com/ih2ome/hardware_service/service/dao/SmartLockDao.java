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
     * 分散式取消关联房间
     */
    void dispersedCancelAssociation(SmartHouseMappingVO smartHouseMappingVO);

    /**
     * 集中式取消关联房间
     */
    void concentrateCancelAssociation(SmartHouseMappingVO smartHouseMappingVO);
}
