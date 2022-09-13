package com.s_hashtag.common.domain.instagram.model.entity;

import com.s_hashtag.common.domain.kakao.model.entity.KakaoDocument;
import com.s_hashtag.common.model.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
//@Setter
@NoArgsConstructor
@Table(name = "INSTAGRAM")
@Entity
public class Instagram extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "INSTAGRAM_ID")
    private Long id;

    private String instagramDocumentId;

    private String kakaoDocumentId;

    @OneToOne
    @JoinColumn(name = "KAKAO_ID")
    private KakaoDocument kakaoDocument;

    @OneToMany(mappedBy = "instagram")
    private List<InstagramPost> instagramPosts = new ArrayList<>();

    private String hashtagName;

    private BigDecimal hashtagCount;

    //==연관관계 메서드==//
    public void settingKakaoDocument(KakaoDocument kakaoDocument) {
        this.kakaoDocument = kakaoDocument;
        kakaoDocument.setInstagram(this);
    }

    @Builder
    public Instagram(String instagramDocumentId, String kakaoDocumentId, KakaoDocument kakaoDocument, String hashtagName, BigDecimal hashtagCount) {
        this.instagramDocumentId = instagramDocumentId;
        this.kakaoDocumentId = kakaoDocumentId;
        this.kakaoDocument = kakaoDocument;
        this.hashtagName = hashtagName;
        this.hashtagCount = hashtagCount;
    }

    public void changeInstagram(String instagramDocumentId, KakaoDocument kakaoDocument, String hashtagName, BigDecimal hashtagCount) {
        this.instagramDocumentId = instagramDocumentId;
        this.kakaoDocument = kakaoDocument;
        this.hashtagName = hashtagName;
        this.hashtagCount = hashtagCount;
    }
}


