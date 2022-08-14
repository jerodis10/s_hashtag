package com.s_hashtag.kakaoapi.dto.presentation;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s_hashtag.kakaoapi.rect.Rect;
import com.s_hashtag.kakaoapi.rect.location.Coordinate;
import com.s_hashtag.kakaoapi.rect.location.Latitude;
import com.s_hashtag.kakaoapi.rect.location.Longitude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
//@Setter
//@NoArgsConstructor
@AllArgsConstructor
public class KakaoMapDto {

//    @NotBlank
//    @JsonAlias("pa")
    @JsonProperty("pa")
    private String minLatitude;

    private String pa;

//    @NotBlank
    @JsonAlias("qa")
    private String maxLatitude;

//    @NotBlank
    @JsonAlias("oa")
    private String minLongitude;

//    @NotBlank
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
