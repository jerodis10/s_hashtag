package com.s_hashtag.common.domain.kakao.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s_hashtag.common.domain.kakao.dto.external.Document;
import com.s_hashtag.common.domain.kakao.model.entity.KakaoDocument;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.s_hashtag.common.domain.kakao.model.entity.QKakaoDocument.kakaoDocument;

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

        if(findKakaoDocument == null) {
            KakaoDocument kakaoDocument = KakaoDocument.builder()
                    .kakaoDocumentId(document.getId())
                    .latitude(document.getLatitude())
                    .longitude(document.getLongitude())
                    .placeName(document.getPlaceName())
                    .placeUrl(document.getPlaceUrl())
                    .categoryGroupCode(document.getCategoryGroupCode())
                    .roadAddressName(document.getRoadAddressName())
                    .build();

            em.persist(kakaoDocument);
        } else {
            findKakaoDocument.changeKakaoDocument(
                    document.getCategoryGroupCode(),
                    document.getLatitude(),
                    document.getLongitude(),
                    document.getPlaceName(),
                    document.getRoadAddressName(),
                    document.getPlaceUrl()
            );
        }
    }
}
