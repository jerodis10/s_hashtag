package com.s_hashtag.common.domain.schedule.dto.external;

import lombok.*;

import java.util.List;

//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ScheduleDto {

    private String scheduleId;
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
    private List<String> checkedList;
}
