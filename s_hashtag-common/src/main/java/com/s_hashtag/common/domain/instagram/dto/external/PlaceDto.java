package com.s_hashtag.common.domain.instagram.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private String kakaoDocumentId;
    private String instagramDocumentId;
    private double MinLatitude;
    private double MaxLatitude;
    private double MinLongitude;
    private double MaxLongitude;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String hashtagName;
    private BigDecimal hashtagCount;
    private String categoryGroupCode;
    private String categoryGroupName;
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private String placeUrl;
    private String phone;
    private String distance;
}
