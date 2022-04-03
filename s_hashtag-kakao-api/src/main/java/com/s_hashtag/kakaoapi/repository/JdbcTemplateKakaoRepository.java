package com.s_hashtag.kakaoapi.repository;

import com.s_hashtag.kakaoapi.domain.dto.Document;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateKakaoRepository implements KakaoRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Override
    public List<String> getKakaoIdByKeyword(String category, List<String> keywordList) {
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
}
