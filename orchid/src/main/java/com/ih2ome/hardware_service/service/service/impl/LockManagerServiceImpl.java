package com.ih2ome.hardware_service.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.LockManagerDao;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseStyleEnum;
import com.ih2ome.sunflower.vo.pageVo.enums.LockDigitPwdTypeEnum;
import com.ih2ome.sunflower.vo.pageVo.enums.LockStatusEnum;
import com.ih2ome.hardware_service.service.service.LockManagerService;
import com.ih2ome.peony.SMSInterface.SMSUtil;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.SmartLockFirmEnum;
import com.ih2ome.sunflower.vo.thirdVo.sms.enums.SMSCodeEnum;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.GuoJiaLockStatusEnum;
import com.ih2ome.sunflower.vo.pageVo.smartLock.*;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;
import com.ih2ome.peony.smartlockInterface.ISmartLock;

import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockVO;
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
    @Override
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
    public LockInfoVo getLockInfoVo(String id, String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException {
        LockInfoVo lockInfoVo = null;
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            lockInfoVo = lockManagerDao.findDispersedLockByLockNo(id);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            lockInfoVo = lockManagerDao.findConcentrateLockByLockNo(id);
        }
        ISmartLock iSmartLock = (ISmartLock) Class.forName(SmartLockFirmEnum.GUO_JIA.getClazz()).newInstance();
        LockVO lockVO = iSmartLock.getLockInfo(lockInfoVo.getSerialNum());
        Long guaranteeTimeStart = lockVO.getGuaranteeTimeStart();
        Long guaranteeTimeEnd = lockVO.getGuaranteeTimeEnd();
        Long comuStatusUpdateTime = lockVO.getComuStatusUpdateTime();
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
        String id = lockRequestVo.getId();
        String type = lockRequestVo.getType();
        List<LockPasswordVo> pwdList = null;
        if (lockRequestVo.isInitPageRows()) {
            PageHelper.startPage(lockRequestVo.getPage(), lockRequestVo.getRows());
        }
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            pwdList = lockManagerDao.findDispersedPwdList(id);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            pwdList = lockManagerDao.findConcentratePwdList(id);
        }
        for (LockPasswordVo lockPasswordVo : pwdList) {
            lockPasswordVo.setStatus(LockStatusEnum.getByCode(lockPasswordVo.getStatus()));
            lockPasswordVo.setDigitPwdType(LockDigitPwdTypeEnum.getByCode(lockPasswordVo.getDigitPwdType()));
        }
        return pwdList;
    }

    //新增门锁密码
    @Override
    public void addPassword(LockPasswordVo lockPasswordVo, String baseUrl) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException {
        //0代表集中式，1代表分散式
        String type = lockPasswordVo.getType();
        String serialNum = null;
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            serialNum = lockManagerDao.findDisSerialNumById(lockPasswordVo.getId());
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            serialNum = lockManagerDao.findConSerialNumById(lockPasswordVo.getId());
        }
        lockPasswordVo.setSerialNum(serialNum);
        ISmartLock iSmartLock = (ISmartLock) Class.forName(SmartLockFirmEnum.GUO_JIA.getClazz()).newInstance();
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
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            lockManagerDao.addDispersedPwd(lockPasswordVo);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            lockManagerDao.addConcentratePwd(lockPasswordVo);
        }
//        JSONObject message = new JSONObject();
//        message.put("password", lockPasswordVo.getPassword());
//        message.put("startTime", lockPasswordVo.getEnableTime());
//        message.put("endTime", lockPasswordVo.getDisableTime());
//        boolean bool = SMSUtil.sendTemplateText(baseUrl, SMSCodeEnum.ADD_OR_UPDATE_DOOR_LOCK_PASSWORD.getName(), lockPasswordVo.getMobile(), message, 0);
//        if (!bool) {
//            throw new SmartLockException("短信发送失败");
//        }

    }

    //修改门锁密码
    @Override
    public void updatePassword(LockPasswordVo lockPasswordVo, String baseUrl) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException {
        //0代表集中式，1代表分散式
        String type = lockPasswordVo.getType();
        //判断是分散式
        LockPasswordVo model=null;
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            model=lockManagerDao.findDispersedLockIdAndPwdNo(lockPasswordVo.getId());
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            model=lockManagerDao.findConcentrateLockIdAndPwdNo(lockPasswordVo.getId());
        }
        lockPasswordVo.setSerialNum(model.getSerialNum());
        lockPasswordVo.setPwdNo(model.getPwdNo());
        ISmartLock iSmartLock = (ISmartLock) Class.forName(SmartLockFirmEnum.GUO_JIA.getClazz()).newInstance();
        //请求果家第三方的修改接口
        String result = iSmartLock.updateLockPassword(lockPasswordVo);
        JSONObject resJson = JSONObject.parseObject(result);
        String rlt_code = resJson.getString("rlt_code");
        if (!rlt_code.equals("HH0000")) {
            throw new SmartLockException("第三方修改密码失败");
        }
        JSONObject jsonData = JSONObject.parseObject(resJson.get("data").toString());
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            lockManagerDao.updateDispersedPwd(lockPasswordVo);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            lockManagerDao.updateConcentratePwd(lockPasswordVo);
        }
