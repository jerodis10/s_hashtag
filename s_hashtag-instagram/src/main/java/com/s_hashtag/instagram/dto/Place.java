package com.s_hashtag.instagram.dto;

import com.s_hashtag.kakaoapi.domain.dto.Document;
import com.s_hashtag.kakaoapi.domain.dto.Meta;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Place {
    private Meta meta;
    private List<Document> documents;
    private String instagramId;
    private String hashtagName;
    private Long hashtagCount;
    private PostDtos postDtos;
}
