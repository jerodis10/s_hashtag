package com.s_hashtag.instagram.repository;

import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.dto.PostDto;
import com.s_hashtag.kakaoapi.domain.dto.Document;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.reflect.Member;
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
    public void kakao_document_save(Document document) {
        String sql_kakao_save =
                "merge into kakao_document " +
                "using dual " +
                        "on (kakao_id = ? and category_group_code = ?) " +
                "when matched then " +
                      "update " +
                         "set latitude = ?, " +
                             "longitude = ?, " +
                             "place_name = ?, "  +
                             "road_address_name = ?, " +
                             "place_url = ? " +
               "when not matched then " +
                     "insert (kakao_id, category_group_code, latitude, longitude, place_name, road_address_name, place_url) " +
                     "values (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql_kakao_save, document.getId(), document.getCategoryGroupCode(),
                document.getLatitude(), document.getLongitude(), document.getPlaceName(), document.getRoadAddressName(), document.getPlaceUrl(),
                document.getId(), document.getCategoryGroupCode(), document.getLatitude(), document.getLongitude(), document.getPlaceName(), document.getRoadAddressName(), document.getPlaceUrl());
    }

    @Override
    public void instagram_save(CrawlingDto crawlingDto, Document document) {
        String sql_instagram_save =
                "merge into instagram " +
                        "using dual " +
                        "on (instagram_id = ?) " +
                        "when matched then " +
                        "update " +
                        "set " +
                            "place_id = ?, " +
                            "hashtag_name = ?, " +
                            "hashtag_count = ? " +
                        "when not matched then " +
                        "insert (" +
                            "instagram_id, place_id, hashtag_name, hashtag_count) " +
                        "values (?, ?, ?, ?)";
        jdbcTemplate.update(sql_instagram_save, crawlingDto.getInstagramId(),
                document.getId(), crawlingDto.getHashtagName(), crawlingDto.getHashtagCount(),
                crawlingDto.getInstagramId(), document.getId(), crawlingDto.getHashtagName(), crawlingDto.getHashtagCount());
    }

    @Override
    public void instagram_post_save(PostDto postDto) {
        String sql_instagram_post_save =
                "merge into instagram_post " +
                        "using dual " +
                        "on (" +
                            "instagram_post_id = ? and instagram_id = ?) " +
                        "when matched then " +
                        "update " +
                        "set " +
                            "instagram_id = ?, " +
                            "post_url = ?, " +
                            "image_url = ? " +
                        "when not matched then " +
                        "insert (" +
                            "instagram_post_id, instagram_id, post_url, image_url) " +
                        "values (" +
                            "?, ?, ?, ?)";
        jdbcTemplate.update(sql_instagram_post_save, postDto.getInstagram_post_id(), postDto.getInstagram_id(),
                postDto.getInstagram_id(), postDto.getPostUrl(), postDto.getImageUrl(),
                postDto.getInstagram_post_id(), postDto.getInstagram_id(), postDto.getPostUrl(), postDto.getImageUrl());
    }

//    private RowMapper<Document> documentRowMapper() {
//        return (rs, rowNum) -> {
//            Document document = new Document();
//            document.setId(rs.getString("kakao_id"));
//            document.setCategoryGroupCode(rs.getString("category_group_code"));
//            document.setLatitude(rs.getString("latitude"));
//            document.setLongitude(rs.getString("longitude"));
//            document.setPlaceName(rs.getString("place_name"));
//            document.setRoadAddressName(rs.getString("road_address_name"));
//            document.setPlaceUrl(rs.getString("place_url"));
//            return document;
//        };
//    }

    //    @Override
//    public Document kakao_document_save(Document document) {
//        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
//        jdbcInsert.withTableName("KAKAO_DOCUMENT");
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("ID", document.getId());
//        parameters.put("CATEGORY_GROUP_CODE", document.getCategoryGroupCode());
//        parameters.put("LATITUDE", document.getLatitude());
//        parameters.put("LONGTITUDE", document.getLongitude());
//        parameters.put("PLACE_NAME", document.getPlaceName());
//        parameters.put("ROAD_ADDRESS_NAME", document.getRoadAddressName());
//        parameters.put("PLACE_URL", document.getPlaceUrl());
//        jdbcInsert.execute(parameters);
//        return document;
//    }

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
