package com.s_hashtag.common.instagram.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private String id;
    private String kakaoId;
    private String placeId;
    private double MinLatitude;
    private double MaxLatitude;
    private double MinLongitude;
    private double MaxLongitude;
    private double latitude;
    private double longitude;
    private String hashtagName;
    private Long hashtagCount;
    private String categoryGroupCode;
    private String categoryGroupName;
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private String placeUrl;
    private String phone;
    private String distance;
}
