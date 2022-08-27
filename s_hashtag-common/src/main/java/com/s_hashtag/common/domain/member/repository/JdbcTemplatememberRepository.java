package com.s_hashtag.common.domain.member.repository;

import com.s_hashtag.common.domain.member.dto.presentation.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplatememberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplatememberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public MemberDto save(MemberDto memberDto) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
//        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
//        jdbcInsert.withTableName("member").usingColumns("id", "password", "name");
        jdbcInsert.withTableName("member");
        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("id", member.getId());
        parameters.put("id", memberDto.getLoginId());
        parameters.put("password", memberDto.getPassword());
        parameters.put("name", memberDto.getName());
        parameters.put("role", memberDto.getRole());
        jdbcInsert.execute(parameters);
//        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
//        member.setId(key.longValue());
//        member.setLoginId(key.toString());
//        member.setPassword(key.toString());
//        member.setName(key.toString());
        return memberDto;
    }

    @Override
    public Optional<MemberDto> findById(String id) {
        List<MemberDto> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<MemberDto> findByName(String name) {
        List<MemberDto> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<MemberDto> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    @Override
    public void delete(List<String> idList) {
        for(String id : idList){
            jdbcTemplate.update("delete from member where id = ?", id);
        }
        return;
    }

    private RowMapper<MemberDto> memberRowMapper() {
        return (rs, rowNum) -> {
            MemberDto memberDto = new MemberDto();
//            member.setId(rs.getString("id"));
            memberDto.setLoginId(rs.getString("id"));
            memberDto.setPassword(rs.getString("password"));
            memberDto.setName(rs.getString("name"));
            memberDto.setRole(rs.getString("role"));
            return memberDto;
        };
    }
}
