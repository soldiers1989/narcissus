package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.SmartLockGatewayDao;
import com.ih2ome.hardware_service.service.enums.HouseStyleEnum;
import com.ih2ome.hardware_service.service.service.SmartLockGatewayService;
import com.ih2ome.hardware_service.service.vo.LockListVo;
import com.ih2ome.hardware_service.service.vo.SmartDoorLockGatewayVO;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.enums.SmartLockFirm;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.vo.guojia.GuoJiaGateWayVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Service
public class SmartLockGatewayServiceImpl implements SmartLockGatewayService {

    @Resource
    SmartLockGatewayDao smartLockGatewayDao;

    @Override
    public List<SmartDoorLockGatewayVO> gatewayList(SmartDoorLockGatewayVO smartDoorLockGatewayVO) {
        if(smartDoorLockGatewayVO.getPage()!= null && smartDoorLockGatewayVO.getRows() != null){
            PageHelper.startPage(smartDoorLockGatewayVO.getPage(),smartDoorLockGatewayVO.getRows());
        }
        if(smartDoorLockGatewayVO.getType().equals(HouseStyleEnum.DISPERSED.getCode())){
            return smartLockGatewayDao.findDispersedGateway(smartDoorLockGatewayVO);
        }else if(smartDoorLockGatewayVO.getType().equals(HouseStyleEnum.CONCENTRAT.getCode())){
            return smartLockGatewayDao.findConcentratGateway(smartDoorLockGatewayVO);
        }else{
            return null;
        }
    }

    @Override
    public SmartDoorLockGatewayVO getSmartDoorLockGatewayVOById(String type, String id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException {
        ISmartLock iSmartLock = (ISmartLock) Class.forName(SmartLockFirm.GUO_JIA.getClazz()).newInstance();
        if(type.equals(HouseStyleEnum.DISPERSED.getCode())){
            SmartDoorLockGatewayVO smartDoorLockGatewayVO = smartLockGatewayDao.getSmartDispersedDoorLockGatewayVOById(id);
            GuoJiaGateWayVo guoJiaGateWayVo = iSmartLock.getGuoJiaGateWayInfo(smartDoorLockGatewayVO.getGatewayCode());
            smartDoorLockGatewayVO.setGuaranteeTimeStart(guoJiaGateWayVo.getGuaranteeTimeStart());
            smartDoorLockGatewayVO.setGuaranteeTimeEnd(guoJiaGateWayVo.getGuaranteeTimeEnd());
            return smartDoorLockGatewayVO;
        }else if(type.equals(HouseStyleEnum.CONCENTRAT.getCode())){
            SmartDoorLockGatewayVO smartDoorLockGatewayVO = smartLockGatewayDao.getConcentratSmartDoorLockGatewayVOById(id);
            GuoJiaGateWayVo guoJiaGateWayVo = iSmartLock.getGuoJiaGateWayInfo(smartDoorLockGatewayVO.getGatewayCode());
            smartDoorLockGatewayVO.setGuaranteeTimeStart(guoJiaGateWayVo.getGuaranteeTimeStart());
            smartDoorLockGatewayVO.setGuaranteeTimeEnd(guoJiaGateWayVo.getGuaranteeTimeEnd());
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
            return smartLockGatewayDao.getDispersedSmartDoorLockByGatewayId(id);
        }else if(type.equals(HouseStyleEnum.CONCENTRAT.getCode())){
            return smartLockGatewayDao.getConcentratSmartDoorLockByGatewayId(id);
        }else{
            return null;
        }
    }
}
