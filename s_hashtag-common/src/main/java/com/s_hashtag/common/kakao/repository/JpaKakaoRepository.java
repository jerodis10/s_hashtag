package com.s_hashtag.common.kakao.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s_hashtag.common.instagram.model.entity.Instagram;
import com.s_hashtag.common.kakao.dto.external.Document;
import com.s_hashtag.common.kakao.model.entity.KakaoDocument;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.s_hashtag.common.instagram.model.entity.QInstagram.instagram;
import static com.s_hashtag.common.kakao.model.entity.QKakaoDocument.kakaoDocument;

@Primary
@Repository
public class JpaKakaoRepository implements KakaoRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public JpaKakaoRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void kakao_document_save(Document document) {
        KakaoDocument kakaoDocumentEntity = queryFactory
                .selectFrom(kakaoDocument)
                .where(kakaoDocument.kakaoDocumentId.eq(document.getId()))
                .fetchOne();

        Instagram instagramEntity = queryFactory
                .selectFrom(instagram)
                .where(kakaoDocument.instagram.kakaoDocument.eq(kakaoDocumentEntity))
                .fetchOne();

        kakaoDocumentEntity.setInstagram(instagramEntity);
        kakaoDocumentEntity.setCategoryGroupCode(document.getCategoryGroupCode());
        kakaoDocumentEntity.setLatitude(document.getLatitude());
        kakaoDocumentEntity.setLongitude(document.getLongitude());
        kakaoDocumentEntity.setPlaceName(document.getPlaceName());
        kakaoDocumentEntity.setRoadAddressName(document.getRoadAddressName());
        kakaoDocumentEntity.setPlaceUrl(document.getPlaceUrl());
    }
}
