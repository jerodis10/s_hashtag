package com.s_hashtag.kakaoapi.repository;

import java.util.List;

public interface KakaoRepository {

    List<String> getKakaoIdByKeyword(String category, List<String> keywordList);
}
