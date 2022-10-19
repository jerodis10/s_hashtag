package com.s_hashtag.kakaoapi.dto.external;

import com.s_hashtag.common.domain.kakao.dto.external.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * meta: Response에 대한 Meta 데이터
 * documents: 검색한 지역에 포함된 가게의 정보
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPlaceDto {
    private Meta meta;
    private List<Document> documents;

    public int getTotalCount() {
        return this.meta.getTotalCount();
    }

}
