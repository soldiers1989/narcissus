package com.ih2ome.hardware_server.server.scheduled;

import com.ih2ome.hardware_service.service.service.WatermeterScheduledService;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import com.ih2ome.sunflower.entity.narcissus.SmartDeviceV2;
import com.ih2ome.hardware_service.service.peony.watermeterInterface.IWatermeter;
import com.ih2ome.sunflower.vo.thirdVo.watermeter.enums.WATERMETER_FIRM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

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
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            Log.error("获取水表类失败",e);
        }
        return iWatermeter;
    }
    /**
     * 定时获取水表抄表
     */
    @Scheduled(cron="0 0 * * * ?")
    public void getWatermeterRecord() {
        Log.info("====================getWatermeterRecord start==================");
        List<SmartDeviceV2> smartDeviceList = watermeterService.getAllSmartDeviceV2List();
        Log.info("*** getWatermeterRecord *** 待抄表个数：{}", smartDeviceList.size());
        try {
            IWatermeter iWatermeter = getIWatermeter();
            for (SmartDeviceV2 device : smartDeviceList) {
                iWatermeter.readWatermeter(device.getThreeId(), device.getProviderCode(), device.getCreatedBy());
                Log.info("*** getWatermeterRecord *** 抄表请求完成：{}", device.getSmartDeviceId());
            }
        } catch (Exception ex) {
            Log.error("task read amount error!", ex);
        }
        Log.info("====================getWatermeterRecord end==================");
    }
}
