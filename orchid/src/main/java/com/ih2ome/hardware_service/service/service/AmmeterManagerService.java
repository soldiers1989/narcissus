package com.ih2ome.hardware_service.service.service;

import com.ih2ome.sunflower.vo.pageVo.ammeter.AmmeterMannagerVo;
import com.ih2ome.sunflower.vo.pageVo.watermeter.DeviceIdAndNameVo;
import com.ih2ome.sunflower.vo.thirdVo.ammeter.enums.PayMod;
import com.ih2ome.hardware_service.service.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.sunflower.vo.thirdVo.ammeter.AmmeterInfoVo;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public interface AmmeterManagerService {

    List <AmmeterMannagerVo> findConcentratAmmeter(AmmeterMannagerVo ammeterMannagerVo);

    List <AmmeterMannagerVo> findDispersedAmmeter(AmmeterMannagerVo ammeterMannagerVo);

    DeviceIdAndNameVo getAmmeterRelation(String id, String type);

    AmmeterInfoVo getAmmeterInfoVo(String id,String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException;

    void updateWiring(String id, String type, String wiring) throws AmmeterException;

    void updatePrice(String id, String type, String price) throws AmmeterException, ClassNotFoundException, IllegalAccessException, InstantiationException;

    void switchDevice(String id,String operate,String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException;

    void updatePayMod(String id, String type, PayMod payMod) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException;

    List<AmmeterMannagerVo> ammeterList(AmmeterMannagerVo ammeterMannagerVo);

    AmmeterInfoVo getAmmeterFlushInfoVo(String id,String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException, InterruptedException;
}
