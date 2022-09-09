package com.s_hashtag.common.domain.instagram.dto.external;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CrawlingDto {
    private final String instagramId;
    private final String placeId;
    private final String hashtagName;
    private final BigDecimal hashtagCount;
    private final PostDtos postDtos;

    public static CrawlingDto of(String instagramId, String placeId, String hashtagName, BigDecimal hashtagCount, PostDtos postDtos) {
        return new CrawlingDto(instagramId, placeId, hashtagName, hashtagCount, postDtos);
    }

    public List<PostDto> getPostDtoList() {
        return postDtos.getPostDtos();
    }
}
