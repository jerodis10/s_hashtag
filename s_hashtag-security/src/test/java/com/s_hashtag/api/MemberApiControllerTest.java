package com.s_hashtag.api;

import com.s_hashtag.common.domain.member.dto.presentation.MemberDto;
import com.s_hashtag.common.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberApiControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("모든 멤버 검색")
    @Test
    void test(){
        List<MemberDto> memberList = memberRepository.findAll();

        assertThat(memberList.size()).isEqualTo(4);
    }

}

