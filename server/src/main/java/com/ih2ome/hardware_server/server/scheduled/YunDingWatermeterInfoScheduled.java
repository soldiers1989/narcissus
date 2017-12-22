package com.ih2ome.hardware_server.server.scheduled;

import com.ih2ome.hardware_service.service.service.WatermeterScheduledService;
import com.ih2ome.hardware_service.service.service.WatermeterService;
import com.ih2ome.hardware_service.service.vo.UuidAndManufactoryVO;
import com.ih2ome.peony.watermeterInterface.IWatermeter;
import com.ih2ome.peony.watermeterInterface.enums.WATERMETER_FIRM;
import com.ih2ome.peony.watermeterInterface.exception.WatermeterException;
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
    @Scheduled(cron="0 0 4 * * ?")
    public void getPowerBeeMissDevice() {
        IWatermeter iWatermeter = getIWatermeter();
        Log.info("====================水表抄表任务开始==================");
        //获取uuids和manufactorys
        List<UuidAndManufactoryVO> uuidAndManufactoryVOS= watermeterService.findWatermeterUuidAndManufactory();
        for (UuidAndManufactoryVO uuidAndManufactoryVO:uuidAndManufactoryVOS
             ) {
            try {
                iWatermeter.readWatermeter(uuidAndManufactoryVO.getUuid(),uuidAndManufactoryVO.getManufactory());
            } catch (WatermeterException e) {
                Log.error("水表抄表任务失败",e);
            }
        }
        Log.info("====================水表抄表任务结束==================");
    }
}
