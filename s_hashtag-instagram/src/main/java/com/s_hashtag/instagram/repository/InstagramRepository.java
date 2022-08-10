package com.s_hashtag.instagram.repository;

import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.dto.PlaceDto;
import com.s_hashtag.instagram.dto.PostDto;
import com.s_hashtag.kakaoapi.dto.external.Document;
import com.s_hashtag.kakaoapi.rect.Rect;

import java.util.*;

//@Repository
public interface InstagramRepository {

//    void kakao_document_save(Document document);

    void instagram_save(CrawlingDto crawlingDto, Document document);

    void instagram_post_save(PostDto postDto);

    List<PlaceDto> getHashtag(String category, Rect rect);

    List<PlaceDto> getHashtagByKeyword(String category, List<String> keywordList);

    List<PlaceDto> getHashtagByCount(String[] categoryList, String check);

    List<PlaceDto> getHashtagByCount2(String[] categoryList, Map<String, Object> hashtag_count_param);


//    Optional<Member> findById(String id);
//    Optional<Member> findByName(String name);
//    List<Member> findAll();
}
