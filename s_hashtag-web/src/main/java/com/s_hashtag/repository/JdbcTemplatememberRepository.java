package com.s_hashtag.repository;

import com.s_hashtag.domain.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplatememberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplatememberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
//        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
//        jdbcInsert.withTableName("member").usingColumns("id", "password", "name");
        jdbcInsert.withTableName("member");
        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("id", member.getId());
        parameters.put("id", member.getLoginId());
        parameters.put("password", member.getPassword());
        parameters.put("name", member.getName());
        parameters.put("role", member.getRole());
        jdbcInsert.execute(parameters);
//        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
//        member.setId(key.longValue());
//        member.setLoginId(key.toString());
//        member.setPassword(key.toString());
//        member.setName(key.toString());
        return member;
    }

    @Override
    public Optional<Member> findById(String id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
//            member.setId(rs.getString("id"));
            member.setLoginId(rs.getString("loginId"));
            member.setPassword(rs.getString("password"));
            member.setName(rs.getString("name"));
            member.setRole(rs.getString("role"));
            return member;
        };
    }
}
