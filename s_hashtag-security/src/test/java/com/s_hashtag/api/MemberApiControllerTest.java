package com.s_hashtag.api;

import com.s_hashtag.model.Member;
import com.s_hashtag.repository.JdbcTemplatememberRepository;
import com.s_hashtag.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberApiControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("모든 멤버 검색")
    @Test
    void test(){
        List<Member> memberList = memberRepository.findAll();

        assertThat(memberList.size()).isEqualTo(4);
    }

}

