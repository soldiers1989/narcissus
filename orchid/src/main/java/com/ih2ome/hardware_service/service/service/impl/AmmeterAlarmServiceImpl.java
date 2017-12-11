package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.hardware_service.service.dao.AmmeterAlarmDao;
import com.ih2ome.hardware_service.service.model.narcissus.SmartAlarmRule;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;
import com.ih2ome.hardware_service.service.service.AmmeterAlarmService;
import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/7
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Service
public class AmmeterAlarmServiceImpl implements AmmeterAlarmService {

    @Resource
    AmmeterAlarmDao ammeterAlarmDao;

    @Override
    public void saveAmmeterAlarmRules(SmartAlarmRule smartReport) {
        if(smartReport.getId()!=null){
            ammeterAlarmDao.updateAmmeterAlarmRules(smartReport);
        }else{
            ammeterAlarmDao.addAmmeterAlarmRules(smartReport);
        }
    }

    @Override
    public List<SmartAlarmRule> getAllSmartAlarmRules() {
        return ammeterAlarmDao.getAllSmartAlarmRules();
    }

    @Override
    public SmartAlarmRule getByReportName(String reportName) {
        return ammeterAlarmDao.getByReportName(reportName);
    }

    @Override
    public void saveAlarmList(List<SmartMistakeInfo> smartMistakeInfoList) {
        ammeterAlarmDao.saveAlarmList(smartMistakeInfoList);
    }

    @Override
    public List<AmmeterMannagerVo> findDispersedAmmeterAlarm(AmmeterMannagerVo ammeterMannagerVo) {
        return ammeterAlarmDao.findDispersedAmmeterAlarm(ammeterMannagerVo);
    }

    @Override
    public List<AmmeterMannagerVo> findConcentratAmmeterAlarm(AmmeterMannagerVo ammeterMannagerVo) {
        return ammeterAlarmDao.findConcentratAmmeter(ammeterMannagerVo);
    }
}
