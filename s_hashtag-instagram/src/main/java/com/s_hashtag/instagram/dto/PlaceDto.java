package com.s_hashtag.instagram.dto;

import lombok.*;

@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private String kakao_id;
    private String place_id;
    private double MinLatitude;
    private double MaxLatitude;
    private double MinLongitude;
    private double MaxLongitude;
    private double latitude;
    private double longitude;
    private String hashtagName;
    private Long hashtagCount;
    private String category_group_code;
    private String category_group_name;
    private String place_name;
    private String address_name;
    private String road_address_name;
    private String place_url;
    private String phone;
    private String distance;
}
