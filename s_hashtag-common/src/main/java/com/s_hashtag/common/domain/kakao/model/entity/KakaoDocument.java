package com.s_hashtag.common.domain.kakao.model.entity;

import com.s_hashtag.common.domain.instagram.model.entity.Instagram;
import com.s_hashtag.common.model.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * id: 해당 장소의 업체명에 부여된 고유 ID
 * categoryName: 카테고리 이름
 * categoryGroupCode: 중요 카테고리만 그룹핑한 카테고리 그룹 코드
 * categoryGroupName: 중요 카테고리만 그룹핑한 카테고리 그룹명
 * ex) {
 * "category_name": "의료,건강 > 약국",
 * "category_group_code": "PM9",
 * "category_group_name": "약국",
 * }
 */

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
