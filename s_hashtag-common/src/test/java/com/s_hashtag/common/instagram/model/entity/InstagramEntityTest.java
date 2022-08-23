package com.s_hashtag.common.instagram.model.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
class InstagramEntityTest {

    private final EntityManager em;

    InstagramEntityTest(EntityManager em) {
        this.em = em;
    }

    @Test
    public void InstagramEntityTest() {

    }
}