package com.ih2ome.hardware_service.service.peony.ammeterInterface;

import com.ih2ome.hardware_service.service.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.sunflower.vo.thirdVo.ammeter.enums.PayMod;
import com.ih2ome.sunflower.vo.thirdVo.ammeter.AmmeterInfoVo;

import java.util.List;
import java.util.Map;

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

     * @param devId   * 设置电表先后付费
     * @param payMod
     */
    void updatePayMod(String devId, PayMod payMod) throws AmmeterException;

    /**
     * 设置电表单价
     * @param devId
     * @param value
     */
    void setElectricityPrice(String devId,Double value) throws AmmeterException;

    /**
     * 获取单个电表数据
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
     * @return
     * @throws AmmeterException
     */
    List <String> getVacantPowerOn()throws AmmeterException;

    /**
     * 获取负数电量设备与负数未断电设备
     * @return
     * @throws AmmeterException
     */
    Map<String,List<String>> getNegativeDeviceAndNegativePowerOnDevice() throws AmmeterException;


    /**
     * 抄表接口
     * @param devId
     * @returnvoid
     */
     void getAmmeterFlushInfo(String devId) throws AmmeterException;


}