//        JSONObject message = new JSONObject();
//        message.put("password", lockPasswordVo.getPassword());
//        message.put("startTime", lockPasswordVo.getEnableTime());
//        message.put("endTime", lockPasswordVo.getDisableTime());
//        boolean bool = SMSUtil.sendTemplateText(baseUrl, SMSCodeEnum.ADD_OR_UPDATE_DOOR_LOCK_PASSWORD.getName(), lockPasswordVo.getMobile(), message, 0);
//        if (!bool) {
//            throw new SmartLockException("短信发送失败");
//        }


    }

    //删除门锁密码
    @Transactional
    @Override
    public void deletePassword(LockPasswordVo lockPasswordVo) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException {
        ISmartLock iSmartLock = (ISmartLock) Class.forName(SmartLockFirmEnum.GUO_JIA.getClazz()).newInstance();
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
            if (status.equals(GuoJiaLockStatusEnum.PUSH_LOCK_POWRE_LOW.getStatus())) {
                JSONObject jsonData = JSONObject.parseObject(lockHistoryStatus.getRdata());
                String power = jsonData.getString("power");
                lockHistoryStatus.setStatus(GuoJiaLockStatusEnum.PUSH_LOCK_POWRE_LOW.getStatusName() + "(" + power + "%)");
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

    //发送短信
    @Override
    public Boolean sendMessage(LockRequestVo params, String baseUrl) {
        String type = params.getType();
        LockPasswordVo lockPasswordVo = null;
        //获取密码Id
        String id = params.getId();
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            lockPasswordVo = lockManagerDao.findDispersedLockPassword(id);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            lockPasswordVo = lockManagerDao.findConcentrateLockPassword(id);
        }
        JSONObject message = new JSONObject();
        message.put("password", lockPasswordVo.getPassword());
        message.put("startTime", lockPasswordVo.getEnableTime());
        message.put("endTime", lockPasswordVo.getDisableTime());
        boolean bool = SMSUtil.sendTemplateText(baseUrl, SMSCodeEnum.ADD_OR_UPDATE_DOOR_LOCK_PASSWORD.getName(), lockPasswordVo.getMobile(), message, 0);
        return bool;

    }

    //获得短信内容
    @Override
    public String getMessage(LockRequestVo params) {
        LockPasswordVo lockPasswordVo = null;
        String type = params.getType();
        //获取密码Id
        String id = params.getId();
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            lockPasswordVo = lockManagerDao.findDispersedLockPassword(id);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            lockPasswordVo = lockManagerDao.findConcentrateLockPassword(id);
        }
        String messageContent = "［水滴管家]欢迎入住,您的开门密码是" + lockPasswordVo.getPassword() +
                "，有效期自" + lockPasswordVo.getEnableTime() +
                "至" + lockPasswordVo.getDisableTime() + "止，在门锁按键输入“密码+#”后，便可开门。";
        return messageContent;
    }

    //获取密码详情
    @Override
    public LockPasswordVo getPasswordInfo(String id, String type) {
        LockPasswordVo lockPasswordVo = null;
        //判断是分散式
        if (type.equals(HouseStyleEnum.DISPERSED.getCode())) {
            lockPasswordVo = lockManagerDao.findDispersedLockPassword(id);
            //判断是集中式
        } else if (type.equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            lockPasswordVo = lockManagerDao.findConcentrateLockPassword(id);
        }
        return lockPasswordVo;
    }

}
