package com.s_hashtag.instagram.repository;

import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.dto.PlaceDto;
import com.s_hashtag.instagram.dto.PostDto;
import com.s_hashtag.kakaoapi.domain.dto.Document;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

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
    public List<PlaceDto> getHashtag(String category_list, Rect rect) {
        List<PlaceDto> ret = new ArrayList<>();

//        String sql_get_hashtag = "select * " +
//                                 "from kakao_document kd " +
//                                 "left outer join instagram it " +
//                                 "on it.place_id = kd.kakao_id " +
//                                 "where latitude between"

//        String sql_get_hashtag =
//                        "select * " +
//                        "from kakao_document kd " +
//                        "left outer join instagram it " +
//                                     "on it.place_id = kd.kakao_id " +
//                        "where latitude between ? and ? " +
//                          "and longitude between ? and ? "
//                ;
//        String inSql = String.join(",", Collections.nCopies(category_list.size(), "?"));

        List<Object> param_list = new ArrayList<>();
        param_list.add(rect.getMinLatitude().getValue());
        param_list.add(rect.getMaxLatitude().getValue());
        param_list.add(rect.getMinLongitude().getValue());
        param_list.add(rect.getMaxLongitude().getValue());

        String inSql = "";
//        for(String str : category_list) {
//            inSql += "and category_group_code = ? ";
//            param_list.add(str);
//        }
        inSql += "and category_group_code = ? ";
        param_list.add(category_list);

        String sql_get_hashtag =
                "select * " +
                "from kakao_document kd " +
                "left outer join instagram it " +
                "on it.place_id = kd.kakao_id " +
                "where latitude between ? and ? " +
                "and longitude between ? and ? ";
        ;

        sql_get_hashtag += inSql;

        return jdbcTemplate.query(sql_get_hashtag, PlaceRowMapper(rect), param_list.toArray());

    }

    @Override
    public List<PlaceDto> getHashtagByKeyword(String category_list, List<String> keywordList) {
        String sql_get_hashtag_byKeyword =
                "select * " +
                "from instagram it " +
                "where 1 = 1";

        for(String keyword : keywordList){
            sql_get_hashtag_byKeyword += "and place_id = ? ";
        }

        sql_get_hashtag_byKeyword += "and category_group_code = ? ";

        List<Object> param = new ArrayList<>();
        param.add(category_list);
        param.add(keywordList);

        return jdbcTemplate.query(sql_get_hashtag_byKeyword, PlaceByKeywordRowMapper(), param);
    }


    private RowMapper<PlaceDto> PlaceRowMapper(Rect rect) {
        return (rs, rowNum) -> {
            PlaceDto placeDto = new PlaceDto();
            placeDto.setMinLatitude(rect.getMinLatitude().getValue());
            placeDto.setMaxLatitude(rect.getMaxLatitude().getValue());
            placeDto.setMinLongitude(rect.getMinLongitude().getValue());
            placeDto.setMaxLongitude(rect.getMaxLongitude().getValue());
            placeDto.setHashtagName(rs.getString("HASHTAG_NAME"));
            placeDto.setHashtagCount(rs.getLong("HASHTAG_COUNT"));
            placeDto.setLatitude(rs.getDouble("LATITUDE"));
            placeDto.setLongitude(rs.getDouble("LONGITUDE"));

            return placeDto;
        };
    }

    private RowMapper<PlaceDto> PlaceByKeywordRowMapper() {
        return (rs, rowNum) -> {
            PlaceDto placeDto = new PlaceDto();
            placeDto.setHashtagName(rs.getString("HASHTAG_NAME"));
            placeDto.setHashtagCount(rs.getLong("HASHTAG_COUNT"));
            placeDto.setLatitude(rs.getDouble("LATITUDE"));
            placeDto.setLongitude(rs.getDouble("LONGITUDE"));

            return placeDto;
        };
    }

}
