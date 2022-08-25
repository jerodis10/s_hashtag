package com.s_hashtag.common.schedule.model.entity;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Table(name = "SCHEDULE")
@Entity
public class ScheduleEntity {

    @Id @GeneratedValue
    @Column(name = "SCHEDULE_ID")
    private Long id;

    private String scheduleDocumentId;

    private String scheduleName;

    private String cronPeriod;

    private String runtime;

    private String scheduleResult;

    private String jobName;

    private String jobResult;

    private String minLatitude;

    private String maxLatitude;

    private String minLongitude;

    private String maxLongitude;
}
