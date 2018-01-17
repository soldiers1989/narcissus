package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import com.ih2ome.hardware_service.service.vo.DeviceIdAndNameVo;
import com.ih2ome.peony.ammeterInterface.vo.AmmeterInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 *TODO:name
 * @author Lucius
 * create by 2017/11/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Repository
public interface AmmeterMannagerDao{

    List<AmmeterMannagerVo>findConcentratAmmeter(AmmeterMannagerVo ammeterMannagerVo);

    List <AmmeterMannagerVo> findDispersedAmmeter(AmmeterMannagerVo ammeterMannagerVo);

    DeviceIdAndNameVo getDeviceByIdWithDispersed(String serialId);

    List <DeviceIdAndNameVo> getDeviceBySerialIdWithDispersed(String id);

    DeviceIdAndNameVo getDeviceByIdWithConcentrated(String serialId);

    List <DeviceIdAndNameVo> getDeviceBySerialIdWithConcentrated(String id);

    void updateWiringWithDispersed(@Param("id")String id,@Param("wiring")String wiring);

    void updateWiringWithConcentrated(@Param("id") String id, @Param("wiring")String wiring);

    String getDeviceIdByIdWithDispersed(@Param("id")String id);

    String getDeviceIdByIdWithConcentrated(@Param("id")String id);

    void updateDevicePriceWithDispersed(@Param("id")String id,@Param("price")String price);

    void updateDevicePriceWithConcentrated(@Param("id")String id,@Param("price")String price);

    void updateDeviceSwitchWithDispersed(@Param("id")String id,@Param("status")String status);

    void updateDeviceSwitchWithConcentrated(@Param("id")String id,@Param("status")String status);

    void updateDevicePayModWithDispersed(@Param("id")String id,@Param("payMod")String payMod);

    void updateDevicePayModWithConcentrated(@Param("id")String id,@Param("payMod")String payMod);

    AmmeterInfoVo getDeviceInfoWithDispersed(@Param("id")String id);

    AmmeterInfoVo getDeviceInfoWithConcentrated(@Param("id")String id);

    com.ih2ome.sunflower.entity.caspain.SmartDevice getMasterAmmeter(@Param("id")String id);

    List<String>getAmmeterByMaster(@Param("id")String id);

    void updateAmmeterWithDispersed(AmmeterInfoVo ammeterInfoVo);

    void updateAmmeterWithConcentrated(AmmeterInfoVo ammeterInfoVo);

    void addDeviceRecordWithDispersed(AmmeterInfoVo ammeterInfoVo);

    void addDeviceRecordWithConcentrated(AmmeterInfoVo ammeterInfoVo);




}
