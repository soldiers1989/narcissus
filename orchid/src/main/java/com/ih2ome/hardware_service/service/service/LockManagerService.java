package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.vo.*;
import com.ih2ome.peony.smartlockInterface.vo.guojia.LockPasswordVo;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;

import java.text.ParseException;
import java.util.List;

/**
 * @author Sky
 * @create 2017/12/21
 * @email sky.li@ixiaoshuidi.com
 **/
public interface LockManagerService {

    //门锁列表
    List<LockListVo> lockList(LockListVo lockListVo);

    //根据门锁编码查询门锁基本信息
    LockInfoVo getLockInfoVo(String id, String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException;

    //根据门锁编码查询门锁密码列表
    List<LockPasswordVo> getPwdList(LockRequestVo lockRequestVo);

    //新增门锁密码
    void addPassword(LockPasswordVo lockPasswordVo, String baseUrl) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException;

    //修改门锁密码
    void updatePassword(LockPasswordVo lockPasswordVo, String baseUrl) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException;

    //删除门锁密码
    void deletePassword(LockPasswordVo lockPasswordVo) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException;

    //查询门锁的历史状态
    List<LockHistoryStatusVO> getLockHistoryList(LockHistoryStatusVO lockHistoryStatusVO);

    //查询门锁的操作记录
    List<LockOperateRecordVO> getLockOperateRecords(LockOperateRecordVO lockOperateRecordVO);

    //查询门锁的开门记录
    List<LockOpenRecordVO> getLockOpenRecords(LockOpenRecordVO lockOpenRecord);

    //发送短信
    Boolean sendMessage(LockRequestVo params, String baseUrl);

    //获得短信内容
    String getMessage(LockRequestVo params);

    //获取密码详情
    LockPasswordVo getPasswordInfo(String id, String type);
}
