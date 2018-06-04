package com.ih2ome.hardware_server.server.scheduled;

import com.alibaba.fastjson.JSON;
import com.ih2ome.hardware_service.service.service.SmartLockService;
import com.ih2ome.sunflower.vo.pageVo.smartLock.PasswordRoomVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * create by 2018/2/26
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Component
@EnableScheduling
public class YunDingSmartLockScheduled {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SmartLockService smartLockService;

    @Value("${sms.baseUrl}")
    private String smsBaseUrl;

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

    /**
     * 定时任务：逾期冻结密码
     */
    @Scheduled(cron = "0 0 9 * * ?")
    //@Scheduled(cron = "0 0/10 * * * ?")
    public void overduePasswordFrozen() {
        Log.info("=================逾期冻结密码任务开始======================");
        List<PasswordRoomVO> passwordRoomList = smartLockService.getPasswordRoomList();
        Log.info("overduePasswordFrozen passwordRoomList.size() = {}",passwordRoomList.size());
        for (PasswordRoomVO item : passwordRoomList) {
            Log.info("overduePasswordFrozen item = {}", JSON.toJSONString(item));
            boolean hasOverdue = smartLockService.judgeRoomOverdue(item, smsBaseUrl);
            Log.info("overduePasswordFrozen hasOverdue = {}", hasOverdue);
            if (hasOverdue) {
                try {
                    smartLockService.frozenLockPassword(item.getCreatedBy(), item.getSmartLockPasswordId());
                    Log.info("overduePasswordFrozen frozenLockPassword done");
                    smartLockService.sendFrozenMessage(item, smsBaseUrl, true);
                    Log.info("overduePasswordFrozen sendFrozenMessage done");
                } catch (Exception ex) {
                    Log.error(String.format("定时任务：逾期冻结密码报错,PasswordId:%s",item.getSmartLockPasswordId()), ex);
                }
            }
        }
        Log.info("=================逾期断电任务结束=========================");
    }

}
