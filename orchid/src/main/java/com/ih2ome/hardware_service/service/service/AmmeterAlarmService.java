package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.model.narcissus.SmartAlarmRule;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;
import com.ih2ome.hardware_service.service.vo.AmmeterMannagerVo;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/7
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public interface AmmeterAlarmService {
    /**
     * 添加报警规则
     * @param smartAlarmRuleList
     */
    void saveAmmeterAlarmRules(List <SmartAlarmRule> smartAlarmRuleList);

    /**
     * 查看报警规则
     * @return
     */
    List<SmartAlarmRule> getAllSmartAlarmRules();

    /**
     * 通过报警名称获取报警规则信息
     * @return
     */
    SmartAlarmRule getByReportName(String reportName);

    /**
     * 批量保存报警信息
     * @param smartMistakeInfoList
     */
    void saveAlarmList(List<SmartMistakeInfo>smartMistakeInfoList);

    /**
     * 查看报警信息
     * @param ammeterMannagerVo
     * @return
     */
    List<AmmeterMannagerVo> findAmmeterAlarmInfoList(AmmeterMannagerVo ammeterMannagerVo);
}
