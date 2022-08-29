package com.s_hashtag.common.domain.kakao.model.entity;

import com.s_hashtag.common.domain.instagram.model.entity.Instagram;
import com.s_hashtag.common.model.entity.BaseEntity;
import lombok.Builder;
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
public class KakaoDocument extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "KAKAO_ID")
    private Long id;

    private String kakaoDocumentId;

    @OneToOne(mappedBy = "kakaoDocument")
    private Instagram instagram;

    private String categoryGroupCode ;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String placeName;

    private String roadAddressName;

    private String placeUrl;

    @Builder
    public KakaoDocument(String kakaoDocumentId, String categoryGroupCode, BigDecimal latitude, BigDecimal longitude, String placeName, String roadAddressName, String placeUrl) {
        this.kakaoDocumentId = kakaoDocumentId;
        this.categoryGroupCode = categoryGroupCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
        this.roadAddressName = roadAddressName;
        this.placeUrl = placeUrl;
    }

    public void changeKakaoDocument(String categoryGroupCode, BigDecimal latitude, BigDecimal longitude, String placeName, String roadAddressName, String placeUrl) {
        this.categoryGroupCode = categoryGroupCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
        this.roadAddressName = roadAddressName;
        this.placeUrl = placeUrl;
    }
}
