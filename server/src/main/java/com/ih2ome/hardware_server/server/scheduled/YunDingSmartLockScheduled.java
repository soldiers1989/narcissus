package com.ih2ome.hardware_server.server.scheduled;

import com.ih2ome.common.utils.CacheUtils;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.yunding.util.YunDingSmartLockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/2/26
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Component
@EnableScheduling
public class YunDingSmartLockScheduled {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());
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

    @Scheduled(cron="0 0 0 * * ?")
    public void flushToken(){
        Log.info("**************开始批量刷新token***************");
        Set<String> tokens = CacheUtils.keys("refresh_token_key*");
        for (String token:tokens) {
            try {
                Log.info("******************刷新"+token+"**********************");
                YunDingSmartLockUtil.flushRefreshTokenByToken(token);
            } catch (SmartLockException e) {
                Log.error(token+"token刷新失败");
                e.printStackTrace();
            }
        }
        Log.info("**************结束批量刷新token***************");
    }

}
