package com.s_hashtag.common.instagram.model.entity;

import com.s_hashtag.common.kakao.model.entity.KakaoDocumentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "INSTAGRAM")
@Entity
public class InstagramEntity {

    @Id @GeneratedValue
    @Column(name = "INSTAGRAM_ID")
    private Long id;

    @Column(name = "INSTAGRAM_DOCUMENT_ID")
    private String instagramDocumentId;

//    @Column(name = "PLACE_ID")
//    private String placeId;

    @OneToOne
    @JoinColumn(name = "KAKAO_ID")
    private KakaoDocumentEntity kakaoDocumentEntity;

    @OneToMany(mappedBy = "instagramEntity")
    private List<InstagramPostEntity> instagramPostEntitys = new ArrayList<>();

//    @Column(name = "HASHTAG_NAME")
    private String hashtagName;

//    @Column(name = "HASHTAG_COUNT")
    private BigDecimal hashtagCount;

    //==연관관계 메서드==//
    public void setKakaoDocument(KakaoDocumentEntity kakaoDocumentEntity) {
        this.kakaoDocumentEntity = kakaoDocumentEntity;
        kakaoDocumentEntity.setInstagramEntity(this);
    }
}


