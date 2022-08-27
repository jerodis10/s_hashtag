package com.s_hashtag.common.domain.member.repository;

import com.s_hashtag.common.domain.member.dto.presentation.MemberDto;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    MemberDto save(MemberDto memberDto);

    Optional<MemberDto> findById(String id);

    Optional<MemberDto> findByName(String name);

    List<MemberDto> findAll();

    void delete(List<String> idList);
}
