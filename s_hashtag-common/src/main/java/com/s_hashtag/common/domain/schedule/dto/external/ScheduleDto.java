package com.s_hashtag.common.domain.schedule.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ScheduleDto {

    private String scheduleId;
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
