package com.s_hashtag.instagram.repository;

import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.dto.PostDto;
import com.s_hashtag.kakaoapi.domain.dto.Document;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import org.jsoup.internal.StringUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.Member;
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
    public List<Map<String, Object>> getHashtag(String category_list, Rect rect) {
        List<Map<String, Object>> ret = new ArrayList<>();

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

        return jdbcTemplate.queryForList(sql_get_hashtag, param_list.toArray());

//        return jdbcTemplate.queryForList(sql_get_hashtag, new Object[]{rect.getMinLatitude().getValue(), rect.getMaxLatitude().getValue(), rect.getMinLongitude().getValue(), rect.getMaxLongitude().getValue(), "FD6"});

//        return jdbcTemplate.queryForList(sql_get_hashtag,
//        rect.getMinLatitude().getValue(), rect.getMaxLatitude().getValue(), rect.getMinLongitude().getValue(), rect.getMaxLongitude().getValue(), "FD6");

//        String sql_category = "";
//        if(category_list.size() > 1) sql_category = StringUtils.join(category_list, ",");
//        else sql_category = category_list.get(0);
//        sql_category += " )";
//        sql_get_hashtag += sql_category;


//        return jdbcTemplate.queryForList(sql_get_hashtag,
//                rect.getMinLatitude().getValue(), rect.getMaxLatitude().getValue(), rect.getMinLongitude().getValue(), rect.getMaxLongitude().getValue());

//        ret = jdbcTemplate.queryForList(sql_get_hashtag,
//                category, rect.getMinLatitude(), rect.getMaxLatitude(), rect.getMinLongitude(), rect.getMaxLongitude());

//        Map<String, Object> parameter = new HashMap<>();
//        parameter.put("category", category);
//        parameter.put("min_latitude", rect.getMinLatitude());
//        parameter.put("max_latitude", rect.getMaxLatitude());
//        parameter.put("min_longitude", rect.getMinLongitude());
//        parameter.put("max_longitude", rect.getMaxLongitude());
//
//        ret = jdbcTemplate.queryForList(sql_get_hashtag, parameter);

//        ret = jdbcTemplate.query(sql_get_hashtag, RowMapper(rect),
//                category, rect.getMinLatitude(), rect.getMaxLatitude(), rect.getMinLongitude(), rect.getMaxLongitude());

//        return ret;
    }

    private RowMapper<Map<String, Object>> RowMapper(Rect rect) {
        return (rs, rowNum) -> {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("min_latitude", rect.getMinLatitude());
            parameter.put("max_latitude", rect.getMaxLatitude());
            parameter.put("min_longitude", rect.getMinLongitude());
            parameter.put("max_longitude", rect.getMaxLongitude());
            return parameter;
        };
    }

    @Override
    public List<Map<String, Object>> findAllMember() {
//        return jdbcTemplate.query("select * from member where id in (?,?,?)", memberRowMapper(), '1', '2', '3');

        return jdbcTemplate.queryForList("select * from member where id in (?,?,?)",
                '1','2','3');
    }

    private RowMapper<Map<String, Object>> memberRowMapper() {
        return (rs, rowNum) -> {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("a", '1');
            parameter.put("b", '2');
            parameter.put("c", '3');
            return parameter;
        };
    }

}
