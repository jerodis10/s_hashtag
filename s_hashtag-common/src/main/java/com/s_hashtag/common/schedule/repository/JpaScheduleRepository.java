package com.s_hashtag.common.schedule.repository;

import com.s_hashtag.common.schedule.model.vo.Schedule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaScheduleRepository implements ScheduleRepository{
    @Override
    public Schedule findById(String id) {
        return null;
    }

    @Override
    public List<Schedule> findAll() {
        return null;
    }

    @Override
    public void scheduleCroneSave(String scheduleId, String expression) {

    }

    @Override
    public void scheduleSave(String scheduleId, String expression, String min_latitude, String max_latitude, String min_longitude, String max_longitude) {

    }
}
