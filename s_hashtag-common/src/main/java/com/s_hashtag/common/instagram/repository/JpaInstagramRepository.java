package com.s_hashtag.common.instagram.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s_hashtag.common.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.instagram.dto.external.PostDto;
import com.s_hashtag.common.instagram.dto.external.PlaceDto;
import com.s_hashtag.common.instagram.model.entity.InstagramEntity;
import com.s_hashtag.common.kakao.dto.external.Document;
import com.s_hashtag.common.kakao.dto.external.Rect;
import com.s_hashtag.common.kakao.model.entity.KakaoDocumentEntity;
import com.s_hashtag.common.schedule.model.entity.ScheduleEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.s_hashtag.common.instagram.model.entity.QInstagramEntity.instagramEntity;
import static com.s_hashtag.common.kakao.model.entity.QKakaoDocumentEntity.kakaoDocumentEntity;
import static com.s_hashtag.common.schedule.model.entity.QScheduleEntity.scheduleEntity;

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
                        instagramEntity.instagramDocumentId,
                        instagramEntity.hashtagCount,
                        instagramEntity.hashtagName,
                        kakaoDocumentEntity.kakaoDocumentId.as("id"),
                        kakaoDocumentEntity.kakaoDocumentId,
                        kakaoDocumentEntity.latitude,
                        kakaoDocumentEntity.longitude))
                .from(instagramEntity)
                .join(instagramEntity.kakaoDocumentEntity, kakaoDocumentEntity)
                .where(latitudeBetween(rect), longitudeBetween(rect),
                        kakaoDocumentEntity.categoryGroupCode.eq(category))
                .orderBy(instagramEntity.hashtagCount.desc())
//                .offset(1)
//                .limit(50)
                .fetch();
    }

    @Override
    public List<PlaceDto> findByKeyword(String category, Rect rect, List<String> keywordList) {
        return queryFactory
                .select(Projections.fields(PlaceDto.class,
                        instagramEntity.instagramDocumentId,
                        instagramEntity.hashtagCount,
                        instagramEntity.hashtagName,
                        kakaoDocumentEntity.kakaoDocumentId.as("id"),
                        kakaoDocumentEntity.kakaoDocumentId,
                        kakaoDocumentEntity.latitude,
                        kakaoDocumentEntity.longitude))
                .from(instagramEntity)
                .join(instagramEntity.kakaoDocumentEntity, kakaoDocumentEntity)
                .where(latitudeBetween(rect), longitudeBetween(rect),
                        kakaoDocumentEntity.categoryGroupCode.eq(category),
                        kakaoDocumentEntity.kakaoDocumentId.in(keywordList))
                .orderBy(instagramEntity.hashtagCount.desc())
//                .offset(1)
//                .limit(50)
                .fetch();
    }

    @Override
    public List<PlaceDto> findByHashtagCount(Rect rect, String[] categoryList, String check) {
        return queryFactory
                .select(Projections.fields(PlaceDto.class,
                        instagramEntity.instagramDocumentId,
                        instagramEntity.hashtagCount,
                        instagramEntity.hashtagName,
                        kakaoDocumentEntity.kakaoDocumentId.as("id"),
                        kakaoDocumentEntity.kakaoDocumentId,
                        kakaoDocumentEntity.latitude,
                        kakaoDocumentEntity.longitude))
                .from(instagramEntity)
                .join(instagramEntity.kakaoDocumentEntity, kakaoDocumentEntity)
                .where(latitudeBetween(rect), longitudeBetween(rect),
                        kakaoDocumentEntity.categoryGroupCode.in(categoryList),
                        hashtagCountBetween(check))
                .orderBy(instagramEntity.hashtagCount.desc())
//                .offset(1)
//                .limit(50)
                .fetch();
    }

    @Override
    public void instagramSave(CrawlingDto crawlingDto, Document document) {
        InstagramEntity instagram = queryFactory
                .selectFrom(instagramEntity)
                .where(instagramEntity.instagramDocumentId.eq(crawlingDto.getInstagramId()))
                .fetchOne();

        KakaoDocumentEntity kakaoDocument = queryFactory
                .selectFrom(kakaoDocumentEntity)
                .where(instagramEntity.kakaoDocumentEntity.kakaoDocumentId.eq(document.getId()))
                .fetchOne();

        instagram.setInstagramDocumentId(crawlingDto.getInstagramId());
        instagram.setKakaoDocumentEntity(kakaoDocument);
        instagram.setHashtagName(crawlingDto.getHashtagName());
        instagram.setHashtagCount(crawlingDto.getHashtagCount());
    }

    @Override
    public void instagramPostSave(PostDto postDto) {

    }

    private BooleanExpression latitudeBetween(Rect rect) {
        return rect != null ? instagramEntity.kakaoDocumentEntity.latitude.between(new BigDecimal(rect.getMinLatitude().getValue()),
                new BigDecimal(rect.getMaxLatitude().getValue())) : null;
    }

    private BooleanExpression longitudeBetween(Rect rect) {
        return rect != null ? instagramEntity.kakaoDocumentEntity.longitude.between(new BigDecimal(rect.getMinLongitude().getValue()),
                new BigDecimal(rect.getMaxLongitude().getValue())) : null;
    }

    private BooleanExpression hashtagCountBetween(String check) {
        switch (check) {
            case "check1"  :
                return check != null ? instagramEntity.hashtagCount.lt(10) : null;
            case "check2"  :
                return check != null ? instagramEntity.hashtagCount.between(10, 99) : null;
            case "check3"  :
                return check != null ? instagramEntity.hashtagCount.between(100, 999) : null;
            case "check4"  :
                return check != null ? instagramEntity.hashtagCount.between(1000, 9999) : null;
            case "check5"  :
                return check != null ? instagramEntity.hashtagCount.gt(10000) : null;

            default:
                return null;
        }
    }
}
