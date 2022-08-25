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
        KakaoDocument findKakaoDocument = queryFactory
                .selectFrom(kakaoDocument)
                .where(kakaoDocument.kakaoDocumentId.eq(document.getId()))
                .fetchOne();

        Instagram findInstagram = queryFactory
                .selectFrom(instagram)
                .where(kakaoDocument.instagram.kakaoDocument.eq(findKakaoDocument))
                .fetchOne();

        if(findKakaoDocument.getId() == null) {
            em.persist(findKakaoDocument);
        } else {
            findKakaoDocument.setInstagram(findInstagram);
            findKakaoDocument.setCategoryGroupCode(document.getCategoryGroupCode());
            findKakaoDocument.setLatitude(document.getLatitude());
            findKakaoDocument.setLongitude(document.getLongitude());
            findKakaoDocument.setPlaceName(document.getPlaceName());
            findKakaoDocument.setRoadAddressName(document.getRoadAddressName());
            findKakaoDocument.setPlaceUrl(document.getPlaceUrl());
        }
    }
}
