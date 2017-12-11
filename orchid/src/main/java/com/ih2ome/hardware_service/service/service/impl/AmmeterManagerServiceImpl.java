package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.common.utils.MyConstUtils;
import com.ih2ome.hardware_service.service.dao.AmmeterMannagerVoDao;
import com.ih2ome.hardware_service.service.service.AmmeterManagerService;
import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import com.ih2ome.hardware_service.service.vo.DeviceIdAndName;
import com.ih2ome.peony.ammeterInterface.IAmmeter;
import com.ih2ome.peony.ammeterInterface.enums.AMMETER_FIRM;
import com.ih2ome.peony.ammeterInterface.enums.PAY_MOD;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.ammeterInterface.vo.AmmeterInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        }else if(type.equals("1")){
            deviceIdAndName = ammeterMannagerVoDao.getDeviceByIdWithConcentrated(id);
            if(deviceIdAndName!=null){
                deviceIdAndNameList = ammeterMannagerVoDao.getDeviceBySerialIdWithConcentrated(deviceIdAndName.getSerialId());
                deviceIdAndName.setDeviceIdAndNames(deviceIdAndNameList);
            }
        }
        return deviceIdAndName;
    }

    /**
     * 获取电表详情接口
     * @param id
     * @param type
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws AmmeterException
     */
    @Transactional
    @Override
    public AmmeterInfoVo getAmmeterInfoVo(String id, String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException {
        AmmeterInfoVo ammeterInfoVo = null;
        AmmeterInfoVo model = null;
        IAmmeter iAmmeter = (IAmmeter) Class.forName(AMMETER_FIRM.POWER_BEE.getClazz()).newInstance();
        String devId = null;
        if (type.equals("0")){
            model = ammeterMannagerVoDao.getDeviceInfoWithDispersed(id);
            devId =ammeterMannagerVoDao.getDeviceIdByIdWithDispersed(id);
            if(model.getIsHub().equals("0")){
                model = initFenTan(model);
            }
        }else if(type.equals("1")){
            model = ammeterMannagerVoDao.getDeviceInfoWithConcentrated(id);
            devId =ammeterMannagerVoDao.getDeviceIdByIdWithConcentrated(id);
        }
        AmmeterInfoVo modelFromInterFace  =iAmmeter.getAmmeterInfo(devId);
        AmmeterInfoVo data = (AmmeterInfoVo)MyConstUtils.mergeObject(modelFromInterFace,model);
        if(type.equals("0")){
            ammeterMannagerVoDao.updateAmmeterWithDispersed(data);
            //ammeterMannagerVoDao.addDeviceRecordWithDispersed(data);
        }else if(type.equals("1")){
            ammeterMannagerVoDao.updateAmmeterWithConcentrated(data);
            //ammeterMannagerVoDao.addDeviceRecordWithConcentrated(data);
        }
        return data;
    }

    /**
     * 计算分摊
     * @param ammeterInfoVo
     * @return
     */
    private AmmeterInfoVo initFenTan(AmmeterInfoVo ammeterInfoVo) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException {
        IAmmeter iAmmeter = (IAmmeter) Class.forName(AMMETER_FIRM.POWER_BEE.getClazz()).newInstance();
        com.ih2ome.hardware_service.service.model.caspain.SmartDevice master = ammeterMannagerVoDao.getMasterAmmeter(ammeterInfoVo.getId());
        AmmeterInfoVo model = iAmmeter.getAmmeterInfo(master.getSerialId());
        Double powerDay = model.getPowerDay();
        Double powerMonth = model.getPowerDay();
        //0=公共区域 1=独立公共区域
        if(ammeterInfoVo.getUseCase().equals("0")){
            List <String> ammeterSerialId = ammeterMannagerVoDao.getAmmeterByMaster(String.valueOf(master.getId()));
            Double allPowerDay = new Double(0.0);
            Double allPowerMonth = new Double(0.0);
            for (String id:ammeterSerialId){
                AmmeterInfoVo ammeter = iAmmeter.getAmmeterInfo(id);
                allPowerDay+=ammeter.getPowerDay();
                allPowerMonth+=ammeter.getPowerMonth();
            }
            Double share = Double.valueOf(ammeterInfoVo.getShare());
            Double shareDay = (powerDay-allPowerDay)*share/100;
            Double shareMonth = (powerMonth-allPowerMonth)*share/100;
            ammeterInfoVo.setShareDay(shareDay);
            ammeterInfoVo.setShareMonth(shareMonth);
        }else if(ammeterInfoVo.getUseCase().equals("1")){
            Double share = Double.valueOf(ammeterInfoVo.getShare());
            Double shareDay = powerDay*share/100;
            Double shareMonth = powerMonth*share/100;
            ammeterInfoVo.setShareDay(shareDay);
            ammeterInfoVo.setShareMonth(shareMonth);
        }
        return ammeterInfoVo;
    }

    @Override
    public void updateWiring(String id, String type, String wiring) {

        if(type.equals("0")){
            ammeterMannagerVoDao.updateWiringWithDispersed(id,wiring);
        }else if(type.equals("1")){
            ammeterMannagerVoDao.updateWiringWithConcentrated(id,wiring);
        }
    }

    @Transactional
    @Override
    public void updatePrice(String id, String type, String price) throws AmmeterException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        IAmmeter iAmmeter = (IAmmeter) Class.forName(AMMETER_FIRM.POWER_BEE.getClazz()).newInstance();
        String devId = null;
        if (type.equals("0")){
           devId =ammeterMannagerVoDao.getDeviceIdByIdWithDispersed(id);
           ammeterMannagerVoDao.updateDevicePriceWithDispersed(id,price);
        }else if(type.equals("1")){
            devId =ammeterMannagerVoDao.getDeviceIdByIdWithConcentrated(id);
            ammeterMannagerVoDao.updateDevicePriceWithConcentrated(id,price);
        }
        iAmmeter.setElectricityPrice(devId,Double.valueOf(price));


    }

    @Override
    public void switchDevice(String id, String operate,String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException {
        IAmmeter iAmmeter = (IAmmeter) Class.forName(AMMETER_FIRM.POWER_BEE.getClazz()).newInstance();
        String devId = null;
        if (type.equals("0")){
            devId =ammeterMannagerVoDao.getDeviceIdByIdWithDispersed(id);
        }else{
            devId =ammeterMannagerVoDao.getDeviceIdByIdWithConcentrated(id);
        }
        iAmmeter.switchAmmeter(devId,operate);
    }

    @Transactional
    @Override
    public void updatePayMod(String id, String type, PAY_MOD pay_mod) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException {
        IAmmeter iAmmeter = (IAmmeter) Class.forName(AMMETER_FIRM.POWER_BEE.getClazz()).newInstance();
        String devId = null;
        if (type.equals("0")){
            devId =ammeterMannagerVoDao.getDeviceIdByIdWithDispersed(id);
            ammeterMannagerVoDao.updateDevicePayModWithDispersed(id, String.valueOf(pay_mod.getCode()));
        }else if(type.equals("1")){
            devId =ammeterMannagerVoDao.getDeviceIdByIdWithConcentrated(id);
            ammeterMannagerVoDao.updateDevicePayModWithConcentrated(id, String.valueOf(pay_mod.getCode()));
        }
        iAmmeter.updatePayMod(devId,pay_mod);

    }


}
