package com.s_hashtag.common.domain.schedule.repository;

import com.s_hashtag.common.domain.schedule.dto.external.ScheduleDto;

import java.util.List;

public interface ScheduleRepository {

    ScheduleDto findById(String id);

    List<ScheduleDto> findAll();

    void scheduleCroneSave(String scheduleId, String cronPeriod);

    void scheduleSave(String scheduleId,
                      String cronPeriod,
                      String minLatitude,
                      String maxLatitude,
                      String minLongitude,
                      String maxLongitude);
}

