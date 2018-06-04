package com.ih2ome.hardware_server.server.scheduled;

import com.ih2ome.hardware_service.service.service.AmmeterAlarmService;
import com.ih2ome.hardware_service.service.peony.ammeterInterface.IAmmeter;
import com.ih2ome.hardware_service.service.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.sunflower.entity.narcissus.SmartAlarmRule;
import com.ih2ome.sunflower.entity.narcissus.SmartMistakeInfo;
import com.ih2ome.sunflower.vo.pageVo.enums.AlarmTypeEnum;
import com.ih2ome.sunflower.vo.pageVo.enums.SmartDeviceTypeEnum;
import com.ih2ome.sunflower.vo.thirdVo.ammeter.enums.AmmeterFirm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/5
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Component
@EnableScheduling
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
            iAmmeter = (IAmmeter) Class.forName(AmmeterFirm.POWER_BEE.getClazz()).newInstance();
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
    @Scheduled(cron="0 0 4 * * ?")
    public void getPowerBeeMissDevice(){
        IAmmeter iAmmeter = getIAmmeter();
        Log.info("====================获取离线设备任务开始==================");
        try {
            SmartAlarmRule smartAlarmRule= ammeterAlarmService.getByReportName(AlarmTypeEnum.LONG_TIME_OFF_LINE.getCode()+"");
            if(smartAlarmRule==null){
                Log.info("====================获取离线设备任务结束==================");
                return;
            }
            List<String> ids = iAmmeter.getMissDevice(Integer.valueOf(smartAlarmRule.getReportParam()));
            List<SmartMistakeInfo>smartMistakeInfoList = new ArrayList<>();
            for (String id: ids) {
                SmartMistakeInfo smartMistakeInfo = new SmartMistakeInfo();
                smartMistakeInfo.setUuid(id);
                smartMistakeInfo.setExceptionType(AlarmTypeEnum.LONG_TIME_OFF_LINE.getCode()+"");
                smartMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.POWER_BEE_AMMETER.getCode());
                smartMistakeInfoList.add(smartMistakeInfo);
            }
            ammeterAlarmService.saveAlarmList(smartMistakeInfoList);
        } catch (AmmeterException e) {
            Log.error("获取离线设备任务失败",e);
        }
        Log.info("====================获取离线设备任务结束==================");
    }

    /**
     * 蜂电获取长时间无数据上报设备
     */
    @Scheduled(cron="0 0 4 * * ?")
    public void getPowerBeeOnlineNoDataDevice(){
        IAmmeter iAmmeter = getIAmmeter();
        Log.info("====================获取长时间无数据上报任务开始==================");
        try {
            SmartAlarmRule smartAlarmRule= ammeterAlarmService.getByReportName(AlarmTypeEnum.DATA_IS_NOT_UPDATE.getCode()+"");
            if(smartAlarmRule==null){
                Log.info("====================获取长时间无数据上报任务结束==================");
                return;
            }
            List<String> ids = iAmmeter.getOnlineNoDataDevice(Integer.valueOf(smartAlarmRule.getReportParam()));
            List<SmartMistakeInfo>smartMistakeInfoList = new ArrayList<>();
            for (String id: ids) {
                SmartMistakeInfo smartMistakeInfo = new SmartMistakeInfo();
                smartMistakeInfo.setUuid(id);
                smartMistakeInfo.setExceptionType(AlarmTypeEnum.DATA_IS_NOT_UPDATE.getCode()+"");
                smartMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.POWER_BEE_AMMETER.getCode());
                smartMistakeInfoList.add(smartMistakeInfo);
            }
            ammeterAlarmService.saveAlarmList(smartMistakeInfoList);
        } catch (AmmeterException e) {
            Log.error("获取获取长时间无数据上报任务失败",e);
        }
        Log.info("====================获取获取长时间无数据上报任务结束==================");
    }

    /**
     * 蜂电获取空置未断电设备
     */
    @Scheduled(cron="0 0 4 * * ?")
    public void getPowerBeeVacantPowerOn(){
        IAmmeter iAmmeter = getIAmmeter();
        Log.info("====================获取获取长时间无数据上报任务开始==================");
        try {
            SmartAlarmRule smartAlarmRule= ammeterAlarmService.getByReportName(AlarmTypeEnum.POWER_CONSUMPTION_WITHOUT_CHECKIN.getCode()+"");
            if(smartAlarmRule==null){
                Log.info("====================获取获取长时间无数据上报任务结束==================");
                return;
            }
            List<String> ids = iAmmeter.getVacantPowerOn();
            List<SmartMistakeInfo>smartMistakeInfoList = new ArrayList<>();
            for (String id: ids) {
                SmartMistakeInfo smartMistakeInfo = new SmartMistakeInfo();
                smartMistakeInfo.setUuid(id);
                smartMistakeInfo.setExceptionType(AlarmTypeEnum.POWER_CONSUMPTION_WITHOUT_CHECKIN.getCode()+"");
                smartMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.POWER_BEE_AMMETER.getCode());
                smartMistakeInfoList.add(smartMistakeInfo);
            }
            ammeterAlarmService.saveAlarmList(smartMistakeInfoList);
        } catch (AmmeterException e) {
            Log.error("获取获取长时间无数据上报任务失败",e);
        }
        Log.info("====================获取获取长时间无数据上报任务结束==================");
    }

    /**
     * 蜂电获取负数电量设备与负数不断电设备
     */
    @Scheduled(cron="0 0 4 * * ?")
    public void getNegativeDeviceAndNegativePowerOnDevice(){
        IAmmeter iAmmeter = getIAmmeter();
        Map <String,List<String>> map = null;
        Log.info("====================获取电量为负数与电量为负数且未断电开始==================");
        try {
            map = iAmmeter.getNegativeDeviceAndNegativePowerOnDevice();
        } catch (AmmeterException e) {
            Log.error("获取电量为负数与电量为负数且未断电失败",e);
        }
        List <String> negativeDeviceList = map.get("negativeDeviceList");
        List <String> negativePowerOnDeviceList = map.get("negativePowerOnDeviceList");
        List<SmartMistakeInfo>smartMistakeInfoList = new ArrayList<>();
        for(String negativeDeviceUuid:negativeDeviceList){
            SmartMistakeInfo smartMistakeInfo = new SmartMistakeInfo();
            smartMistakeInfo.setUuid(negativeDeviceUuid);
            smartMistakeInfo.setExceptionType(AlarmTypeEnum.POWER_RATE_SMALL_THAN_ZERO.getCode()+"");
            smartMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.POWER_BEE_AMMETER.getCode());
            smartMistakeInfoList.add(smartMistakeInfo);
        }
        for(String negativePowerOnDeviceUuid:negativePowerOnDeviceList){
            SmartMistakeInfo smartMistakeInfo = new SmartMistakeInfo();
            smartMistakeInfo.setUuid(negativePowerOnDeviceUuid);
            smartMistakeInfo.setExceptionType(AlarmTypeEnum.POWER_NOT_FAILURE_WITH_POWER_RATE_THAN_ZERO.getCode()+"");
            smartMistakeInfo.setSmartDeviceType(SmartDeviceTypeEnum.POWER_BEE_AMMETER.getCode());
            smartMistakeInfoList.add(smartMistakeInfo);
        }
        try {
            ammeterAlarmService.saveAlarmList(smartMistakeInfoList);
        } catch (AmmeterException e) {
            Log.error("获取电量为负数与电量为负数且未断电失败",e);
        }
        Log.info("====================获取电量为负数与电量为负数且未断电结束==================");
    }
}
