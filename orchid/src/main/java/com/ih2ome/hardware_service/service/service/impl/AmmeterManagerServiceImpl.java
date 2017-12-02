package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.AmmeterMannagerVoDao;
import com.ih2ome.hardware_service.service.service.AmmeterManagerService;
import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import com.ih2ome.hardware_service.service.vo.DeviceIdAndName;
import com.ih2ome.peony.ammeterInterface.IAmmeter;
import com.ih2ome.peony.ammeterInterface.enums.AMMETER_FIRM;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.ammeterInterface.vo.AmmeterInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Service
public class AmmeterManagerServiceImpl implements AmmeterManagerService{

    @Resource
    AmmeterMannagerVoDao ammeterMannagerVoDao;

    @Override
    public List<AmmeterMannagerVo> findConcentratAmmeter(AmmeterMannagerVo ammeterMannagerVo) {
        if(ammeterMannagerVo.getPage()!= null && ammeterMannagerVo.getRows() != null){
            PageHelper.startPage(ammeterMannagerVo.getPage(),ammeterMannagerVo.getRows());
        }
        return ammeterMannagerVoDao.findConcentratAmmeter(ammeterMannagerVo);
    }

    @Override
    public List<AmmeterMannagerVo> findDispersedAmmeter(AmmeterMannagerVo ammeterMannagerVo) {
        if(ammeterMannagerVo.getPage()!= null && ammeterMannagerVo.getRows() != null){
            PageHelper.startPage(ammeterMannagerVo.getPage(),ammeterMannagerVo.getRows());
        }
        return ammeterMannagerVoDao.findDispersedAmmeter(ammeterMannagerVo);
    }

    @Override
    public DeviceIdAndName getAmmeterRelation(String id,String type) {
        DeviceIdAndName deviceIdAndName = null;
        List<DeviceIdAndName>deviceIdAndNameList = null;
        if(type.equals("0")){
            deviceIdAndName = ammeterMannagerVoDao.getDeviceByIdWithDispersed(id);
            if(deviceIdAndName!=null) {
                deviceIdAndNameList = ammeterMannagerVoDao.getDeviceBySerialIdWithDispersed(deviceIdAndName.getSerialId());
                deviceIdAndName.setDeviceIdAndNames(deviceIdAndNameList);
            }
        }else{
            deviceIdAndName = ammeterMannagerVoDao.getDeviceByIdWithConcentrated(id);
            if(deviceIdAndName!=null){
                deviceIdAndNameList = ammeterMannagerVoDao.getDeviceBySerialIdWithConcentrated(deviceIdAndName.getSerialId());
                deviceIdAndName.setDeviceIdAndNames(deviceIdAndNameList);
            }
        }
        return deviceIdAndName;
    }

    @Override
    public AmmeterInfoVo getAmmeterInfoVo(String id, String type) {
        AmmeterInfoVo ammeterInfoVo = null;

        return null;
    }

    @Override
    public void updateWiring(String id, String type, String wiring) {

        if(type.equals("0")){
            ammeterMannagerVoDao.updateWiringWithDispersed(id,wiring);
        }else{
            ammeterMannagerVoDao.updateWiringWithConcentrated(id,wiring);
        }
    }

    @Transient
    @Override
    public void updatePrice(String id, String type, String price) throws AmmeterException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        IAmmeter iAmmeter = null;
        iAmmeter = (IAmmeter) Class.forName(AMMETER_FIRM.POWER_BEE.getClazz()).newInstance();
        String devId = null;
        if (type.equals("0")){
           devId =ammeterMannagerVoDao.getDeviceNameByIdWithDispersed(id);
           ammeterMannagerVoDao.updateDevicePriceWithDispersed(id,price);
        }else{
            devId =ammeterMannagerVoDao.getDeviceNameByIdWithConcentrated(id);
            ammeterMannagerVoDao.updateWiringWithConcentrated(id,price);
        }
        iAmmeter.setElectricityPrice(devId,Double.valueOf(price));


    }

    @Override
    public void switchDevice(String id, String operate,String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException {
        IAmmeter iAmmeter = null;
        iAmmeter = (IAmmeter) Class.forName(AMMETER_FIRM.POWER_BEE.getClazz()).newInstance();
        String devId = null;
        if (type.equals("0")){
            devId =ammeterMannagerVoDao.getDeviceNameByIdWithDispersed(id);
        }else{
            devId =ammeterMannagerVoDao.getDeviceNameByIdWithConcentrated(id);
        }
        iAmmeter.switchAmmeter(devId,operate);
    }
}
