package com.s_hashtag.instagram.repository;

import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.dto.PlaceDto;
import com.s_hashtag.instagram.dto.PostDto;
import com.s_hashtag.kakaoapi.domain.dto.Document;
import com.s_hashtag.kakaoapi.domain.rect.Rect;

import java.util.*;

//@Repository
public interface InstagramRepository {
    void kakao_document_save(Document document);

    void instagram_save(CrawlingDto crawlingDto, Document document);

    void instagram_post_save(PostDto postDto);

//    List<Map<String, Object>> getHashtag(String category, Rect rect);

    List<PlaceDto> getHashtag(String category, Rect rect);

//    Optional<Member> findById(String id);
//    Optional<Member> findByName(String name);
//    List<Member> findAll();
}
