package com.s_hashtag.instagram.repository;

import com.s_hashtag.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface InstagramRepository {
    Member save(Member member);
    Optional<Member> findById(String id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
