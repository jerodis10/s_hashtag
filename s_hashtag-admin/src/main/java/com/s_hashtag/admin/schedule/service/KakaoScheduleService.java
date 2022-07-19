package com.s_hashtag.admin.schedule.service;

import com.s_hashtag.KakaoScheduler;
import com.s_hashtag.admin.schedule.repository.KakaoScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoScheduleService {
    private final KakaoScheduler kakaoScheduler;
    private final KakaoScheduleRepository kakaoScheduleRepository;

    @Transactional
    public void changeSchedulePeriod(String scheduleName, String expression) {
        kakaoScheduleRepository.scheduleCroneSave(scheduleName, expression);
    }

    public boolean getKakaoScheduleActiveStatus() {
        return kakaoScheduler.isActive();
    }


    public void startSchedule() {
        kakaoScheduler.start();
    }

    public void stopSchedule() {
        kakaoScheduler.stop();
    }

    @Transactional
    public void toggleScheduleAutoRunnable(final String name) {
//        Schedule schedule = scheduleRepository.findByName(name)
//                .orElseThrow(() -> new AdminException(
//                        AdminExceptionStatus.NOT_FOUND_SCHEDULER,
//                        String.format("스케쥴러(%s)가 존재하지 않습니다.", name)
//                ));
//        schedule.toggle();
    }

}
