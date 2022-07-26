package com.s_hashtag.scheduler.config;

import com.s_hashtag.InstagramScheduler;
import com.s_hashtag.batch.config.JobConfiguration;
import com.s_hashtag.common.schedule.repository.ScheduleRepository;
import com.s_hashtag.scheduler.CronPeriod;
import com.s_hashtag.scheduler.KakaoScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class KakaoSchedulerConfig {

    private final JobConfiguration jobConfiguration;
    private final JobLauncher jobLauncher;
    private final ScheduleRepository scheduleRepository;

//    private static final String EXPRESSION = "0 0 2 1 * ?";

//    @Bean
//    public KakaoScheduler kakaoPlaceScheduler() {
//        return new KakaoScheduler(()-> System.out.println("aaa"), new CronPeriod("0/3 * * * * *"));
//    }

    @Bean
    public KakaoScheduler kakaoPlaceScheduler() {
        String expression = scheduleRepository.findById("KakaoScheduler").getCron_period();
        return new KakaoScheduler(getKakaoPlaceJob(), new CronPeriod(expression));
    }

//    @Bean
//    public InstagramScheduler InstagramCrawlingScheduler() {
//        return new InstagramScheduler(()-> log.info("aaa"), new CronPeriod("0/3 * * * * *"));
//    }

    @Bean
    public InstagramScheduler InstagramCrawlingScheduler() {
        String expression = scheduleRepository.findById("InstagramScheduler").getCron_period();
        return new InstagramScheduler(getInstagramCrawlingJob(), new CronPeriod(expression));
    }
//
//    @Bean
//    public KakaoInstagramScheduler kakaoInstagramCrawlingScheduler(String expression) {
//        return new KakaoInstagramScheduler(getkakaoInstagramCrawlingJob(), new CronPeriod(expression));
//    }

    private Runnable getKakaoPlaceJob() {
        return () -> {
            // job parameter 설정
            Map<String, JobParameter> confMap = new HashMap<>();
            confMap.put("time", new JobParameter(System.currentTimeMillis()));
            JobParameters jobParameters = new JobParameters(confMap);

            try {
                log.info("aaa");
                jobLauncher.run(jobConfiguration.kakaoPlaceJob(), jobParameters);
            } catch (JobInstanceAlreadyCompleteException e) {
                log.error(e.getMessage());
            } catch (JobExecutionAlreadyRunningException e) {
                log.error(e.getMessage());
            } catch (JobParametersInvalidException e) {
                log.error(e.getMessage());
            } catch (JobRestartException e) {
                log.error(e.getMessage());
            }
        };
    }

    private Runnable getInstagramCrawlingJob() {
        return () -> {
            // job parameter 설정
            Map<String, JobParameter> confMap = new HashMap<>();
            confMap.put("time", new JobParameter(System.currentTimeMillis()));
            JobParameters jobParameters = new JobParameters(confMap);

            try {
                log.info("aaa");
                jobLauncher.run(jobConfiguration.InstagramCrawlingJob(), jobParameters);
            } catch (JobInstanceAlreadyCompleteException e) {
                log.error(e.getMessage());
            } catch (JobExecutionAlreadyRunningException e) {
                log.error(e.getMessage());
            } catch (JobParametersInvalidException e) {
                log.error(e.getMessage());
            } catch (JobRestartException e) {
                log.error(e.getMessage());
            }
        };
    }

    private Runnable getkakaoInstagramCrawlingJob() {
        return () -> {
            // job parameter 설정
            Map<String, JobParameter> confMap = new HashMap<>();
            confMap.put("time", new JobParameter(System.currentTimeMillis()));
            JobParameters jobParameters = new JobParameters(confMap);

            try {
                jobLauncher.run(jobConfiguration.kakaoInstagramCrawlingJob(), jobParameters);
            } catch (JobInstanceAlreadyCompleteException e) {
                log.error(e.getMessage());
            } catch (JobExecutionAlreadyRunningException e) {
                log.error(e.getMessage());
            } catch (JobParametersInvalidException e) {
                log.error(e.getMessage());
            } catch (JobRestartException e) {
                log.error(e.getMessage());
            }
        };
    }

}


