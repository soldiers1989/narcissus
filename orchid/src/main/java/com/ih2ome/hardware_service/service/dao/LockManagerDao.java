package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import com.ih2ome.hardware_service.service.vo.LockManagerVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sky
 * @create 2017/12/21
 * @email sky.li@ixiaoshuidi.com
 **/
@Repository
public interface LockManagerDao {
    //门锁列表（集中式）
    List<LockManagerVo> findConcentrateLock(LockManagerVo lockManagerVo);
    //门锁列表（分散式）
    List <LockManagerVo> findDispersedLock(LockManagerVo lockManagerVo);

}
