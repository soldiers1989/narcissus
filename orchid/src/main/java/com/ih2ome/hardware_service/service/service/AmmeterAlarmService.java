package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.model.narcissus.SmartAlarmRule;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;
import com.ih2ome.hardware_service.service.vo.AmmeterAlarmVo;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;

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
    void saveAmmeterAlarmRules(List <SmartAlarmRule> smartAlarmRuleList) throws AmmeterException;

    /**
     * 查看报警规则
     * @return
     */
    List<SmartAlarmRule> getAllSmartAlarmRules();

    /**
     * 通过报警名称获取报警规则信息
     * @return
     */
    SmartAlarmRule getByReportName(String reportName) throws AmmeterException;

    /**
     * 批量保存报警信息
     * @param smartMistakeInfoList
     */
    void saveAlarmList(List<SmartMistakeInfo>smartMistakeInfoList) throws AmmeterException;

    /**
     * 查看报警信息
     * @param ammeterAlarmVo
     * @return
     */
    List<AmmeterAlarmVo> findAmmeterAlarmInfoList(AmmeterAlarmVo ammeterAlarmVo) throws AmmeterException;
}
