package com.ih2ome.hardware_service.service.dao;


import com.ih2ome.hardware_service.service.entity.narcissus.SmartAlarmRule;
import com.ih2ome.hardware_service.service.entity.narcissus.SmartMistakeInfo;
import com.ih2ome.hardware_service.service.vo.AmmeterAlarmVo;
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

    List<AmmeterAlarmVo>findDispersedAmmeterAlarm(AmmeterAlarmVo ammeterAlarmVo);

    List<AmmeterAlarmVo>findConcentratAmmeter(AmmeterAlarmVo ammeterAlarmVo);

    void clearAmmeterAlarmRules();



}
