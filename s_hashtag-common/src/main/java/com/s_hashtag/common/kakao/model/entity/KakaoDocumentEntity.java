package com.s_hashtag.common.kakao.model.entity;

import com.s_hashtag.common.instagram.model.entity.InstagramEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "KAKAO_DOCUMENT")
@Entity
public class KakaoDocumentEntity {

    @Id @GeneratedValue
    @Column(name = "KAKAO_ID")
    private Long id;

    @Column(name = "KAKAO_DOCUMENT_ID")
    private String kakaoDocumentId;

    @OneToOne(mappedBy = "kakaoDocumentEntity")
    private InstagramEntity instagramEntity;

    @Column(name = "CATEGORY_GROUP_CODE ")
    private String categoryGroupCode ;

    @Column(name = "LATITUDE")
    private BigDecimal latitude;

    @Column(name = "LONGITUDE")
    private BigDecimal longitude;

    @Column(name = "PLACE_NAME")
    private String placeName;

    @Column(name = "ROAD_ADDRESS_NAME")
    private String roadAddressName;

    @Column(name = "PLACE_URL")
    private String placeUrl;

}
