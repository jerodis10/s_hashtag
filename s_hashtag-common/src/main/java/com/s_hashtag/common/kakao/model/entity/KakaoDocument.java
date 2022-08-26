package com.s_hashtag.common.kakao.model.entity;

import com.s_hashtag.common.instagram.model.entity.Instagram;
import com.s_hashtag.common.model.entity.BaseEntity;
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

}
