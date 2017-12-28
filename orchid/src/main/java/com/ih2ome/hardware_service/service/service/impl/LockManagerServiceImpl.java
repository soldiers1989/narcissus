package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.LockManagerDao;
import com.ih2ome.hardware_service.service.enums.HouseStyleEnum;
import com.ih2ome.hardware_service.service.enums.LockDigitPwdTypeEnum;
import com.ih2ome.hardware_service.service.enums.LockStatusEnum;
import com.ih2ome.hardware_service.service.service.LockManagerService;
import com.ih2ome.hardware_service.service.vo.LockInfoVo;
import com.ih2ome.hardware_service.service.vo.LockListVo;
import com.ih2ome.hardware_service.service.vo.LockPasswordListVo;
import com.ih2ome.hardware_service.service.vo.LockRequestVo;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.enums.SmartLockFirm;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.vo.GuoJiaLockInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Sky
 * @create 2017/12/21
 * @email sky.li@ixiaoshuidi.com
 **/
@Service
public class LockManagerServiceImpl implements LockManagerService {

    @Resource
    private LockManagerDao lockManagerDao;

    //门锁列表
    public List<LockListVo> lockList(LockListVo lockListVo) {
        if (lockListVo == null) {
            return null;
        }
        if (lockListVo.isInitPageRows()) {
            PageHelper.startPage(lockListVo.getPage(), lockListVo.getRows());
        }
        //判断是分散式
        if (lockListVo.getType().equals(HouseStyleEnum.DISPERSED.getCode())) {
            return lockManagerDao.findDispersedLock(lockListVo);
            //判断是集中式
        } else if (lockListVo.getType().equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            return lockManagerDao.findConcentrateLock(lockListVo);
        } else {
            return null;
        }
    }

    //根据门锁编码查询门锁基本信息
    @Override
    public LockInfoVo getLockInfoVo(String lockNo, String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException {
        LockInfoVo lockInfoVo = null;
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            lockInfoVo = lockManagerDao.findDispersedLockByLockNo(lockNo);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            lockInfoVo = lockManagerDao.findConcentrateLockByLockNo(lockNo);
        }
        ISmartLock iSmartLock = (ISmartLock) Class.forName(SmartLockFirm.GUO_JIA.getClazz()).newInstance();
        GuoJiaLockInfoVo guoJiaLockInfo = iSmartLock.getGuoJiaLockInfo(lockNo);
        Long guaranteeTimeStart = guoJiaLockInfo.getGuaranteeTimeStart();
        Long guaranteeTimeEnd = guoJiaLockInfo.getGuaranteeTimeEnd();
        Long comuStatusUpdateTime = guoJiaLockInfo.getComuStatusUpdateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lockInfoVo.setGuaranteeTimeStart(simpleDateFormat.format(new Date(guaranteeTimeStart)));
        lockInfoVo.setGuaranteeTimeEnd(simpleDateFormat.format(new Date(guaranteeTimeEnd)));
        lockInfoVo.setStatusUpdateTime(simpleDateFormat.format(new Date(comuStatusUpdateTime)));
        return lockInfoVo;
    }

    //根据门锁编码查询门锁密码列表
    @Override
    public List<LockPasswordListVo> getPwdList(LockRequestVo lockRequestVo) {
        if(lockRequestVo==null){
            return null;
        }
        String lockNo=lockRequestVo.getLockNo();
        String type=lockRequestVo.getType();
        List<LockPasswordListVo> pwdList=null;
        if(lockRequestVo.isInitPageRows()){
            PageHelper.startPage(lockRequestVo.getPage(),lockRequestVo.getRows());
        }
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
           pwdList= lockManagerDao.findDispersedPwdList(lockNo);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            pwdList=lockManagerDao.findConcentratePwdList(lockNo);
        }
        for (LockPasswordListVo lockPasswordListVo:pwdList){
            lockPasswordListVo.setStatus(LockStatusEnum.getByCode(lockPasswordListVo.getStatus()));
            lockPasswordListVo.setDigitPwdType(LockDigitPwdTypeEnum.getByCode(lockPasswordListVo.getDigitPwdType()));
        }
        return pwdList;
    }

}
