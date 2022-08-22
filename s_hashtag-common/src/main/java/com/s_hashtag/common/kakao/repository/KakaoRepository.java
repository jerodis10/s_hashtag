package com.s_hashtag.common.kakao.repository;


import com.s_hashtag.common.kakao.dto.external.Document;

public interface KakaoRepository {

    void kakao_document_save(Document document);

}
