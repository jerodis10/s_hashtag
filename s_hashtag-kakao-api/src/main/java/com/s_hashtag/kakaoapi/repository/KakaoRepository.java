package com.s_hashtag.kakaoapi.repository;


import com.s_hashtag.kakaoapi.domain.dto.Document;

//@Repository
public interface KakaoRepository {

    void kakao_document_save(Document document);

}
