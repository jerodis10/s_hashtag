package com.s_hashtag.api;

import com.s_hashtag.model.Member;
import com.s_hashtag.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootConfiguration
@SpringBootTest
class MemberApiControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("Test")
    @Test
    void test(){
        List<Member> memberList = memberRepository.findAll();

        assertThat(memberList.size()).isEqualTo(4);
    }

}

