package com.s_hashtag.admin.schedule.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcTemplateScheduleRepository implements KakaoScheduleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void scheduleCroneSave(String scheduleName, String crone) {
                String sql_kakao_save =
                "merge into schedule " +
                "using dual " +
                        "on (schedule_name = ?) " +
                "when matched then " +
                      "update " +
                         "set crone = ? " +
               "when not matched then " +
                     "insert (schedule_name, crone) " +
                     "values (?, ?)";
        jdbcTemplate.update(sql_kakao_save,
                scheduleName, crone, scheduleName, crone
        );
    }

}
