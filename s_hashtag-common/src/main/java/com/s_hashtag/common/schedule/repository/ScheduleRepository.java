package com.s_hashtag.common.schedule.repository;

import com.s_hashtag.common.schedule.dto.external.ScheduleDto;

import java.util.List;

public interface ScheduleRepository {

//    Optional<Schedule> findById(String id);

    ScheduleDto findById(String id);

    List<ScheduleDto> findAll();

    void scheduleCroneSave(String scheduleId, String expression);

    void scheduleSave(String scheduleId,
                      String expression,
                      String min_latitude,
                      String max_latitude,
                      String min_longitude,
                      String max_longitude);
}

