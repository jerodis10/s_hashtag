package com.s_hashtag.scheduler.model.vo;

import com.s_hashtag.scheduler.exception.KakaoSchedulerException;
import com.s_hashtag.scheduler.exception.KakaoSchedulerExceptionStatus;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Getter
public class InstagramScheduler {
    private final ThreadPoolTaskScheduler scheduler;
    private final CronPeriod cronPeriod;
    private final Runnable runnable;
    private ScheduledFuture<?> scheduledFuture;

    public InstagramScheduler(Runnable runnable, CronPeriod cronPeriod) {
        this.scheduler = new ThreadPoolTaskScheduler();
        this.scheduler.setThreadNamePrefix("my-scheduled-task-pool-");
        this.scheduler.setWaitForTasksToCompleteOnShutdown(true);
        this.scheduler.initialize();
        this.runnable = runnable;
        this.cronPeriod = cronPeriod;
    }

    public void start() {
        if (isActive()) {
            log.info("InstagramScheduler already running");
            throw new KakaoSchedulerException(KakaoSchedulerExceptionStatus.SCHEDULE_ALREADY_RUNNING);
        }
        this.scheduledFuture = this.scheduler.schedule(this.runnable, getTrigger());
        log.info("InstagramScheduler started at : " + LocalDateTime.now());
    }

    public boolean isActive() {
        return this.scheduledFuture != null && !this.scheduledFuture.isCancelled();
    }

    public boolean isNotActive() {
        return !isActive();
    }

    private Trigger getTrigger() {
        return this.cronPeriod.getTrigger();
    }

    @PreDestroy
    public void end() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.scheduler.shutdown();
        log.info("InstagramScheduler destroyed at : " + LocalDateTime.now());
    }

    public void changePeriod(String expression) {
        if (isActive()) {
            log.info("Can't change period, InstagramScheduler is running");
            throw new KakaoSchedulerException(KakaoSchedulerExceptionStatus.SCHEDULE_ALREADY_RUNNING);
        }
        this.cronPeriod.change(expression);
        log.info("InstagramScheduler cron period changed at : " + LocalDateTime.now());
    }

    public boolean stop() {
        if (isNotActive()) {
            log.info("InstagramScheduler already stopped");
            throw new KakaoSchedulerException(KakaoSchedulerExceptionStatus.SCHEDULE_ALREADY_STOPPED);
        }
        this.scheduledFuture.cancel(true);
        log.info("InstagramScheduler stopped at : " + LocalDateTime.now());
        return this.scheduledFuture.isCancelled();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}