package com.s_hashtag.instagram.repository;

import com.s_hashtag.kakaoapi.domain.dto.Document;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTemplateInstagramRepository implements InstagramRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

//    @Autowired
    public JdbcTemplateInstagramRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Document kakao_document_save(Document document) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("KAKAO_DOCUMENT");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ID", document.getId());
        parameters.put("CATEGORY_GROUP_CODE", document.getCategoryGroupCode());
        parameters.put("LATITUDE", document.getLatitude());
        parameters.put("LONGTITUDE", document.getLongitude());
        parameters.put("PLACE_NAME", document.getPlaceName());
        parameters.put("ROAD_ADDRESS_NAME", document.getRoadAddressName());
        parameters.put("PLACE_URL", document.getPlaceUrl());
        jdbcInsert.execute(parameters);
        return document;
    }

//    @Override
//    public Optional<Member> findById(String id) {
//        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
//        return result.stream().findAny();
//    }
//
//    @Override
//    public Optional<Member> findByName(String name) {
//        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
//        return result.stream().findAny();
//    }
//
//    @Override
//    public List<Member> findAll() {
//        return jdbcTemplate.query("select * from member", memberRowMapper());
//    }
//
//    private RowMapper<Member> memberRowMapper() {
//        return (rs, rowNum) -> {
//            Member member = new Member();
////            member.setId(rs.getString("id"));
//            member.setLoginId(rs.getString("loginId"));
//            member.setPassword(rs.getString("password"));
//            member.setName(rs.getString("name"));
//            return member;
//        };
//    }
}
