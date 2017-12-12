package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.hardware_service.service.model.narcissus.SmartAlarmRule;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;
import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/7
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Repository
public interface AmmeterAlarmDao {

    void addAmmeterAlarmRules(SmartAlarmRule smartReport);

    void updateAmmeterAlarmRules(SmartAlarmRule smartReport);

    List<SmartAlarmRule> getAllSmartAlarmRules();

    SmartAlarmRule getByReportName(@Param("reportName")String reportName);

    void saveAlarmList(List<SmartMistakeInfo> smartMistakeInfoList);

    List<AmmeterMannagerVo>findDispersedAmmeterAlarm(AmmeterMannagerVo ammeterMannagerVo);

    List<AmmeterMannagerVo>findConcentratAmmeter(AmmeterMannagerVo ammeterMannagerVo);



}