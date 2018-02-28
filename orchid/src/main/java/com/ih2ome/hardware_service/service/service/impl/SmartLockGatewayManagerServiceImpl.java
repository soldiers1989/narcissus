package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.SmartLockGatewayManagerDao;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayManagerService;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseStyleEnum;
import com.ih2ome.sunflower.vo.pageVo.smartLock.LockListVo;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartDoorLockGatewayVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.GatewayInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.SmartLockFirmEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Service
public class SmartLockGatewayManagerServiceImpl implements SmartLockGatewayManagerService {

    @Resource
    SmartLockGatewayManagerDao smartLockGatewayManagerDao;

    @Override
    public List<SmartDoorLockGatewayVO> gatewayList(SmartDoorLockGatewayVO smartDoorLockGatewayVO) {
        if(smartDoorLockGatewayVO.getPage()!= null && smartDoorLockGatewayVO.getRows() != null){
            PageHelper.startPage(smartDoorLockGatewayVO.getPage(),smartDoorLockGatewayVO.getRows());
        }
        if(smartDoorLockGatewayVO.getType().equals(HouseStyleEnum.DISPERSED.getCode())){
            return smartLockGatewayManagerDao.findDispersedGateway(smartDoorLockGatewayVO);
        }else if(smartDoorLockGatewayVO.getType().equals(HouseStyleEnum.CONCENTRAT.getCode())){
            return smartLockGatewayManagerDao.findConcentratGateway(smartDoorLockGatewayVO);
        }else{
            return null;
        }
    }

    @Override
    public SmartDoorLockGatewayVO getSmartDoorLockGatewayVOById(String type, String id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException {
        String dateType = "yyyy-mm-dd";
        ISmartLock iSmartLock = (ISmartLock) Class.forName(SmartLockFirmEnum.GUO_JIA.getClazz()).newInstance();
        if(type.equals(HouseStyleEnum.DISPERSED.getCode())){
            SmartDoorLockGatewayVO smartDoorLockGatewayVO = smartLockGatewayManagerDao.getSmartDispersedDoorLockGatewayVOById(id);
            GatewayInfoVO gateWayInfoVo = iSmartLock.getGateWayInfo(smartDoorLockGatewayVO.getGatewayCode());
            smartDoorLockGatewayVO.setGuaranteeTimeStart(gateWayInfoVo.getGuaranteeTimeStart());
            smartDoorLockGatewayVO.setGuaranteeTimeEnd(gateWayInfoVo.getGuaranteeTimeEnd());
            return smartDoorLockGatewayVO;
        }else if(type.equals(HouseStyleEnum.CONCENTRAT.getCode())){
            SmartDoorLockGatewayVO smartDoorLockGatewayVO = smartLockGatewayManagerDao.getConcentratSmartDoorLockGatewayVOById(id);
            GatewayInfoVO gateWayInfoVo = iSmartLock.getGateWayInfo(smartDoorLockGatewayVO.getGatewayCode());
            smartDoorLockGatewayVO.setGuaranteeTimeStart(gateWayInfoVo.getGuaranteeTimeStart());
            smartDoorLockGatewayVO.setGuaranteeTimeEnd(gateWayInfoVo.getGuaranteeTimeEnd());
            return smartDoorLockGatewayVO;
        }else{
            return null;
        }
    }

    @Override
    public List<LockListVo> getSmartDoorLockByGatewayId(String id, String type, Integer page, Integer rows) {
        if(page!= null && rows != null){
            PageHelper.startPage(page,rows);
        }else{
            PageHelper.startPage(1,10);
        }
        if(type.equals(HouseStyleEnum.DISPERSED.getCode())){
            return smartLockGatewayManagerDao.getDispersedSmartDoorLockByGatewayId(id);
        }else if(type.equals(HouseStyleEnum.CONCENTRAT.getCode())){
            return smartLockGatewayManagerDao.getConcentratSmartDoorLockByGatewayId(id);
        }else{
            return null;
        }
    }
}
