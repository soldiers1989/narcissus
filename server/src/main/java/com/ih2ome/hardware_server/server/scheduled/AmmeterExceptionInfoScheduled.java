package com.ih2ome.hardware_server.server.scheduled;

import com.ih2ome.hardware_service.service.enums.AlarmTypeEnum;
import com.ih2ome.hardware_service.service.model.narcissus.SmartAlarmRule;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;
import com.ih2ome.hardware_service.service.service.AmmeterAlarmService;
import com.ih2ome.peony.ammeterInterface.IAmmeter;
import com.ih2ome.peony.ammeterInterface.enums.AMMETER_FIRM;
import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/5
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class AmmeterExceptionInfoScheduled {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AmmeterAlarmService ammeterAlarmService;
    /**
     *设置线程池，多线程跑批
     * @return
     */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }

    private IAmmeter getIAmmeter(){
        IAmmeter iAmmeter = null;
        try {
            iAmmeter = (IAmmeter) Class.forName(AMMETER_FIRM.POWER_BEE.getClazz()).newInstance();
        } catch (InstantiationException e) {
            Log.error("获取电表类失败",e);
        } catch (IllegalAccessException e) {
            Log.error("获取电表类失败",e);
        } catch (ClassNotFoundException e) {
            Log.error("获取电表类失败",e);
        }
        return iAmmeter;
    }
    /**
     * 蜂电获取离线设备任务
     */
    @Scheduled(fixedDelay = 5000)
    public void getPowerBeeMissDevice(){
        IAmmeter iAmmeter = getIAmmeter();
        Log.info("====================获取离线设备任务开始==================");
        try {
            SmartAlarmRule smartAlarmRule= ammeterAlarmService.getByReportName(AlarmTypeEnum.LONG_TIME_OFF_LINE.getCode()+"");
            List<String> ids = iAmmeter.getMissDevice(Integer.valueOf(smartAlarmRule.getReportParam()));
            List<SmartMistakeInfo>smartMistakeInfoList = new ArrayList<>();
            for (String id: ids) {
                SmartMistakeInfo smartMistakeInfo = new SmartMistakeInfo();
                smartMistakeInfo.setUuid(id);
                smartMistakeInfo.setExceptionType(AlarmTypeEnum.LONG_TIME_OFF_LINE.getCode()+"");

                smartMistakeInfoList.add(smartMistakeInfo);
            }
        } catch (AmmeterException e) {
            Log.error("获取离线设备任务失败",e);
        }
        Log.info("====================获取离线设备任务结束==================");
    }

    /**
     * 蜂电获取长时间无数据上报设备
     */
    @Scheduled(fixedDelay = 5000)
    public void getPowerBeeOnlineNoDataDevice(){
        IAmmeter iAmmeter = getIAmmeter();
        Log.info("====================获取获取长时间无数据上报任务开始==================");
        try {
            SmartAlarmRule smartAlarmRule= ammeterAlarmService.getByReportName(AlarmTypeEnum.DATA_IS_NOT_UPDATE.getCode()+"");
            List<String> ids = iAmmeter.getOnlineNoDataDevice(Integer.valueOf(smartAlarmRule.getReportParam()));
        } catch (AmmeterException e) {
            Log.error("获取获取长时间无数据上报任务失败",e);
        }
        Log.info("====================获取获取长时间无数据上报任务结束==================");
    }

    /**
     * 蜂电获取空置未断电设备
     */
    @Scheduled(fixedDelay = 5000)
    public void getPowerBeeVacantPowerOn(){
        IAmmeter iAmmeter = getIAmmeter();
        Log.info("====================获取获取长时间无数据上报任务开始==================");
        try {
            SmartAlarmRule smartAlarmRule= ammeterAlarmService.getByReportName(AlarmTypeEnum.POWER_CONSUMPTION_WITHOUT_CHECKIN.getCode()+"");
            List<String> ids = iAmmeter.getVacantPowerOn(Integer.valueOf(smartAlarmRule.getReportParam()));
        } catch (AmmeterException e) {
            Log.error("获取获取长时间无数据上报任务失败",e);
        }
        Log.info("====================获取获取长时间无数据上报任务结束==================");
    }
}
