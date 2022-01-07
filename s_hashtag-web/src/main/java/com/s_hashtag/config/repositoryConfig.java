package com.s_hashtag.config;

import com.s_hashtag.repository.JdbcTemplatememberRepository;
import com.s_hashtag.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class repositoryConfig {

    private DataSource dataSource;

    @Autowired
    public repositoryConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
        return new JdbcTemplatememberRepository(dataSource);
    }


}
