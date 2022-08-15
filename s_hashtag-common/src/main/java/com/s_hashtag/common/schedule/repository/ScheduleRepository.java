package com.s_hashtag.common.schedule.repository;

import com.s_hashtag.common.schedule.model.entity.ScheduleEntity;
import com.s_hashtag.common.schedule.model.vo.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository {

//    Optional<Schedule> findById(String id);

    Schedule findById(String id);

    List<Schedule> findAll();

    void scheduleCroneSave(String scheduleId, String expression);

    void scheduleSave(String scheduleId,
                      String expression,
                      String min_latitude,
                      String max_latitude,
                      String min_longitude,
                      String max_longitude);
}

