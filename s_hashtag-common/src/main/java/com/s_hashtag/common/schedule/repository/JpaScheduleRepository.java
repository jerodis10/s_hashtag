package com.s_hashtag.common.schedule.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s_hashtag.common.schedule.model.entity.ScheduleEntity;
import com.s_hashtag.common.schedule.model.vo.Schedule;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

import static com.s_hashtag.common.schedule.model.entity.QScheduleEntity.scheduleEntity;

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
    public Schedule findById(String id) {
        return queryFactory
                .select(Projections.fields(Schedule.class,
                        scheduleEntity.scheduleDocumentId,
                        scheduleEntity.scheduleName,
                        scheduleEntity.scheduleResult,
                        scheduleEntity.cronPeriod,
                        scheduleEntity.jobName,
                        scheduleEntity.jobResult,
                        scheduleEntity.runtime,
                        scheduleEntity.minLatitude,
                        scheduleEntity.maxLatitude,
                        scheduleEntity.minLongitude,
                        scheduleEntity.maxLongitude))
                .from(scheduleEntity)
                .where(scheduleEntity.scheduleDocumentId.eq(id))
                .fetchOne();
    }

    @Override
    public List<Schedule> findAll() {
        return queryFactory
                .select(Projections.fields(Schedule.class,
                        scheduleEntity.scheduleDocumentId,
                        scheduleEntity.scheduleName,
                        scheduleEntity.scheduleResult,
                        scheduleEntity.cronPeriod,
                        scheduleEntity.jobName,
                        scheduleEntity.jobResult,
                        scheduleEntity.runtime,
                        scheduleEntity.minLatitude,
                        scheduleEntity.maxLatitude,
                        scheduleEntity.minLongitude,
                        scheduleEntity.maxLongitude))
                .from(scheduleEntity)
                .fetch();
    }

    @Override
    public void scheduleCroneSave(String scheduleId, String expression) {

    }

    @Override
    public void scheduleSave(String scheduleId, String expression, String min_latitude, String max_latitude, String min_longitude, String max_longitude) {
        ScheduleEntity schedule = queryFactory
                .selectFrom(scheduleEntity)
                .where(scheduleEntity.scheduleDocumentId.eq(scheduleId))
                .fetchOne();

        schedule.setCronPeriod(expression);
        schedule.setMinLatitude(min_latitude);
        schedule.setMaxLatitude(max_latitude);
        schedule.setMinLongitude(min_longitude);
        schedule.setMaxLongitude(max_longitude);
    }
}


