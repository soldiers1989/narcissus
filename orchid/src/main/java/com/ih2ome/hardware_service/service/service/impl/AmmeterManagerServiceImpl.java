package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.hardware_service.service.dao.AmmeterMannagerVoDao;
import com.ih2ome.hardware_service.service.service.AmmeterManagerService;
import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import com.ih2ome.hardware_service.service.vo.DeviceIdAndName;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        return ammeterMannagerVoDao.findConcentratAmmeter(ammeterMannagerVo);
    }

    @Override
    public List<AmmeterMannagerVo> findDispersedAmmeter(AmmeterMannagerVo ammeterMannagerVo) {
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
}
