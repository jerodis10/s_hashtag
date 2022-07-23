package com.s_hashtag.admin.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Schedule {

    private String schedule_id;
    private String schedule_name;
    private String cron_period;
    private String runtime;
    private String schedule_result;
    private String job_name;
    private String job_result;
}
