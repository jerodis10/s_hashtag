package com.s_hashtag.kakaoapi.dto.presentation;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.s_hashtag.common.domain.kakao.dto.external.Coordinate;
import com.s_hashtag.common.domain.kakao.dto.external.Latitude;
import com.s_hashtag.common.domain.kakao.dto.external.Longitude;
import com.s_hashtag.common.domain.kakao.dto.external.Rect;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

//@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoMapDto {

    @NotBlank
    @JsonAlias("qa")
    private String minLatitude;

    @NotBlank
    @JsonAlias("pa")
    private String maxLatitude;

    @NotBlank
    @JsonAlias("ha")
    private String minLongitude;

    @NotBlank
    @JsonAlias("oa")
    private String maxLongitude;

    @JsonAlias("category_list")
    private List<String> categoryList;

    @JsonAlias("hashtag_name")
    private String hashtagName;

    private String searchText;

    private String check;

    public Rect CreateRect() {
        Coordinate minLatitude = new Latitude(new BigDecimal(this.minLatitude));
        Coordinate maxLatitude = new Latitude(new BigDecimal(this.maxLatitude));
        Coordinate minLongitude = new Longitude(new BigDecimal(this.minLongitude));
        Coordinate maxLongitude = new Longitude(new BigDecimal(this.maxLongitude));
        return new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }
}
