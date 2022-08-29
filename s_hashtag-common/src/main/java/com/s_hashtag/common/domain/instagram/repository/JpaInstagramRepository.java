package com.s_hashtag.common.domain.instagram.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s_hashtag.common.domain.kakao.dto.external.Document;
import com.s_hashtag.common.domain.kakao.dto.external.Rect;
import com.s_hashtag.common.domain.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.domain.instagram.dto.external.PostDto;
import com.s_hashtag.common.domain.instagram.dto.external.PlaceDto;
import com.s_hashtag.common.domain.instagram.model.entity.Instagram;
import com.s_hashtag.common.domain.instagram.model.entity.InstagramPost;
import com.s_hashtag.common.domain.kakao.model.entity.KakaoDocument;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.s_hashtag.common.domain.instagram.model.entity.QInstagram.instagram;
import static com.s_hashtag.common.domain.instagram.model.entity.QInstagramPost.instagramPost;
import static com.s_hashtag.common.domain.kakao.model.entity.QKakaoDocument.kakaoDocument;

@Primary
@Repository
public class JpaInstagramRepository implements InstagramRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public JpaInstagramRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PlaceDto> findAll(String category, Rect rect) {
        return queryFactory
                .select(Projections.fields(PlaceDto.class,
                        instagram.instagramDocumentId,
                        instagram.hashtagCount,
                        instagram.hashtagName,
                        kakaoDocument.kakaoDocumentId.as("id"),
                        kakaoDocument.kakaoDocumentId,
                        kakaoDocument.latitude,
                        kakaoDocument.longitude))
                .from(instagram)
                .join(instagram.kakaoDocument, kakaoDocument)
                .where(latitudeBetween(rect), longitudeBetween(rect),
                        kakaoDocument.categoryGroupCode.eq(category))
                .orderBy(instagram.hashtagCount.desc())
//                .offset(1)
//                .limit(50)
                .fetch();
    }

    @Override
    public List<PlaceDto> findByKeyword(String category, Rect rect, List<String> keywordList) {
        return queryFactory
                .select(Projections.fields(PlaceDto.class,
                        instagram.instagramDocumentId,
                        instagram.hashtagCount,
                        instagram.hashtagName,
                        kakaoDocument.kakaoDocumentId.as("id"),
                        kakaoDocument.kakaoDocumentId,
                        kakaoDocument.latitude,
                        kakaoDocument.longitude))
                .from(instagram)
                .join(instagram.kakaoDocument, kakaoDocument)
                .where(latitudeBetween(rect), longitudeBetween(rect),
                        kakaoDocument.categoryGroupCode.eq(category),
                        kakaoDocument.kakaoDocumentId.in(keywordList))
                .orderBy(instagram.hashtagCount.desc())
//                .offset(1)
//                .limit(50)
                .fetch();
    }

    @Override
    public List<PlaceDto> findByHashtagCount(Rect rect, String[] categoryList, String check) {
        return queryFactory
                .select(Projections.fields(PlaceDto.class,
                        instagram.instagramDocumentId,
                        instagram.hashtagCount,
                        instagram.hashtagName,
                        kakaoDocument.kakaoDocumentId.as("id"),
                        kakaoDocument.kakaoDocumentId,
                        kakaoDocument.latitude,
                        kakaoDocument.longitude))
                .from(instagram)
                .join(instagram.kakaoDocument, kakaoDocument)
                .where(latitudeBetween(rect), longitudeBetween(rect),
                        kakaoDocument.categoryGroupCode.in(categoryList),
                        hashtagCountBetween(check))
                .orderBy(instagram.hashtagCount.desc())
//                .offset(1)
//                .limit(50)
                .fetch();
    }

    @Override
    public void instagramSave(CrawlingDto crawlingDto, Document document) {
        Instagram findInstagram = queryFactory
                .selectFrom(instagram)
                .where(instagram.instagramDocumentId.eq(crawlingDto.getInstagramId()))
                .fetchOne();

        KakaoDocument findKakaoDocument = queryFactory
                .selectFrom(kakaoDocument)
                .where(kakaoDocument.kakaoDocumentId.eq(document.getId()))
                .fetchOne();

        if(findInstagram == null) {
            Instagram instagram = Instagram.builder()
                    .instagramDocumentId(crawlingDto.getInstagramId())
                    .kakaoDocumentId(crawlingDto.getPlaceId())
                    .hashtagCount(crawlingDto.getHashtagCount())
                    .hashtagName(crawlingDto.getHashtagName())
                    .kakaoDocument(findKakaoDocument)
                    .build();

            em.persist(instagram);
        } else {
            findInstagram.changeInstagram(
                    crawlingDto.getInstagramId(),
                    findKakaoDocument,
                    crawlingDto.getHashtagName(),
                    crawlingDto.getHashtagCount()
            );
        }
    }

    @Override
    public void instagramPostSave(PostDto postDto) {
        Instagram findInstagram = queryFactory
                .selectFrom(instagram)
                .where(instagram.instagramDocumentId.eq(postDto.getInstagram_document_id()))
                .fetchOne();

        InstagramPost findInstagramPost = queryFactory
                .selectFrom(instagramPost)
                .where(instagramPost.instagram.instagramDocumentId.eq(postDto.getInstagram_document_id()),
                        instagramPost.instagramPostDocumentId.eq(postDto.getInstagram_post_id()))
                .fetchOne();

        if(findInstagramPost == null) {
            InstagramPost instagramPost = InstagramPost.builder()
                    .instagramPostDocumentId(postDto.getInstagram_post_id())
                    .instagramDocumentId(postDto.getInstagram_document_id())
                    .postUrl(postDto.getPostUrl())
                    .imageUrl(postDto.getImageUrl())
                    .instagram(findInstagram)
                    .build();

            em.persist(instagramPost);
        } else {
            findInstagramPost.changeInstagramPost(findInstagram, postDto.getPostUrl(), postDto.getImageUrl());
        }
    }

    private BooleanExpression latitudeBetween(Rect rect) {
        return rect != null ? instagram.kakaoDocument.latitude.between(new BigDecimal(rect.getMinLatitude().getValue()),
                new BigDecimal(rect.getMaxLatitude().getValue())) : null;
    }

    private BooleanExpression longitudeBetween(Rect rect) {
        return rect != null ? instagram.kakaoDocument.longitude.between(new BigDecimal(rect.getMinLongitude().getValue()),
                new BigDecimal(rect.getMaxLongitude().getValue())) : null;
    }

    private BooleanExpression hashtagCountBetween(String check) {
        switch (check) {
            case "check1"  :
                return check != null ? instagram.hashtagCount.lt(10) : null;
            case "check2"  :
                return check != null ? instagram.hashtagCount.between(10, 99) : null;
            case "check3"  :
                return check != null ? instagram.hashtagCount.between(100, 999) : null;
            case "check4"  :
                return check != null ? instagram.hashtagCount.between(1000, 9999) : null;
            case "check5"  :
                return check != null ? instagram.hashtagCount.goe(10000) : null;

            default:
                return null;
        }
    }
}
