package com.s_hashtag.common.instagram.repository;

import com.s_hashtag.common.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.instagram.dto.external.PlaceDto;
import com.s_hashtag.common.instagram.dto.external.PostDto;
import com.s_hashtag.common.kakao.dto.external.Document;
import com.s_hashtag.common.kakao.dto.external.Rect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

//@Primary
@Repository
@Slf4j
public class JdbcTemplateInstagramRepository implements InstagramRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

//    @Autowired
    public JdbcTemplateInstagramRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void instagramSave(CrawlingDto crawlingDto, Document document) {
        log.info("insert crawling HashtagName = {}", crawlingDto.getHashtagName());
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
    public void instagramPostSave(PostDto postDto) {
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
    public List<PlaceDto> findAll(String category_list, Rect rect) {
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
                "from ( " +
                    "select  " +
                    "kd.kakao_id, kd.category_group_code, kd.latitude, kd.longitude, kd.place_name, it.instagram_id, it.hashtag_name, it.hashtag_count " +
                    "from kakao_document kd " +
                    "inner join instagram it " +
                    "on it.place_id = kd.kakao_id " +
                    "where kd.latitude between ? and ? " +
                    "and kd.longitude between ? and ? " +
                    "and it.hashtag_count > 0 " +
                    "order by hashtag_count desc " +
                ")" +
                "where rownum <= 50 ";
//                "where hashtag_name='서대문집'";
        ;

        sql_get_hashtag += inSql;

        return jdbcTemplate.query(sql_get_hashtag, PlaceRowMapper(rect), param_list.toArray());

    }

    @Override
    public List<PlaceDto> findByKeyword(String category_list, List<String> keywordList) {
        String sql_get_hashtag_byKeyword =
                "select * " +
                "from instagram it " +
                "left outer join kakao_document kd " +
                "on it.place_id = kd.kakao_id " +
                "where 1 = 1 ";

        sql_get_hashtag_byKeyword += "and category_group_code = ? ";
        sql_get_hashtag_byKeyword += "and place_id in (";
        for (int i=0; i<keywordList.size(); i++) {
            if(i != 0) sql_get_hashtag_byKeyword += ",?";
            else sql_get_hashtag_byKeyword += "?";
        }
        sql_get_hashtag_byKeyword += ")";


        List<Object> param = new ArrayList<>();
        param.add(category_list);

        for(String keyword : keywordList){
            param.add(keyword);
        }

        return jdbcTemplate.query(sql_get_hashtag_byKeyword, PlaceByKeywordRowMapper(), param.toArray());
    }

    @Override
    public List<PlaceDto> findByHashtagCount(Rect rect, String[] categoryList, String check) {
        List<Object> param = new ArrayList<>();
        String sql_get_hashtag_byCount =
                "select * " +
                "from instagram it " +
                "left outer join kakao_document kd " +
                "on it.place_id = kd.kakao_id " +
                "where 1 = 1 "+
                "and latitude between ? and ? " +
                "and longitude between ? and ? ";

        param.add(rect.getMinLatitude().getValue());
        param.add(rect.getMaxLatitude().getValue());
        param.add(rect.getMinLongitude().getValue());
        param.add(rect.getMaxLongitude().getValue());

        sql_get_hashtag_byCount += "and category_group_code in ( ";
        for (int i=0; i<categoryList.length; i++) {
            if(i != 0) sql_get_hashtag_byCount += ",?";
            else sql_get_hashtag_byCount += "?";
            param.add(categoryList[i]);
        }
        sql_get_hashtag_byCount += ") ";

        if(check.equals("check1")) {
            sql_get_hashtag_byCount += "and hashtag_count < 10  ";
        }
        if(check.equals("check2")) {
            sql_get_hashtag_byCount += "and (hashtag_count >= 10 and hashtag_count < 100) ";
        }
        if(check.equals("check3")) {
            sql_get_hashtag_byCount += "and (hashtag_count >= 100 and hashtag_count < 1000) ";
        }
        if(check.equals("check4")) {
            sql_get_hashtag_byCount += "and (hashtag_count >= 1000 and hashtag_count < 10000) ";
        }
        if(check.equals("check5")) {
            sql_get_hashtag_byCount += "and (hashtag_count >= 10000) ";
        }

        return jdbcTemplate.query(sql_get_hashtag_byCount, PlaceRowMapper(rect), param.toArray());
    }

//    @Override
//    public List<PlaceDto> getHashtagByCount2(String[] categoryList, Map<String, Object> hashtag_count_param) {
//        List<Object> param = new ArrayList<>();
//        String sql_get_hashtag_byCount =
//                "select * " +
//                "from instagram it " +
//                "left outer join kakao_document kd " +
//                "on it.place_id = kd.kakao_id " +
//                "where 1 = 1 ";
//
//        sql_get_hashtag_byCount += "and category_group_code in ( ";
//        for (int i=0; i<categoryList.length; i++) {
//            if(i != 0) sql_get_hashtag_byCount += ",?";
//            else sql_get_hashtag_byCount += "?";
//            param.add(categoryList[i]);
//        }
//        sql_get_hashtag_byCount += ") ";
//
//        int cnt = 0;
//        if(hashtag_count_param.get("check1").equals("true")) cnt++;
//        if(hashtag_count_param.get("check2").equals("true")) cnt++;
//        if(hashtag_count_param.get("check3").equals("true")) cnt++;
//        if(hashtag_count_param.get("check4").equals("true")) cnt++;
//        if(hashtag_count_param.get("check5").equals("true")) cnt++;
//        if(cnt > 0) sql_get_hashtag_byCount += "and (";
//
//        boolean flag = true;
//
//        if(hashtag_count_param.get("check1").equals("true")) {
//            sql_get_hashtag_byCount += "(hashtag_count <= 10) ";
//            flag = false;
//        }
//        if(!flag && hashtag_count_param.get("check2").equals("true")) {
//            sql_get_hashtag_byCount += "or (hashtag_count > 10 and hashtag_count <= 100) ";
//        }
//        if(flag && hashtag_count_param.get("check2").equals("true")) {
//            sql_get_hashtag_byCount += "(hashtag_count > 10 and hashtag_count <= 100) ";
//            flag = false;
//        }
//        if(!flag && hashtag_count_param.get("check3").equals("true")) {
//            sql_get_hashtag_byCount += "or (hashtag_count > 100 and hashtag_count <= 1000) ";
//        }
//        if(flag && hashtag_count_param.get("check3").equals("true")) {
//            sql_get_hashtag_byCount += "(hashtag_count > 100 and hashtag_count <= 1000) ";
//            flag = false;
//        }
//        if(!flag && hashtag_count_param.get("check4").equals("true")) {
//            sql_get_hashtag_byCount += "or (hashtag_count > 1000 and hashtag_count <= 10000) ";
//        }
//        if(flag && hashtag_count_param.get("check4").equals("true")) {
//            sql_get_hashtag_byCount += "(hashtag_count > 1000 and hashtag_count <= 10000) ";
//            flag = false;
//        }
//        if(!flag && hashtag_count_param.get("check5").equals("true")) {
//            sql_get_hashtag_byCount += "or (hashtag_count > 10000 and hashtag_count <= 100000) ";
//        }
//        if(flag && hashtag_count_param.get("check5").equals("true")) {
//            sql_get_hashtag_byCount += "(hashtag_count > 10000 and hashtag_count <= 100000) ";
//        }
//
//        if(cnt > 0) sql_get_hashtag_byCount += ") ";
//
////        param.add(categoryList);
//
//        return jdbcTemplate.query(sql_get_hashtag_byCount, PlaceByKeywordRowMapper(), param.toArray());
//    }

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
            placeDto.setKakaoId(rs.getString("KAKAO_ID"));
            placeDto.setCategoryGroupCode(rs.getString("CATEGORY_GROUP_CODE"));

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
            placeDto.setCategoryGroupCode(rs.getString("CATEGORY_GROUP_CODE"));
            placeDto.setPlaceId(rs.getString("PLACE_ID"));

            return placeDto;
        };
    }

}
