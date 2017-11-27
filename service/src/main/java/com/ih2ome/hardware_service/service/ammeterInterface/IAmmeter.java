package com.ih2ome.hardware_service.service.ammeterInterface;

import com.ih2ome.hardware_service.service.ammeterInterface.exception.AmmeterException;
import com.ih2ome.hardware_service.service.ammeterInterface.vo.AmmeterInfoVo;
import com.ih2ome.hardware_service.service.enums.PAY_MOD;

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
    void updatePayMod(String devId, PAY_MOD payMod);

    /**
     * 设置电表单价
     * @param devId
     * @param value
     */
    void setElectricityPrice(String devId,Double value);

    /**
     * 获取单个电表最新数据
     * @param devId
     * @return
     */
    AmmeterInfoVo getAmmeterInfo(String devId);


}
