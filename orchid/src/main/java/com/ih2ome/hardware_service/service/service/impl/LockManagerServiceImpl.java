package com.ih2ome.hardware_service.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.LockManagerDao;
import com.ih2ome.hardware_service.service.enums.HouseStyleEnum;
import com.ih2ome.hardware_service.service.enums.LockDigitPwdTypeEnum;
import com.ih2ome.hardware_service.service.enums.LockStatusEnum;
import com.ih2ome.hardware_service.service.service.LockManagerService;
import com.ih2ome.hardware_service.service.vo.*;
import com.ih2ome.peony.SMSInterface.SMSUtil;
import com.ih2ome.peony.SMSInterface.enums.SMSCodeEnum;
import com.ih2ome.peony.smartlockInterface.enums.GuoJiaLockStatusEnum;
import com.ih2ome.peony.smartlockInterface.vo.LockPasswordVo;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.enums.SmartLockFirm;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.vo.GuoJiaLockInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
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
    public List<LockPasswordVo> getPwdList(LockRequestVo lockRequestVo) {
        if (lockRequestVo == null) {
            return null;
        }
        String lockNo = lockRequestVo.getLockNo();
        String type = lockRequestVo.getType();
        List<LockPasswordVo> pwdList = null;
        if (lockRequestVo.isInitPageRows()) {
            PageHelper.startPage(lockRequestVo.getPage(), lockRequestVo.getRows());
        }
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            pwdList = lockManagerDao.findDispersedPwdList(lockNo);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            pwdList = lockManagerDao.findConcentratePwdList(lockNo);
        }
        for (LockPasswordVo lockPasswordVo : pwdList) {
            lockPasswordVo.setStatus(LockStatusEnum.getByCode(lockPasswordVo.getStatus()));
            lockPasswordVo.setDigitPwdType(LockDigitPwdTypeEnum.getByCode(lockPasswordVo.getDigitPwdType()));
        }
        return pwdList;
    }

    //新增门锁密码
    @Override
    public void addPassword(LockPasswordVo lockPasswordVo,String baseUrl) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException {
        ISmartLock iSmartLock = (ISmartLock) Class.forName(SmartLockFirm.GUO_JIA.getClazz()).newInstance();
        //请求果家第三方的新增密码接口
        String result = iSmartLock.addLockPassword(lockPasswordVo);
        JSONObject resJson = JSONObject.parseObject(result);
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            throw new SmartLockException("第三方添加密码失败");
        }
        JSONObject jsonData = JSONObject.parseObject(resJson.get("data").toString());
        //密码编号
        String pwd_no = jsonData.getString("pwd_no");
        lockPasswordVo.setPwdNo(pwd_no);
        //0代表集中式，1代表分散式
        String type = lockPasswordVo.getType();
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            lockManagerDao.addDispersedPwd(lockPasswordVo);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            lockManagerDao.addConcentratePwd(lockPasswordVo);
        }
        JSONObject message = new JSONObject();
        message.put("password",lockPasswordVo.getPassword());
        message.put("startTime",lockPasswordVo.getEnableTime());
        message.put("endTime",lockPasswordVo.getDisableTime());
        boolean bool = SMSUtil.sendTemplateText(baseUrl, SMSCodeEnum.ADD_OR_UPDATE_DOOR_LOCK_PASSWORD.getName(), lockPasswordVo.getMobile(), message, 0);
        if(!bool){
            throw new SmartLockException("短信发送失败");
        }

    }

    //修改门锁密码
    @Override
    public void updatePassword(LockPasswordVo lockPasswordVo,String baseUrl) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException {
        ISmartLock iSmartLock = (ISmartLock) Class.forName(SmartLockFirm.GUO_JIA.getClazz()).newInstance();
        //请求果家第三方的修改接口
        String result = iSmartLock.updateLockPassword(lockPasswordVo);
        JSONObject resJson = JSONObject.parseObject(result);
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            throw new SmartLockException("第三方修改密码失败");
        }
        JSONObject jsonData = JSONObject.parseObject(resJson.get("data").toString());
        //0代表集中式，1代表分散式
        String type = lockPasswordVo.getType();
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            lockManagerDao.updateDispersedPwd(lockPasswordVo);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            lockManagerDao.updateConcentratePwd(lockPasswordVo);
        }
        JSONObject message = new JSONObject();
        message.put("password",lockPasswordVo.getPassword());
        message.put("startTime",lockPasswordVo.getEnableTime());
        message.put("endTime",lockPasswordVo.getDisableTime());
        boolean bool = SMSUtil.sendTemplateText(baseUrl, SMSCodeEnum.ADD_OR_UPDATE_DOOR_LOCK_PASSWORD.getName(), lockPasswordVo.getMobile(), message, 0);
        if(!bool){
            throw new SmartLockException("短信发送失败");
        }


    }

    //删除门锁密码
    @Transactional
    @Override
    public void deletePassword(LockPasswordVo lockPasswordVo) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException {
        ISmartLock iSmartLock = (ISmartLock) Class.forName(SmartLockFirm.GUO_JIA.getClazz()).newInstance();
        //请求果家第三方的删除密码接口
        String result = iSmartLock.deleteLockPassword(lockPasswordVo);
        JSONObject resJson = JSONObject.parseObject(result);
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            throw new SmartLockException("第三方删除密码失败");
        }
        //0代表集中式，1代表分散式
        String type = lockPasswordVo.getType();
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            lockManagerDao.deleteDispersedPwd(lockPasswordVo);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            lockManagerDao.deleteConcentratePwd(lockPasswordVo);
        }
    }

    //查询门锁的历史状态
    @Override
    public List<LockHistoryStatusVO> getLockHistoryList(LockHistoryStatusVO lockHistoryStatusVO) {
        List<LockHistoryStatusVO> historyStatus = null;
        if (lockHistoryStatusVO == null) {
            return null;
        }
        String type = lockHistoryStatusVO.getType();
        if (lockHistoryStatusVO.isInitPageRows()) {
            PageHelper.startPage(lockHistoryStatusVO.getPage(), lockHistoryStatusVO.getRows());
        }
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            historyStatus = lockManagerDao.findDispersedLockHistoryStatus(lockHistoryStatusVO);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            historyStatus = lockManagerDao.findConcentrateLockHistoryStatus(lockHistoryStatusVO);
        }
        for (LockHistoryStatusVO lockHistoryStatus : historyStatus) {
            String status = lockHistoryStatus.getStatus();
            //如果当前状态是'电量低'
            if(status.equals(GuoJiaLockStatusEnum.PUSH_LOCK_POWRE_LOW.getStatus())){
                JSONObject jsonData = JSONObject.parseObject(lockHistoryStatus.getRdata());
                String power = jsonData.getString("power");
                lockHistoryStatus.setStatus(GuoJiaLockStatusEnum.PUSH_LOCK_POWRE_LOW.getStatusName()+"("+power+"%)");
                continue;
            }
            lockHistoryStatus.setStatus(GuoJiaLockStatusEnum.getByStatus(status));
        }
        return historyStatus;
    }

    //查询门锁的操作记录
    @Override
    public List<LockOperateRecordVO> getLockOperateRecords(LockOperateRecordVO lockOperateRecordVO) {
        List<LockOperateRecordVO> operateRecords = null;
        if (lockOperateRecordVO == null) {
            return null;
        }
        String type = lockOperateRecordVO.getType();
        if (lockOperateRecordVO.isInitPageRows()) {
            PageHelper.startPage(lockOperateRecordVO.getPage(), lockOperateRecordVO.getRows());
        }
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            operateRecords = lockManagerDao.findDispersedLockOperateRecord(lockOperateRecordVO);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            operateRecords = lockManagerDao.findConcentrateLockOperateRecord(lockOperateRecordVO);
        }
        return operateRecords;
    }

    //查询门锁的开门记录
    @Override
    public List<LockOpenRecordVO> getLockOpenRecords(LockOpenRecordVO lockOpenRecord) {
        List<LockOpenRecordVO> openRecords = null;
        if (lockOpenRecord == null) {
            return null;
        }
        String type = lockOpenRecord.getType();
        if (lockOpenRecord.isInitPageRows()) {
            PageHelper.startPage(lockOpenRecord.getPage(), lockOpenRecord.getRows());
        }
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            openRecords = lockManagerDao.findDispersedLockOpenRecord(lockOpenRecord);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            openRecords = lockManagerDao.findConcentrateLockOpenRecord(lockOpenRecord);
        }
        return openRecords;

    }

}
