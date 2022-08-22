package com.s_hashtag.kakaoapi.dto.presentation;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.s_hashtag.common.kakao.dto.external.Coordinate;
import com.s_hashtag.common.kakao.dto.external.Latitude;
import com.s_hashtag.common.kakao.dto.external.Longitude;
import com.s_hashtag.common.kakao.dto.external.Rect;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

//@Builder
//@Setter
//@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoMapDto {

    @NotBlank
    @JsonAlias("pa")
    private String minLatitude;

    @NotBlank
    @JsonAlias("qa")
    private String maxLatitude;

    @NotBlank
    @JsonAlias("oa")
    private String minLongitude;

    @NotBlank
    @JsonAlias("ha")
    private String maxLongitude;

    @JsonAlias("category_list")
    private String category;

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
