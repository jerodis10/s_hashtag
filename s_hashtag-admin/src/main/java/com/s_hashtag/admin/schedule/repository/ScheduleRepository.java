package com.s_hashtag.admin.schedule.repository;

public interface ScheduleRepository {
    void scheduleCroneSave(String scheduleName, String crone);
}
