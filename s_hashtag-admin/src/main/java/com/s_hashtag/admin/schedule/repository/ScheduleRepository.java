package com.s_hashtag.admin.schedule.repository;

import com.s_hashtag.admin.schedule.model.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    void scheduleCroneSave(String scheduleName, String crone);

    Optional<Schedule> findById(String id);

    List<Schedule> findAll();
}
