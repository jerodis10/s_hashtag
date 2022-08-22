//package com.s_hashtag.instagram.repository;
//
//import com.s_hashtag.instagram.dto.external.CrawlingDto;
//import com.s_hashtag.instagram.dto.external.PlaceDto;
//import com.s_hashtag.instagram.dto.external.PostDto;
//import com.s_hashtag.kakaoapi.dto.external.Document;
//import com.s_hashtag.kakaoapi.rect.Rect;
//
//import java.util.*;
//
//public interface InstagramRepository {
//
//    List<PlaceDto> findAll(String category, Rect rect);
//
//    List<PlaceDto> findByKeyword(String category, List<String> keywordList);
//
//    List<PlaceDto> findByHashtagCount(Rect rect, String[] categoryList, String check);
//
//    void instagramSave(CrawlingDto crawlingDto, Document document);
//
//    void instagramPostSave(PostDto postDto);
//
////    List<PlaceDto> getHashtagByCount2(String[] categoryList, Map<String, Object> hashtag_count_param);
//
//
////    Optional<Member> findById(String id);
////    Optional<Member> findByName(String name);
////    List<Member> findAll();
//}
