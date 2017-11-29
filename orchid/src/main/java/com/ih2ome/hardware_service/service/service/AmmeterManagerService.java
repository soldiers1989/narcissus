package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;

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
}
