package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.hardware_service.service.dao.AmmeterAlarmDao;
import com.ih2ome.sunflower.entity.narcissus.SmartAlarmRule;
import com.ih2ome.sunflower.entity.narcissus.SmartMistakeInfo;
import com.ih2ome.sunflower.vo.pageVo.enums.HouseStyleEnum;
import com.ih2ome.hardware_service.service.service.AmmeterAlarmService;
import com.ih2ome.sunflower.vo.pageVo.ammeter.AmmeterAlarmVo;
import com.ih2ome.hardware_service.service.peony.ammeterInterface.exception.AmmeterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void saveAmmeterAlarmRules(List <SmartAlarmRule> smartAlarmRuleList) throws AmmeterException {
        if(smartAlarmRuleList == null){
            throw new AmmeterException("参数为空");
        }
        ammeterAlarmDao.clearAmmeterAlarmRules();
        for(SmartAlarmRule smartAlarmRule:smartAlarmRuleList) {
            ammeterAlarmDao.addAmmeterAlarmRules(smartAlarmRule);
        }
    }

    @Override
    public List<SmartAlarmRule> getAllSmartAlarmRules() {
        return ammeterAlarmDao.getAllSmartAlarmRules();
    }

    @Override
    public SmartAlarmRule getByReportName(String reportName) throws AmmeterException {
        if(StringUtils.isEmpty(reportName)){
            throw new AmmeterException("参数为空");
        }
        return ammeterAlarmDao.getByReportName(reportName);
    }

    @Override
    public void saveAlarmList(List<SmartMistakeInfo> smartMistakeInfoList) throws AmmeterException {
        if(smartMistakeInfoList == null){
           throw new AmmeterException("参数错误");
        }
        ammeterAlarmDao.saveAlarmList(smartMistakeInfoList);
    }

    @Override
    public List<AmmeterAlarmVo> findAmmeterAlarmInfoList(AmmeterAlarmVo ammeterAlarmVo) throws AmmeterException {
        if(ammeterAlarmVo == null){
            throw new AmmeterException("参数错误");
        }
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
