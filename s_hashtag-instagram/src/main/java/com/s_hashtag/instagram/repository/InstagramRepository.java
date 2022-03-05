package com.s_hashtag.instagram.repository;

import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.dto.PostDto;
import com.s_hashtag.kakaoapi.domain.dto.Document;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface InstagramRepository {
    void kakao_document_save(Document document);
    void instagram_save(CrawlingDto crawlingDto, Document document);
    void instagram_post_save(PostDto postDto);
//    Optional<Member> findById(String id);
//    Optional<Member> findByName(String name);
//    List<Member> findAll();
}
