package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.model.narcissus.SmartAlarmRule;

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
     * @param smartReport
     */
    void saveAmmeterAlarmRules(SmartAlarmRule smartReport);

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
}
