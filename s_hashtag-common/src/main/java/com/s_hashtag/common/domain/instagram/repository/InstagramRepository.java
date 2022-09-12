package com.s_hashtag.common.domain.instagram.repository;

import com.s_hashtag.common.domain.kakao.dto.external.Document;
import com.s_hashtag.common.domain.kakao.dto.external.Rect;
import com.s_hashtag.common.domain.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.domain.instagram.dto.external.PlaceDto;
import com.s_hashtag.common.domain.instagram.dto.external.PostDto;

import java.util.List;

public interface InstagramRepository {

    List<PlaceDto> findAll(List<String> category, Rect rect);

    List<PlaceDto> findByKeyword(String category, Rect rect, List<String> keywordList);

    List<PlaceDto> findByHashtagCount(Rect rect, List<String> categoryList, String check);

    List<PostDto> findByHashtagName(List<String> categoryList, Rect rect, String hashtagName);

    void instagramSave(CrawlingDto crawlingDto, Document document);

    void instagramPostSave(PostDto postDto);

//    Optional<Member> findById(String id);
//    Optional<Member> findByName(String name);
//    List<Member> findAll();
}
