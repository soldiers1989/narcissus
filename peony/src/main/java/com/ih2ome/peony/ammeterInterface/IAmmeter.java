package com.ih2ome.peony.ammeterInterface;

import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.ammeterInterface.vo.AmmeterInfoVo;
import com.ih2ome.peony.ammeterInterface.enums.PAY_MOD;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public interface IAmmeter {
    /**
     * 电表通断电
     * @param devId
     * @param onOrOff
     */
    void switchAmmeter(String devId,String onOrOff) throws AmmeterException;

    /**
     * 设置电表先后付费
     * @param devId
     * @param payMod
     */
    void updatePayMod(String devId, PAY_MOD payMod) throws AmmeterException;

    /**
     * 设置电表单价
     * @param devId
     * @param value
     */
    void setElectricityPrice(String devId,Double value) throws AmmeterException;

    /**
     * 获取单个电表最新数据
     * @param devId
     * @return
     */
    AmmeterInfoVo getAmmeterInfo(String devId) throws AmmeterException;

    /**
     * 获取离线电表
     * @param hour
     * @return
     * @throws AmmeterException
     */
    List <String> getMissDevice(Integer hour) throws AmmeterException;

    /**
     * 获取长时间无数据上报设备
     * @param hour
     * @return
     * @throws AmmeterException
     */
    List <String> getOnlineNoDataDevice(Integer hour)throws AmmeterException;

    /**
     * 获取空置未断电设备数量
     * @param hour
     * @return
     * @throws AmmeterException
     */
    List <String> getVacantPowerOn(Integer hour)throws AmmeterException;


}
