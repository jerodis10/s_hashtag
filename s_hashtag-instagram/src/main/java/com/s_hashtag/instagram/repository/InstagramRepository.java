package com.s_hashtag.instagram.repository;

import com.s_hashtag.kakaoapi.domain.dto.Document;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface InstagramRepository {
    Document kakao_document_save(Document document);
//    Optional<Member> findById(String id);
//    Optional<Member> findByName(String name);
//    List<Member> findAll();
}
