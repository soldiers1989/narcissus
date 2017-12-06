package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import com.ih2ome.hardware_service.service.vo.DeviceIdAndName;
import com.ih2ome.peony.ammeterInterface.vo.AmmeterInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Repository
public interface AmmeterMannagerVoDao{

    List<AmmeterMannagerVo>findConcentratAmmeter(AmmeterMannagerVo ammeterMannagerVo);

    List <AmmeterMannagerVo> findDispersedAmmeter(AmmeterMannagerVo ammeterMannagerVo);

    DeviceIdAndName getDeviceByIdWithDispersed(String serialId);

    List <DeviceIdAndName> getDeviceBySerialIdWithDispersed(String id);

    DeviceIdAndName getDeviceByIdWithConcentrated(String serialId);

    List <DeviceIdAndName> getDeviceBySerialIdWithConcentrated(String id);

    void updateWiringWithDispersed(String id,String wiring);

    void updateWiringWithConcentrated(@Param("id") String id, @Param("wiring")String wiring);

    String getDeviceIdByIdWithDispersed(@Param("id")String id);

    String getDeviceIdByIdWithConcentrated(@Param("id")String id);

    String updateDevicePriceWithDispersed(@Param("id")String id,@Param("price")String price);

    String updateDevicePriceWithConcentrated(@Param("id")String id,@Param("price")String price);

    void updateDevicePayModWithDispersed(@Param("id")String id,@Param("payMod")String payMod);

    void updateDevicePayModWithConcentrated(@Param("id")String id,@Param("payMod")String payMod);

    AmmeterInfoVo getDeviceInfoWithDispersed(@Param("id")String id);

    AmmeterInfoVo getDeviceInfoWithConcentrated(@Param("id")String id);

    com.ih2ome.hardware_service.service.model.caspain.SmartDevice getMasterAmmeter(@Param("id")String id);




}
