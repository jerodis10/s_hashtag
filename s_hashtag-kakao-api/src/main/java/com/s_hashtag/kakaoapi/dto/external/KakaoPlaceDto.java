package com.s_hashtag.kakaoapi.dto.external;

import com.s_hashtag.common.kakao.dto.external.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * meta: Response에 대한 Meta 데이터
 * documents: 검색한 지역에 포함된 가게의 정보
 */

@Getter
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPlaceDto {
    private Meta meta;
//    private Document documents;
    private List<Document> documents;
//    private Integer totalCount;

    public int getTotalCount() {
        return this.meta.getTotalCount();
    }

//    public int getPageableCount() {
//        return this.meta.getPageableCount();
//    }
}
