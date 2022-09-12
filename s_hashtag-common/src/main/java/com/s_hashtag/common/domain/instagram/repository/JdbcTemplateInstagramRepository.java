package com.s_hashtag.common.domain.instagram.repository;

import com.s_hashtag.common.domain.kakao.dto.external.Document;
import com.s_hashtag.common.domain.kakao.dto.external.Rect;
import com.s_hashtag.common.domain.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.domain.instagram.dto.external.PlaceDto;
import com.s_hashtag.common.domain.instagram.dto.external.PostDto;
import lombok.extern.slf4j.Slf4j;
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
                        "on (instagram_document_id = ?) " +
                        "when matched then " +
                        "update " +
                        "set " +
                            "kakao_document_id = ?, " +
                            "hashtag_name = ?, " +
                            "hashtag_count = ? " +
                        "when not matched then " +
                        "insert (" +
                            "instagram_document_id, kakao_document_id, hashtag_name, hashtag_count) " +
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
                            "instagram_post_id = ? and instagram_document_id = ?) " +
                        "when matched then " +
                        "update " +
                        "set " +
                            "instagram_document_id = ?, " +
                            "post_url = ?, " +
                            "image_url = ? " +
                        "when not matched then " +
                        "insert (" +
                            "instagram_post_id, instagram_document_id, post_url, image_url) " +
                        "values (" +
                            "?, ?, ?, ?)";
        jdbcTemplate.update(sql_instagram_post_save,
                postDto.getInstagramPostId(), postDto.getInstagramDocumentId(),
                postDto.getInstagramDocumentId(), postDto.getPostUrl(), postDto.getImageUrl(),
                postDto.getInstagramPostId(), postDto.getInstagramDocumentId(), postDto.getPostUrl(), postDto.getImageUrl());
    }

    @Override
    public List<PlaceDto> findAll(List<String> categoryList, Rect rect) {
        List<Object> param_list = new ArrayList<>();
        param_list.add(rect.getMinLatitude().getValue());
        param_list.add(rect.getMaxLatitude().getValue());
        param_list.add(rect.getMinLongitude().getValue());
        param_list.add(rect.getMaxLongitude().getValue());

        String inSql = "";
        inSql += "and category_group_code = ? ";
        param_list.add(categoryList);

        String sql_get_hashtag =
                "select * " +
                "from ( " +
                    "select  " +
                    "kd.kakao_document_id, kd.category_group_code, kd.latitude, kd.longitude, kd.place_name, it.instagram_document_id, it.hashtag_name, it.hashtag_count " +
                    "from kakao_document kd " +
                    "inner join instagram it " +
                    "on it.kakao_document_id = kd.kakao_document_id " +
                    "where kd.latitude between ? and ? " +
                    "and kd.longitude between ? and ? " +
                    "and it.hashtag_count > 0 " +
                    "order by hashtag_count desc " +
                ")" +
                "where rownum <= 50 ";

        sql_get_hashtag += inSql;

        return jdbcTemplate.query(sql_get_hashtag, PlaceRowMapper(rect), param_list.toArray());
    }

    @Override
    public List<PlaceDto> findByKeyword(String category_list, Rect rect, List<String> keywordList) {
        String sql_get_hashtag_byKeyword =
                "select * " +
                "from instagram it " +
                "left outer join kakao_document kd " +
                "on it.kakao_document_id = kd.kakao_document_id " +
                "where 1 = 1 ";

        sql_get_hashtag_byKeyword += "and category_group_code = ? ";
        sql_get_hashtag_byKeyword += "and kakao_document_id in (";
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
    public List<PlaceDto> findByHashtagCount(Rect rect, List<String> categoryList, String check) {
        List<Object> param = new ArrayList<>();
        String sql_get_hashtag_byCount =
                "select * " +
                "from instagram it " +
                "left outer join kakao_document kd " +
                "on it.kakao_document_id = kd.kakao_document_id " +
                "where 1 = 1 "+
                "and latitude between ? and ? " +
                "and longitude between ? and ? ";

        param.add(rect.getMinLatitude().getValue());
        param.add(rect.getMaxLatitude().getValue());
        param.add(rect.getMinLongitude().getValue());
        param.add(rect.getMaxLongitude().getValue());

        sql_get_hashtag_byCount += "and category_group_code in ( ";
        for (int i=0; i<categoryList.size(); i++) {
            if(i != 0) sql_get_hashtag_byCount += ",?";
            else sql_get_hashtag_byCount += "?";
            param.add(categoryList.get(i));
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

    @Override
    public List<PostDto> findByHashtagName(List<String> categoryList, Rect rect, String hashtagName) {
        return null;
    }

    private RowMapper<PlaceDto> PlaceRowMapper(Rect rect) {
        return (rs, rowNum) -> {
            PlaceDto placeDto = new PlaceDto();
            placeDto.setMinLatitude(rect.getMinLatitude().getValue());
            placeDto.setMaxLatitude(rect.getMaxLatitude().getValue());
            placeDto.setMinLongitude(rect.getMinLongitude().getValue());
            placeDto.setMaxLongitude(rect.getMaxLongitude().getValue());
            placeDto.setHashtagName(rs.getString("HASHTAG_NAME"));
            placeDto.setHashtagCount(rs.getBigDecimal("HASHTAG_COUNT"));
            placeDto.setLatitude(rs.getBigDecimal("LATITUDE"));
            placeDto.setLongitude(rs.getBigDecimal("LONGITUDE"));
            placeDto.setKakaoDocumentId(rs.getString("KAKAO_DOCUMENT_ID"));
            placeDto.setCategoryGroupCode(rs.getString("CATEGORY_GROUP_CODE"));

            return placeDto;
        };
    }

    private RowMapper<PlaceDto> PlaceByKeywordRowMapper() {
        return (rs, rowNum) -> {
            PlaceDto placeDto = new PlaceDto();
            placeDto.setHashtagName(rs.getString("HASHTAG_NAME"));
            placeDto.setHashtagCount(rs.getBigDecimal("HASHTAG_COUNT"));
            placeDto.setLatitude(rs.getBigDecimal("LATITUDE"));
            placeDto.setLongitude(rs.getBigDecimal("LONGITUDE"));
            placeDto.setCategoryGroupCode(rs.getString("CATEGORY_GROUP_CODE"));
            placeDto.setKakaoDocumentId(rs.getString("KAKAO_DOCUMENT_ID"));

            return placeDto;
        };
    }
}
