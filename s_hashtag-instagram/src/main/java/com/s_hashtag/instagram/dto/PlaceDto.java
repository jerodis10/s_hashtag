package com.s_hashtag.instagram.dto;

import com.s_hashtag.kakaoapi.domain.rect.location.Coordinate;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private double MinLatitude;
    private double MaxLatitude;
    private double MinLongitude;
    private double MaxLongitude;
    private double latitude;
    private double longitude;
    private String hashtagName;
    private Long hashtagCount;
    private String category;
}
