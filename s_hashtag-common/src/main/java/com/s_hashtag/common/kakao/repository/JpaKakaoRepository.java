package com.s_hashtag.common.kakao.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s_hashtag.common.instagram.model.entity.InstagramEntity;
import com.s_hashtag.common.kakao.dto.external.Document;
import com.s_hashtag.common.kakao.model.entity.KakaoDocumentEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.s_hashtag.common.instagram.model.entity.QInstagramEntity.instagramEntity;
import static com.s_hashtag.common.kakao.model.entity.QKakaoDocumentEntity.kakaoDocumentEntity;

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
        KakaoDocumentEntity kakaoDocument = queryFactory
                .selectFrom(kakaoDocumentEntity)
                .where(kakaoDocumentEntity.kakaoDocumentId.eq(document.getId()))
                .fetchOne();

        InstagramEntity instagram = queryFactory
                .selectFrom(instagramEntity)
                .where(kakaoDocumentEntity.instagramEntity.kakaoDocumentEntity.eq(kakaoDocument))
                .fetchOne();

        kakaoDocument.setInstagramEntity(instagram);
        kakaoDocument.setCategoryGroupCode(document.getCategoryGroupCode());
        kakaoDocument.setLatitude(document.getLatitude());
        kakaoDocument.setLongitude(document.getLongitude());
        kakaoDocument.setPlaceName(document.getPlaceName());
        kakaoDocument.setRoadAddressName(document.getRoadAddressName());
        kakaoDocument.setPlaceUrl(document.getPlaceUrl());
    }
}
