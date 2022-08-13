package com.s_hashtag.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s_hashtag.kakaoapi.dto.presentation.KakaoMapDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MapApiControllerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void KakaoMapJsonTest() throws IOException {
//        String json = "{\"pa\":\"hello world\"}";
        String json = "{\"pa\":\"hello world\", \"qa\":\"q\"}";

//        var rect_json = {ha: 126.66578831035362, oa: 126.6851487382762, pa: 37.52847533960485, qa: 37.55625487247741};
//        var json = JSON.stringify(rect_json);


        KakaoMapDto result = this.objectMapper.readValue(json, KakaoMapDto.class);

        assertThat(result.getMinLatitude()).isEqualTo("hello world");
    }
}