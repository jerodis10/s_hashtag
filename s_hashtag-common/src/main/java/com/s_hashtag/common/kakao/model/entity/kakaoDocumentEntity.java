package com.s_hashtag.common.kakao.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "KAKAO_DOCUMENT")
@Entity
public class kakaoDocumentEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "KAKAO_ID")
    private String kakaoId;

    @Column(name = "CATEGORY_GROUP_CODE ")
    private String categoryGroupCode ;

    @Column(name = "LATITUDE")
    private String latitude;

    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "PLACE_NAME")
    private String placeName;

    @Column(name = "ROAD_ADDRESS_NAME")
    private String roadAddressName;

    @Column(name = "PLACE_URL")
    private String placeUrl;

}
