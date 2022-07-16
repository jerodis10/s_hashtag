package com.s_hashtag.config;

import com.s_hashtag.CronPeriod;
import com.s_hashtag.KakaoScheduler;
import com.s_hashtag.batch.config.JobConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

    private final JobConfiguration jobConfiguration;

//    private static final String EXPRESSION = "0 0 2 1 * ?";

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//        KakaoScheduler kakaoScheduler = new KakaoScheduler(getRunnable(), new CronPeriod(EXPRESSION));
//        KakaoScheduler kakaoScheduler = new KakaoScheduler(getRunnable(), new CronPeriod("0 0 2 1 * ?"));
        KakaoScheduler kakaoScheduler = new KakaoScheduler(jobConfiguration.kakaoJob(), new CronPeriod("0 0 2 1 * ?"));
        scheduledTaskRegistrar.setTaskScheduler(kakaoScheduler.getScheduler());
    }

    private Runnable getRunnable() {
        return () -> {
            log.info("KakaoScheduler started at : " + LocalDateTime.now());
        };
    }
}


