package com.s_hashtag.kakaoapi.repository;

import com.s_hashtag.kakaoapi.dto.external.Document;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcTemplateKakaoRepository implements KakaoRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

//    @Autowired
    public JdbcTemplateKakaoRepository(DataSource dataSource) {
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

}
