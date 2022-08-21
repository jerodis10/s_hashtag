package com.s_hashtag.instagram.dto.external;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class CrawlingDto {
    private final String instagramId;
    private final String placeId;
    private final String hashtagName;
    private final Long hashtagCount;
    private final PostDtos postDtos;

    public static CrawlingDto of(String instagramId, String placeId, String hashtagName, String hashtagCount, PostDtos postDtos) {
        return new CrawlingDto(instagramId, placeId, hashtagName, Long.valueOf(hashtagCount), postDtos);
    }

    public List<PostDto> getPostDtoList() {
        return postDtos.getPostDtos();
    }
}
