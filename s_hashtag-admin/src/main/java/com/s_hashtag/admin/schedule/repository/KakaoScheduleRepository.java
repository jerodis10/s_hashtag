package com.s_hashtag.admin.schedule.repository;

public interface KakaoScheduleRepository  {
    void scheduleCroneSave(String scheduleName, String crone);
}
