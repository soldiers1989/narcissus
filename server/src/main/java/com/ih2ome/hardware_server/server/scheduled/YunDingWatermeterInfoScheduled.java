package com.ih2ome.hardware_server.server.scheduled;

import com.ih2ome.hardware_service.service.service.WatermeterScheduledService;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import com.ih2ome.sunflower.entity.narcissus.SmartDeviceV2;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeter;
import com.ih2ome.sunflower.entity.narcissus.SmartWatermeterAccountLog;
import com.ih2ome.sunflower.vo.pageVo.watermeter.UuidAndManufactoryVO;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/5
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Component
@EnableScheduling
public class YunDingWatermeterInfoScheduled {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WatermeterScheduledService watermeterScheduledService;

    @Autowired
    WatermeterService watermeterService;

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

    private IWatermeter getIWatermeter(){
        IWatermeter iWatermeter = null;
        try {
            iWatermeter = (IWatermeter) Class.forName(WATERMETER_FIRM.YUN_DING.getClazz()).newInstance();
        } catch (InstantiationException e) {
            Log.error("获取水表类失败",e);
        } catch (IllegalAccessException e) {
            Log.error("获取水表类失败",e);
        } catch (ClassNotFoundException e) {
            Log.error("获取水表类失败",e);
        }
        return iWatermeter;
    }
    /**
     * 定时获取水表抄表
     */
    @Scheduled(cron="0 0/5 * * * ?")
    public void getWatermeterRecord() {
        Log.info("====================水表抄表任务开始==================");
        List<SmartDeviceV2> smartDeviceList = watermeterService.getAllSmartDeviceV2List();
        Log.info("*** getWatermeterRecord *** 待抄表个数：{}", smartDeviceList.size());
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.HOUR, -3);
        Date beforeDate = beforeTime.getTime();
        try {
            IWatermeter iWatermeter = getIWatermeter();
            for (SmartDeviceV2 device : smartDeviceList) {
                SmartWatermeter watermeter = watermeterService.getWatermeterByDeviceId(Integer.parseInt(device.getSmartDeviceId()));
                if(watermeter != null && (watermeter.getMeterUpdatedAt() == null || watermeter.getMeterUpdatedAt().before(beforeDate))) {
                    iWatermeter.readWatermeter(device.getThreeId(), device.getProviderCode(), device.getCreatedBy());
                    Log.info("*** getWatermeterRecord *** 抄表请求完成：{}", device.getSmartDeviceId());
                }
                else {
                    Log.info("*** getWatermeterRecord *** 抄表请求跳过：{}", device.getSmartDeviceId());
                }
            }
        } catch (Exception ex) {
            Log.error("task read amount error!", ex);
        }
        Log.info("====================水表抄表任务结束==================");
    }
}
