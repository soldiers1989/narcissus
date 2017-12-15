package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.AmmeterAlarmDao;
import com.ih2ome.hardware_service.service.enums.HouseStyleEnum;
import com.ih2ome.hardware_service.service.model.narcissus.SmartAlarmRule;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;
import com.ih2ome.hardware_service.service.service.AmmeterAlarmService;
import com.ih2ome.hardware_service.service.vo.AmmeterAlarmVo;
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
    public void saveAmmeterAlarmRules(List <SmartAlarmRule> smartAlarmRuleList) {
        for(SmartAlarmRule smartAlarmRule:smartAlarmRuleList) {
            if (smartAlarmRule.getId() != null) {
                ammeterAlarmDao.updateAmmeterAlarmRules(smartAlarmRule);
            } else {
                ammeterAlarmDao.addAmmeterAlarmRules(smartAlarmRule);
            }
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
    public List<AmmeterAlarmVo> findAmmeterAlarmInfoList(AmmeterAlarmVo ammeterAlarmVo) {
        if(ammeterAlarmVo.getPage()!= null && ammeterAlarmVo.getRows() != null){
            PageHelper.startPage(ammeterAlarmVo.getPage(),ammeterAlarmVo.getRows());
        }
        if(ammeterAlarmVo.getType().equals(HouseStyleEnum.DISPERSED.getCode())){
            return ammeterAlarmDao.findDispersedAmmeterAlarm(ammeterAlarmVo);
        }else if(ammeterAlarmVo.getType().equals(HouseStyleEnum.CONCENTRAT.getCode())){
            return ammeterAlarmDao.findConcentratAmmeter(ammeterAlarmVo);
        }
        return null;
    }

}
