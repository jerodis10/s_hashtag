package com.s_hashtag.instagram.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s_hashtag.instagram.dto.external.CrawlingDto;
import com.s_hashtag.instagram.dto.external.PlaceDto;
import com.s_hashtag.instagram.dto.external.PostDto;
import com.s_hashtag.kakaoapi.dto.external.Document;
import com.s_hashtag.kakaoapi.rect.Rect;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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
        return null;
//        return queryFactory
//                .select()
    }

    @Override
    public List<PlaceDto> findByKeyword(String category, List<String> keywordList) {
        return null;
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
