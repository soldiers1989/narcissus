package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
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
public interface AmmeterMannagerVoDao {

    List<AmmeterMannagerVo>findConcentratAmmeter(AmmeterMannagerVo ammeterMannagerVo);

    List <AmmeterMannagerVo> findDispersedAmmeter(AmmeterMannagerVo ammeterMannagerVo);
}
