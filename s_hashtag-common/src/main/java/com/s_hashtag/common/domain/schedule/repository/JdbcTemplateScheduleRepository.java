package com.s_hashtag.common.domain.schedule.repository;

import com.s_hashtag.common.domain.schedule.dto.external.ScheduleDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

//@Primary
@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void scheduleCroneSave(String scheduleId, String expression) {
                String sql_scheduleCroneSave =
                "merge into schedule " +
                "using dual " +
                        "on (schedule_id = ?) " +
                "when matched then " +
                      "update " +
                         "set cron_period = ? " +
               "when not matched then " +
                     "insert (schedule_id, cron_period) " +
                     "values (?, ?)";
        jdbcTemplate.update(sql_scheduleCroneSave,
                scheduleId, expression, scheduleId, expression
        );
    }

    @Override
    public void scheduleSave(String scheduleId, String expression, String min_latitude, String max_latitude, String min_longitude, String max_longitude) {
        String sql_scheduleSave =
                "merge into schedule " +
                        "using dual " +
                        "on (schedule_id = ?) " +
                        "when matched then " +
                        "update " +
                        "set cron_period = ?, " +
                        "    min_latitude = ?, " +
                        "    max_latitude = ?, " +
                        "    min_longitude = ?, " +
                        "    max_longitude = ? ";
        jdbcTemplate.update(sql_scheduleSave,
                scheduleId, expression, min_latitude, max_latitude, min_longitude, max_longitude
        );
    }

    @Override
    public ScheduleDto findById(String id) {
        return jdbcTemplate.queryForObject("select * from schedule where schedule_id = ?", scheduleRowMapper(), id);
    }

    @Override
    public List<ScheduleDto> findAll() {
        return jdbcTemplate.query("select * from schedule", scheduleRowMapper());
    }

    private RowMapper<ScheduleDto> scheduleRowMapper() {
        return (rs, rowNum) -> {
            ScheduleDto scheduleDto = ScheduleDto.builder()
                    .scheduleId(rs.getString("schedule_id"))
                    .scheduleName(rs.getString("schedule_name"))
                    .cronPeriod(rs.getString("cron_period"))
                    .runtime(rs.getString("runtime"))
                    .scheduleResult(rs.getString("schedule_result"))
                    .jobName(rs.getString("job_name"))
                    .jobResult(rs.getString("job_result"))
                    .minLatitude(rs.getString("min_latitude"))
                    .maxLatitude(rs.getString("max_latitude"))
                    .minLongitude(rs.getString("min_longitude"))
                    .maxLongitude(rs.getString("max_longitude"))
                    .build();
            return scheduleDto;
        };
    }

}
