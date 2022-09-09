package com.s_hashtag.common.domain.schedule.dto.external;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
//@Setter
@Builder
public class ScheduleDto {

    private String scheduleId;

    @JsonAlias("schedule_document_id")
    private String scheduleDocumentId;

    private String scheduleName;

    @JsonAlias("cron_period")
    private String cronPeriod;

    private String runtime;

    private String scheduleResult;

    private String jobName;

    private String jobResult;

    @JsonAlias("min_latitude")
    private String minLatitude;

    @JsonAlias("max_latitude")
    private String maxLatitude;

    @JsonAlias("min_longitude")
    private String minLongitude;

    @JsonAlias("max_longitude")
    private String maxLongitude;

    private List<String> checkedList;

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "scheduleId='" + scheduleId + '\'' +
                ", scheduleDocumentId='" + scheduleDocumentId + '\'' +
                ", scheduleName='" + scheduleName + '\'' +
                ", cronPeriod='" + cronPeriod + '\'' +
                ", runtime='" + runtime + '\'' +
                ", scheduleResult='" + scheduleResult + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobResult='" + jobResult + '\'' +
                ", minLatitude='" + minLatitude + '\'' +
                ", maxLatitude='" + maxLatitude + '\'' +
                ", minLongitude='" + minLongitude + '\'' +
                ", maxLongitude='" + maxLongitude + '\'' +
                '}';
    }
}
