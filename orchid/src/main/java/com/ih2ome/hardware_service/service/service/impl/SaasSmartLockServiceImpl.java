package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.hardware_service.service.dao.SmartLockDao;
import com.ih2ome.hardware_service.service.service.SaasSmartLockService;
import com.ih2ome.sunflower.model.backup.SaasSmartLock;
import com.ih2ome.sunflower.vo.pageVo.Ammeter;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseStyleEnum;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockDetailVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.WatermeterDetailVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaasSmartLockServiceImpl implements SaasSmartLockService{

    @Resource
    private SmartLockDao smartLockDao;

    @Override
    public SaasSmartLock getSmartLock(String userId, String type, String roomId) {
        SaasSmartLock smartLock=new SaasSmartLock();
        //判断是集中式
        if(HouseStyleEnum.CONCENTRAT.getCode().equals(type)){
            smartLock=smartLockDao.findSmartLock(roomId);
        }else if(HouseStyleEnum.DISPERSED.getCode().equals(type)){
            smartLock=smartLockDao.findSmartLockCode(roomId);
        }
        return smartLock;
    }

    @Override
    public SaasSmartLock getSmartLockCount(String userId, String type) {
        int count=0;
        int gatWay=0;
        SaasSmartLock saasSmartLock=new SaasSmartLock();
        if(HouseStyleEnum.CONCENTRAT.getCode().equals(type)){
            String employerapatmentsid=smartLockDao.findEmployer(userId);
            //判断子账号权限
            if(employerapatmentsid==null) {
                List<String> list=smartLockDao.findUserId(userId);
                list.add(userId);
                for(String id:list){
                    count+=Integer.parseInt(smartLockDao.findSmartLockCount(id));
                    gatWay+=Integer.parseInt(smartLockDao.findSmartGateWayCount(id));
                }
                saasSmartLock.setCount(count+"");
                saasSmartLock.setGatWay(gatWay+"");
            }else{
                saasSmartLock.setCount(smartLockDao.findSmartLockCount(userId));
                saasSmartLock.setGatWay(smartLockDao.findSmartGateWayCount(userId));
            }
        }else if(HouseStyleEnum.DISPERSED.getCode().equals(type)){
            String employerId=smartLockDao.queryEmployer(userId);
            if(employerId==null) {
                List<String> list=smartLockDao.findUserId(userId);
                list.add(userId);
                for(String id:list){
                    count+=Integer.parseInt(smartLockDao.QuerySmartLockCount(id));
                    gatWay+=Integer.parseInt(smartLockDao.querySmartGatWayCount(id));
                }
                saasSmartLock.setCount(count+"");
                saasSmartLock.setGatWay(gatWay+"");
            }else{
                saasSmartLock.setCount(smartLockDao.QuerySmartLockCount(userId));
                saasSmartLock.setGatWay(smartLockDao.querySmartGatWayCount(userId));
            }
        }
        return saasSmartLock;
    }

    @Override
    public SmartLockDetailVO getInformation(String type, String roomId) {
        SmartLockDetailVO smartLockDetailVOList=smartLockDao.findSmartLockInformation(roomId,type);
        return smartLockDetailVOList;
    }

    @Override
    public List<WatermeterDetailVO> getWatermeter(String type, String roomId) {
        List<WatermeterDetailVO> watermeterDetailVO=smartLockDao.findWatermeter(roomId,type);
        return watermeterDetailVO;
    }

    @Override
    public Ammeter getAmmeter(String type, String roomId) {
        Ammeter Ammeter=new Ammeter();
        if(HouseStyleEnum.CONCENTRAT.getCode().equals(type)){
            Ammeter=smartLockDao.findAmmeter(roomId);
        }else if(HouseStyleEnum.DISPERSED.getCode().equals(type)){
            Ammeter=smartLockDao.findDispersedAmmeters(roomId);
        }
        return Ammeter;
    }
}
