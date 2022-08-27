package com.s_hashtag.common.domain.kakao.repository;


import com.s_hashtag.common.domain.kakao.dto.external.Document;

public interface KakaoRepository {

    void kakao_document_save(Document document);

}
