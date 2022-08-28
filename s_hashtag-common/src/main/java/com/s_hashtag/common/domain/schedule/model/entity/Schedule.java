package com.s_hashtag.common.domain.schedule.model.entity;

import com.s_hashtag.common.model.entity.BaseEntity;
import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SCHEDULE")
@Entity
public class Schedule extends BaseEntity {

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

    @Builder
    public Schedule(String scheduleDocumentId, String cronPeriod, String minLatitude, String maxLatitude, String minLongitude, String maxLongitude) {
        this.scheduleDocumentId = scheduleDocumentId;
        this.cronPeriod = cronPeriod;
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
    }
}
