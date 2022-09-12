package com.s_hashtag.scheduler.service;

import com.s_hashtag.common.domain.schedule.repository.ScheduleRepository;
import com.s_hashtag.scheduler.model.vo.InstagramScheduler;
import com.s_hashtag.scheduler.model.vo.KakaoScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoScheduleService {

    private final KakaoScheduler kakaoScheduler;
    private final InstagramScheduler instagramScheduler;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void changeSchedulePeriod(String scheduleId, String cronPeriod) {
        scheduleRepository.scheduleCroneSave(scheduleId, cronPeriod);
    }

    @Transactional
    public void changeSchedule(String scheduleId,
                               String cronPeriod,
                               String minLatitude,
                               String maxLatitude,
                               String minLongitude,
                               String maxLongitude
    ) {
        scheduleRepository.scheduleSave(scheduleId, cronPeriod, minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

    public boolean getKakaoScheduleActiveStatus(String scheduleName) {
        if(scheduleName.equals(kakaoScheduler.toString())) {
            return kakaoScheduler.isActive();
        } else if(scheduleName.equals(instagramScheduler.toString())) {
            return instagramScheduler.isActive();
        }

        return true;
    }

    public void startSchedule(String scheduleId) {
        if(scheduleId.equals(kakaoScheduler.toString())) {
            kakaoScheduler.start();
        } else if(scheduleId.equals(instagramScheduler.toString())) {
            instagramScheduler.start();
        }
    }

    public void stopSchedule(String scheduleName) {
        if(scheduleName.equals(kakaoScheduler.toString())) {
            kakaoScheduler.stop();
        } else if(scheduleName.equals(instagramScheduler.toString())) {
            instagramScheduler.stop();
        }

    }
}
