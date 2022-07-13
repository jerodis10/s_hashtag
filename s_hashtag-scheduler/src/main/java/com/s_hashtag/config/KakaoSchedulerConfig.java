package com.s_hashtag.config;

import com.s_hashtag.CronPeriod;
import com.s_hashtag.KakaoScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.LocalDateTime;

@Slf4j
@Configuration
public class KakaoSchedulerConfig implements SchedulingConfigurer {
    private static final String EXPRESSION = "0 0 2 1 * ?";

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        KakaoScheduler kakaoScheduler = new KakaoScheduler(getRunnable(), new CronPeriod(EXPRESSION));
        scheduledTaskRegistrar.setTaskScheduler(kakaoScheduler.getScheduler());
    }

    private Runnable getRunnable() {
        return () -> {
            log.info("KakaoScheduler started at : " + LocalDateTime.now());
        };
    }
}
