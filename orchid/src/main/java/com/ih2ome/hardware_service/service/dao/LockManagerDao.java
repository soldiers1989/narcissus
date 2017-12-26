package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.hardware_service.service.vo.LockListVo;
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
    List<LockListVo> findConcentrateLock(LockListVo lockListVo);
    //门锁列表（分散式）
    List<LockListVo> findDispersedLock(LockListVo lockListVo);

}
