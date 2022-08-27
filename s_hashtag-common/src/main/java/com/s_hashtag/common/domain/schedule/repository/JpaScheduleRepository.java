package com.s_hashtag.common.domain.schedule.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s_hashtag.common.domain.schedule.model.entity.Schedule;
import com.s_hashtag.common.domain.schedule.dto.external.ScheduleDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

import static com.s_hashtag.common.domain.schedule.model.entity.QSchedule.schedule;

@Primary
@Repository
public class JpaScheduleRepository implements ScheduleRepository{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public JpaScheduleRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ScheduleDto findById(String id) {
        return queryFactory
                .select(Projections.fields(ScheduleDto.class,
                        schedule.scheduleDocumentId,
                        schedule.scheduleName,
                        schedule.scheduleResult,
                        schedule.cronPeriod,
                        schedule.jobName,
                        schedule.jobResult,
                        schedule.runtime,
                        schedule.minLatitude,
                        schedule.maxLatitude,
                        schedule.minLongitude,
                        schedule.maxLongitude))
                .from(schedule)
                .where(schedule.scheduleDocumentId.eq(id))
                .fetchOne();
    }

    @Override
    public List<ScheduleDto> findAll() {
        return queryFactory
                .select(Projections.fields(ScheduleDto.class,
                        schedule.scheduleDocumentId,
                        schedule.scheduleName,
                        schedule.scheduleResult,
                        schedule.cronPeriod,
                        schedule.jobName,
                        schedule.jobResult,
                        schedule.runtime,
                        schedule.minLatitude,
                        schedule.maxLatitude,
                        schedule.minLongitude,
                        schedule.maxLongitude))
                .from(schedule)
                .fetch();
    }

    @Override
    public void scheduleCroneSave(String scheduleId, String expression) {

    }

    @Override
    public void scheduleSave(String scheduleId, String expression, String min_latitude, String max_latitude, String min_longitude, String max_longitude) {
        Schedule findSchedule = queryFactory
                .selectFrom(schedule)
                .where(schedule.scheduleDocumentId.eq(scheduleId))
                .fetchOne();

        findSchedule.setCronPeriod(expression);
        findSchedule.setMinLatitude(min_latitude);
        findSchedule.setMaxLatitude(max_latitude);
        findSchedule.setMinLongitude(min_longitude);
        findSchedule.setMaxLongitude(max_longitude);
    }
}


