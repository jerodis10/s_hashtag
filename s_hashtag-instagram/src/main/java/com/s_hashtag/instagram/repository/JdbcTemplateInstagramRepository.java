package com.s_hashtag.instagram.repository;

import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.dto.PostDto;
import com.s_hashtag.kakaoapi.domain.dto.Document;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        jdbcTemplate.update(sql_kakao_save,
                document.getId(), document.getCategoryGroupCode(),
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
        jdbcTemplate.update(sql_instagram_save,
                crawlingDto.getInstagramId(),
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
        jdbcTemplate.update(sql_instagram_post_save,
                postDto.getInstagram_post_id(), postDto.getInstagram_id(),
                postDto.getInstagram_id(), postDto.getPostUrl(), postDto.getImageUrl(),
                postDto.getInstagram_post_id(), postDto.getInstagram_id(), postDto.getPostUrl(), postDto.getImageUrl());
    }

    @Override
    public List<Map<String, Object>> getHashtag(String category, Rect rect) {
        List<Map<String, Object>> ret = new ArrayList<>();

        String sql_get_hashtag =
                        "select * " +
                        "from kakao_document kd" +
                        "left outer join instagram it" +
                                     "on it place_id = kd.kakao_id" +
                        "where category_group_code = ?" +
                          "and latitude between ? and ?" +
                          "and longitute between ? and ?";
        ret = jdbcTemplate.queryForList(sql_get_hashtag,
                category, rect.getMinLatitude(), rect.getMaxLatitude(), rect.getMinLongitude(), rect.getMaxLongitude());

        return ret;
    }


}
