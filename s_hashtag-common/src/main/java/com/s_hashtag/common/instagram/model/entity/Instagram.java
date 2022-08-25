package com.s_hashtag.common.instagram.model.entity;

import com.s_hashtag.common.kakao.model.entity.KakaoDocument;
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
public class Instagram {

    @Id @GeneratedValue
    @Column(name = "INSTAGRAM_ID")
    private Long id;

    @Column(name = "INSTAGRAM_DOCUMENT_ID")
    private String instagramDocumentId;

//    @Column(name = "PLACE_ID")
//    private String placeId;

    @OneToOne
    @JoinColumn(name = "KAKAO_ID")
    private KakaoDocument kakaoDocument;

    @OneToMany(mappedBy = "instagram")
    private List<InstagramPost> instagramPosts = new ArrayList<>();

    private String hashtagName;

    private BigDecimal hashtagCount;

    //==연관관계 메서드==//
    public void setKakaoDocument(KakaoDocument kakaoDocument) {
        this.kakaoDocument = kakaoDocument;
        kakaoDocument.setInstagram(this);
    }
}


