package com.s_hashtag.common.schedule.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SCHEDULE")
@Entity
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "SCHEDULE_ID")
    private String scheduleId;

    @Column(name = "SCHEDULE_NAME")
    private String scheduleName;

    @Column(name = "CRON_PERIOD")
    private String cronPeriod;

    @Column(name = "RUNTIME")
    private String runtime;

    @Column(name = "SCHEDULE_RESULT")
    private String scheduleResult;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "JOB_RESULT")
    private String jobResult;

    @Column(name = "MIN_LATITUDE")
    private String minLatitude;

    @Column(name = "MAX_LATITUDE")
    private String maxLatitude;

    @Column(name = "MIN_LONGITUDE")
    private String minLongitude;

    @Column(name = "MAX_LONGITUDE")
    private String maxLongitude;
}
