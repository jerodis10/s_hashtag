package com.s_hashtag.instagram.dto;

import com.s_hashtag.kakaoapi.domain.dto.Document;
import com.s_hashtag.kakaoapi.domain.dto.Meta;
import lombok.*;

import java.util.List;

@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    private List<Document> documents;
    private String instagramId;
    private String hashtagName;
    private Long hashtagCount;
    private PostDtos postDtos;
}
