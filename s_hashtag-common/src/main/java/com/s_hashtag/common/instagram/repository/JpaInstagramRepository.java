package com.s_hashtag.common.instagram.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s_hashtag.common.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.instagram.dto.external.PostDto;
import com.s_hashtag.common.instagram.dto.external.PlaceDto;
import com.s_hashtag.common.kakao.dto.external.Document;
import com.s_hashtag.common.kakao.dto.external.Rect;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.s_hashtag.common.instagram.model.entity.QInstagramEntity.instagramEntity;

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
                        instagramEntity.placeId.as("id"),
                        instagramEntity.placeId,
                        instagramEntity.instagramId,
                        instagramEntity.hashtagCount,
                        instagramEntity.hashtagName))
                .from(instagramEntity)
                .where()
                .orderBy(instagramEntity.hashtagCount.desc())
                .offset(1)
                .limit(50)
                .fetch();
    }

    @Override
    public List<PlaceDto> findByKeyword(String category, List<String> keywordList) {
        return queryFactory
                .select(Projections.fields(PlaceDto.class,
                        instagramEntity.placeId.as("id")))
                .from(instagramEntity)
                .orderBy(instagramEntity.hashtagCount.desc())
                .offset(1)
                .limit(50)
                .fetch();
    }

    @Override
    public List<PlaceDto> findByHashtagCount(Rect rect, String[] categoryList, String check) {
        return null;
    }

    @Override
    public void instagramSave(CrawlingDto crawlingDto, Document document) {

    }

    @Override
    public void instagramPostSave(PostDto postDto) {

    }
}
