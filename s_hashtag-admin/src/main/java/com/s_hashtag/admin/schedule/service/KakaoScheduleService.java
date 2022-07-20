package com.s_hashtag.admin.schedule.service;

import com.s_hashtag.KakaoScheduler;
import com.s_hashtag.admin.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoScheduleService {

    private final KakaoScheduler kakaoScheduler;
//    private final InstagramScheduler instagramScheduler;
//    private final KakaoInstagramScheduler kakaoInstagramScheduler;
    private final ScheduleRepository scheduleRepository;
//    private final KakaoSchedulerConfig kakaoSchedulerConfig;

    @Transactional
    public void changeSchedulePeriod(String scheduleName, String expression) {
        scheduleRepository.scheduleCroneSave(scheduleName, expression);
    }

    public boolean getKakaoScheduleActiveStatus(String scheduleName) {
        if(scheduleName.equals(kakaoScheduler.toString())) {
            return kakaoScheduler.isActive();
        }
//        else if(scheduleName.equals(instagramScheduler.toString())) {
//            return instagramScheduler.isActive();
//        } else if(scheduleName.equals(kakaoInstagramScheduler.toString())) {
//            return kakaoInstagramScheduler.isActive();
//        }

        return true;
    }

    public void startSchedule(String scheduleName) {
        if(scheduleName.equals(kakaoScheduler.toString())) {
            kakaoScheduler.start();
        }
//        else if(scheduleName.equals(instagramScheduler.toString())) {
//            instagramScheduler.start();
//        } else if(scheduleName.equals(kakaoInstagramScheduler.toString())) {
//            kakaoInstagramScheduler.start();
//        }

//        kakaoSchedulerConfig.kakaoPlaceScheduler()
    }

    public void stopSchedule(String scheduleName) {
        if(scheduleName.equals(kakaoScheduler.toString())) {
            kakaoScheduler.stop();
        }
//        else if(scheduleName.equals(instagramScheduler.toString())) {
//            instagramScheduler.stop();
//        } else if(scheduleName.equals(kakaoInstagramScheduler.toString())) {
//            kakaoInstagramScheduler.stop();
//        }
    }

}
