package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.hardware_service.service.vo.LockHistoryStatusVO;
import com.ih2ome.hardware_service.service.vo.LockInfoVo;
import com.ih2ome.hardware_service.service.vo.LockListVo;
import com.ih2ome.peony.smartlockInterface.vo.LockPasswordVo;
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

    //门锁基本信息(分散式)
    LockInfoVo findDispersedLockByLockNo(String lockNo);

    //门锁基本信息(集中式)
    LockInfoVo findConcentrateLockByLockNo(String lockNo);

    //密码列表(分散式)
    List<LockPasswordVo> findDispersedPwdList(String lockNo);

    //密码列表（集中式）
    List<LockPasswordVo> findConcentratePwdList(String lockNo);

    //新增密码(分散式)
    void addDispersedPwd(LockPasswordVo lockPasswordVo);

    //新增密码（集中式）
    void addConcentratePwd(LockPasswordVo lockPasswordVo);

    //修改密码(分散式)
    void updateDispersedPwd(LockPasswordVo lockPasswordVo);

    //修改密码(集中式)
    void updateConcentratePwd(LockPasswordVo lockPasswordVo);

    //删除密码(分散式)
    void deleteDispersedPwd(LockPasswordVo lockPasswordVo);

    //删除密码(集中式)
    void deleteConcentratePwd(LockPasswordVo lockPasswordVo);

    //查询门锁的历史状态(分散式)
    List<LockHistoryStatusVO> findDispersedLockHistoryStatus(LockHistoryStatusVO lockHistoryStatusVO);

    //查询门锁的历史状态(集中式)
    List<LockHistoryStatusVO> findConcentrateLockHistoryStatus(LockHistoryStatusVO lockHistoryStatusVO);
}
